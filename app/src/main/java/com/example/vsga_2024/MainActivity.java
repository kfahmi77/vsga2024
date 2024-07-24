package com.example.vsga_2024;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String[] countryName = new String[]{
            "Indonesia",
            "Malaysia",
            "Thailand",
            "Singapura",
            "Brunei Darussalam",
            "Vietnam",
            "Filipina",
            "Myanmar",
            "Laos",
            "Cambodia",
            "Papua New Guinea",
            "Bangkok",
            "Bangladesh",
            "Jepang",
            "Korea Selatan",
            "Korea Utara",
            "India",
            "Iran",
            "Irak",
    };
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Arrays.sort(countryName);
        listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                countryName
        );
        listView.setAdapter(adapter);

     listView.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(this,
             "Anda memilih " + countryName[position],
             Toast.LENGTH_SHORT).show());

    }
}