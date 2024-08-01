package com.example.vsga_2024;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddDataActivity extends AppCompatActivity {
    private EditText etName, etAddress;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        etName = findViewById(R.id.et_name);
        etAddress = findViewById(R.id.et_address);
        Button btnSubmit = findViewById(R.id.btn_submit);
        Button btnCancel = findViewById(R.id.btn_cancel);

        dbHelper = new DBHelper(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String address = etAddress.getText().toString();

                if (name.isEmpty() || address.isEmpty()) {
                    Toast.makeText(AddDataActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (isDataExists(name, address)) {
                        Toast.makeText(AddDataActivity.this, "Data already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        saveData(name, address);
                    }
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean isDataExists(String name, String address) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DBHelper.COLUMN_NAME, DBHelper.COLUMN_ADDRESS};
        String selection = DBHelper.COLUMN_NAME + "=? AND " + DBHelper.COLUMN_ADDRESS + "=?";
        String[] selectionArgs = {name, address};
        Cursor cursor = db.query(DBHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    private void saveData(String name, String address) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, name);
        values.put(DBHelper.COLUMN_ADDRESS, address);

        long newRowId = db.insert(DBHelper.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(AddDataActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(AddDataActivity.this, "Error saving data", Toast.LENGTH_SHORT).show();
        }
    }
}