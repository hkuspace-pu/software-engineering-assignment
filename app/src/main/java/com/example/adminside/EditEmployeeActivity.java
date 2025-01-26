package com.example.adminside;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class EditEmployeeActivity extends AppCompatActivity {
    private EditText editTextId, editTextForename, editTextSurname;
    private static final String URL = "https://web.socem.plymouth.ac.uk/COMP2000/api/employees/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editemployeelayout);

        editTextId = findViewById(R.id.editTextId);
        editTextForename = findViewById(R.id.editTextForename);
        editTextSurname = findViewById(R.id.editTextSurname);
        Button buttonSave = findViewById(R.id.buttonSave);
        Button backButton1 = findViewById(R.id.buttonBack1);

        int id = getIntent().getIntExtra("EMPLOYEE_ID", -1);
        String forename = getIntent().getStringExtra("EMPLOYEE_FORNAME");
        String surname = getIntent().getStringExtra("EMPLOYEE_SURNAME");

        if (id == -1 || forename == null || surname == null) {
            Toast.makeText(this, "Employee data is missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        editTextId.setText(String.valueOf(id));
        editTextForename.setText(forename);
        editTextSurname.setText(surname);

        buttonSave.setOnClickListener(v -> {
            saveEmployee(id);
        });

        backButton1.setOnClickListener(v -> finish());
    }

    private void saveEmployee(int id) {
        String forename = editTextForename.getText().toString();
        String surname = editTextSurname.getText().toString();

        if (forename.isEmpty() || surname.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject employeeJson = new JSONObject();
        try {
            employeeJson.put("id", id);
            employeeJson.put("forename", forename);
            employeeJson.put("surname", surname);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating JSON object", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                URL + id,
                employeeJson,
                response -> {
                    Toast.makeText(EditEmployeeActivity.this, "Employee updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    if (error.networkResponse != null) {
                        Log.e("API Error", "Error Code: " + error.networkResponse.statusCode);
                        Log.e("API Error", "Error Data: " + new String(error.networkResponse.data));
                    }
                    Toast.makeText(EditEmployeeActivity.this, "Error updating employee: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );

        VolleySingleton.getInstance(this).getRequestQueue().add(jsonObjectRequest);
    }
}