package com.example.adminside;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewEmployeeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private List<Employee> employeeList;
    private static final String URL = "https://web.socem.plymouth.ac.uk/COMP2000/api/employees";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewemployeedetaillayout);

        recyclerView = findViewById(R.id.recyclerViewEmployees);
        employeeList = new ArrayList<>();
        employeeAdapter = new EmployeeAdapter(employeeList, new EmployeeAdapter.OnEmployeeClickListener() {
            @Override
            public void onEmployeeClick(Employee employee) {
                // Navigate to EditEmployeeActivity
                Intent intent = new Intent(ViewEmployeeActivity.this, EditEmployeeActivity.class);
                intent.putExtra("EMPLOYEE_ID", employee.getId());
                intent.putExtra("EMPLOYEE_FORNAME", employee.getForename());
                intent.putExtra("EMPLOYEE_SURNAME", employee.getSurname());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Employee employee) {
                // Call delete method
                deleteEmployee(employee.getId());
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(employeeAdapter);

        fetchEmployeeIds();
    }

    private void fetchEmployeeIds() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        employeeList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject employee = response.getJSONObject(i);
                                int id = employee.getInt("id");
                                String forename = employee.getString("forename");
                                String surname = employee.getString("surname");
                                employeeList.add(new Employee(id, forename, surname));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ViewEmployeeActivity.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                            }
                        }
                        employeeAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API Error", error.toString());
                        Toast.makeText(ViewEmployeeActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        VolleySingleton.getInstance(this).getRequestQueue().add(jsonArrayRequest);
    }

    private void deleteEmployee(int employeeId) {
        String deleteUrl = URL + "/" + employeeId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                deleteUrl,
                null,
                response -> {
                    Toast.makeText(ViewEmployeeActivity.this, "Employee deleted successfully!", Toast.LENGTH_SHORT).show();
                    fetchEmployeeIds(); // Refresh the list after deletion
                },
                error -> {
                    Log.e("API Error", error.toString());
                    Toast.makeText(ViewEmployeeActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );

        VolleySingleton.getInstance(this).getRequestQueue().add(jsonObjectRequest);
    }
}