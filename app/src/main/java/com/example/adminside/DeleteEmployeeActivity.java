package com.example.adminside;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

public class DeleteEmployeeActivity extends AppCompatActivity {
    private TextView textViewId, textViewForename, textViewSurname;
    private static final String URL = "https://web.socem.plymouth.ac.uk/COMP2000/api/employees"; // Define your base URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deleteemployeelayout);

        textViewId = findViewById(R.id.textViewId);
        textViewForename = findViewById(R.id.textViewForename);
        textViewSurname = findViewById(R.id.textViewSurname);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonCancel = findViewById(R.id.buttonCancel);

        String id = getIntent().getStringExtra("EMPLOYEE_ID");
        String forename = getIntent().getStringExtra("EMPLOYEE_FORNAME");
        String surname = getIntent().getStringExtra("EMPLOYEE_SURNAME");

        // Check if the received data is valid
        if (id == null || forename == null || surname == null) {
            Toast.makeText(this, "Employee data is missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        textViewId.setText("Employee ID: " + id);
        textViewForename.setText("Forename: " + forename);
        textViewSurname.setText("Surname: " + surname);

        buttonDelete.setOnClickListener(v -> {
            try {
                int employeeId = Integer.parseInt(id); // Convert String ID to int
                deleteEmployee(employeeId);
            } catch (NumberFormatException e) {
                Toast.makeText(DeleteEmployeeActivity.this, "Invalid Employee ID", Toast.LENGTH_SHORT).show();
            }
        });

        buttonCancel.setOnClickListener(v -> finish());
    }

    private void deleteEmployee(int employeeId) {
        String deleteUrl = URL + "/" + employeeId; // Construct the DELETE URL

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                deleteUrl,
                null,
                response -> {
                    Toast.makeText(DeleteEmployeeActivity.this, "Employee deleted successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity after deletion
                },
                error -> {
                    Log.e("API Error", "Error: " + error.toString());
                    Toast.makeText(DeleteEmployeeActivity.this, "Error deleting employee: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );

        // Add the request to the RequestQueue
        VolleySingleton.getInstance(this).getRequestQueue().add(jsonObjectRequest);
    }
}