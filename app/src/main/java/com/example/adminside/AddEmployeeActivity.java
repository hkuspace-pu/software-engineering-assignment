package com.example.adminside;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AddEmployeeActivity extends AppCompatActivity {
    private EditText idEditText, forenameEditText, surnameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addemployeelayout);

        idEditText = findViewById(R.id.editTextId);
        forenameEditText = findViewById(R.id.editTextForename);
        surnameEditText = findViewById(R.id.editTextSurname);
        Button addButton = findViewById(R.id.buttonAddEmployee);
        Button backButton = findViewById(R.id.buttonBack); // Initialize the back button

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEmployee();
            }
        });

        // Set onClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the current activity and return to the previous one
            }
        });
    }

    private void submitEmployee() {
        String id = idEditText.getText().toString();
        String forename = forenameEditText.getText().toString();
        String surname = surnameEditText.getText().toString();

        if (id.isEmpty() || forename.isEmpty() || surname.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate ID input
        int employeeId;
        try {
            employeeId = Integer.parseInt(id);
            if (employeeId <= 0) {
                Toast.makeText(this, "ID must be a positive integer", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid ID format", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject employeeJson = new JSONObject();
        try {
            employeeJson.put("id", employeeId);
            employeeJson.put("forename", forename);
            employeeJson.put("surname", surname);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating JSON object", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://web.socem.plymouth.ac.uk/COMP2000/api/employees";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                employeeJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("API Response", response.toString());
                        Toast.makeText(AddEmployeeActivity.this, "Employee added successfully!", Toast.LENGTH_SHORT).show();
                        finish(); // Close this activity immediately
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API Error", "Error: " + error.toString());
                        if (error.networkResponse != null) {
                            Log.e("API Error", "Status Code: " + error.networkResponse.statusCode);
                            Log.e("API Error", "Response Body: " + new String(error.networkResponse.data));
                        }
                        Toast.makeText(AddEmployeeActivity.this, "Error adding employee: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        VolleySingleton.getInstance(AddEmployeeActivity.this).getRequestQueue().add(jsonObjectRequest);
    }};