package com.example.adminside;

import android.os.Bundle;
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

public class Volley extends AppCompatActivity {
    private EditText idEditText, forenameEditText, surnameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addemployeelayout);

        idEditText = findViewById(R.id.editTextId);
        forenameEditText = findViewById(R.id.editTextForename);
        surnameEditText = findViewById(R.id.editTextSurname);
        Button addButton = findViewById(R.id.buttonAddEmployee);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitEmployee();
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

        JSONObject employeeJson = new JSONObject();
        try {
            employeeJson.put("id", id);
            employeeJson.put("forename", forename);
            employeeJson.put("surname", surname);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "https://web.socem.plymouth.ac.uk/COMP2000/api/employees";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                employeeJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Volley.this, "Employee added successfully!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Volley.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        VolleySingleton.getInstance(this).getRequestQueue().add(jsonObjectRequest);
    }
}