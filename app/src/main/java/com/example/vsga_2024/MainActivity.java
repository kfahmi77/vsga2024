package com.example.vsga_2024;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String FILE_NAME = "vsga_2024.txt";
    Button createFileBtn, modifyFileBtn, readFileBtn, deleteFileBtn;
    EditText resultTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTxt = findViewById(R.id.resultTxt);
    }

    private void createFile() {
        File file = new File(getFilesDir(), FILE_NAME);
        FileOutputStream fos;
        try {
            file.createNewFile();
            fos = new FileOutputStream(file, false);
            fos.write(resultTxt.getText().toString().getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        File file = new File(getFilesDir(), FILE_NAME);
        if (file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            resultTxt.setText(text);
        }
        else {
            resultTxt.setText("");
        }
    }

    private void deleteFile() {
        File file = new File(getFilesDir(), FILE_NAME);
        if (file.exists()) {
            file.delete();
            resultTxt.setText("");
        }
    }
    public void onClick(View view) {
        if (view.getId() == R.id.createFileBtn)
            createFile();
        else if (view.getId() == R.id.readFileBtn)
            readFile();
        else if (view.getId() == R.id.deleteFileBtn)
            deleteFile();
    }

}
