package com.example.vsga_2024;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private TaskDAO taskDAO;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView lightSensorStatus;
    private EditText editTitle, editDescription, editStatus;

    private static final float DARK_THRESHOLD = 10.0f;       // Very Dark
    private static final float DIM_THRESHOLD = 50.0f;        // Dim
    private static final float BRIGHT_THRESHOLD = 200.0f;    // Bright
    private static final float VERY_BRIGHT_THRESHOLD = 500.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightSensorStatus = findViewById(R.id.lightSensorStatus);

        taskDAO = new TaskDAO(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        editStatus = findViewById(R.id.editStatus);
        Button btnAddTask = findViewById(R.id.btnAddTask);

        TaskAdapter.OnTaskClickListener listener = new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(Task task) {
                showUpdateDialog(task);
            }

            @Override
            public void onTaskLongClick(Task task) {
                showDeleteConfirmationDialog(task);
            }
        };

        adapter = new TaskAdapter(taskDAO.getAllTasks(), listener);
        recyclerView.setAdapter(adapter);

        btnAddTask.setOnClickListener(v -> addTask());
    }

    private void addTask() {
        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();
        String status = editStatus.getText().toString();
        taskDAO.insertTask(title, description, status);
        updateTaskList();
        clearInputs();
    }

    private void updateTaskList() {
        ArrayList<Task> taskList = taskDAO.getAllTasks();
        adapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(Task task) {
                showUpdateDialog(task);
            }

            @Override
            public void onTaskLongClick(Task task) {
                showDeleteConfirmationDialog(task);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void clearInputs() {
        editTitle.setText("");
        editDescription.setText("");
        editStatus.setText("");
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Register the sensor listener
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the sensor listener
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lightValue = event.values[0];
           updateLightStatus(lightValue);
        }
    }
    private void updateLightStatus(float lightLevel) {
        String status;

        if (lightLevel < DARK_THRESHOLD) {
            status = "Gelap";
        } else if (lightLevel < DIM_THRESHOLD) {
            status = "Lumayan Gelap";
        } else if (lightLevel < BRIGHT_THRESHOLD) {
            status = "Cahaya: Terang";
        } else if (lightLevel < VERY_BRIGHT_THRESHOLD) {
            status = "Cukup Terang";
        } else {
            status = "Sangat Terang";
        }

        lightSensorStatus.setText(status);
        editStatus.setText(status);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    private void showUpdateDialog(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Task");

        View view = getLayoutInflater().inflate(R.layout.dialog_task, null);
        EditText editTitle = view.findViewById(R.id.editTitle);
        EditText editDescription = view.findViewById(R.id.editDescription);
        EditText editStatus = view.findViewById(R.id.editStatus);

        editTitle.setText(task.getTitle());
        editDescription.setText(task.getDescription());
        editStatus.setText(task.getStatus());

        builder.setView(view);
        builder.setPositiveButton("Update", (dialog, which) -> {
            String title = editTitle.getText().toString();
            String description = editDescription.getText().toString();
            String status = editStatus.getText().toString();
            taskDAO.updateTask(task.getId(), title, description, status);
            updateTaskList();
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showDeleteConfirmationDialog(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Task");
        builder.setMessage("Are you sure you want to delete this task?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            taskDAO.deleteTask(task.getId());
            updateTaskList();
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }


}
