package com.example.vsga_2024;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button simpanBtn, bacaBtn;
    private EditText namaTxt, nimTxt;
    private TextView displayTxt;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DbHelper(this);
        displayTxt = findViewById(R.id.displayTxt);
        namaTxt = findViewById(R.id.namatxt);
        nimTxt = findViewById(R.id.nimTxt);
        simpanBtn = findViewById(R.id.simpanBtn);
        bacaBtn = findViewById(R.id.bacaBtn);

        simpanBtn.setOnClickListener(view -> {
            dbHelper.insertMahasiswa(namaTxt.getText().toString(), nimTxt.getText().toString());
            namaTxt.setText("");
            nimTxt.setText("");
            Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
        });

        bacaBtn.setOnClickListener(view -> {
            ArrayList<String> mahasiswaList = dbHelper.getAllMahasiswa();
            displayTxt.setText("");
            for (String mahasiswa : mahasiswaList) {
                displayTxt.append(mahasiswa + "\n");
            }
        });
    }
}