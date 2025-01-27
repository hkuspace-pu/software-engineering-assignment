package com.example.adminside;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    Button addemployee_button;
    Button viewEmployeeList_button;
    private static final String URL = "https://web.socem.plymouth.ac.uk/COMP2000/api/employees";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        addemployee_button = findViewById(R.id.addemployee_button);

        viewEmployeeList_button = findViewById(R.id.viewEmployeeList_button);

        addemployee_button.setOnClickListener(view -> addemployee());

        viewEmployeeList_button.setOnClickListener(view -> viewEmployeeList());
    }

    private void addemployee() {
        Intent intent = new Intent(Dashboard.this, AddEmployeeActivity.class);
        startActivity(intent);
    }

    private void deleteemployee() {
        Intent intent = new Intent(Dashboard.this, DeleteEmployeeActivity.class);
        startActivity(intent);
    }

    private void viewEmployeeList() {
        Intent intent = new Intent(Dashboard.this, EmployeeListActivity.class);
        startActivity(intent);
    }
}