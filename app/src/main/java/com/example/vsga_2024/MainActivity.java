package com.example.vsga_2024;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.listView);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddDataActivity.class);
            startActivity(intent);
        });

        displayData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayData();
    }

    private void displayData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {
            String[] from = {DBHelper.COLUMN_NAME, DBHelper.COLUMN_ADDRESS};
            int[] to = {R.id.tv_name, R.id.tv_address};

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item_data, cursor, from, to, 0);
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }
    }
}