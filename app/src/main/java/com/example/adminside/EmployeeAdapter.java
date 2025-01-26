package com.example.adminside;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee> employeeList;
    private OnEmployeeClickListener listener;

    public EmployeeAdapter(List<Employee> employeeList, OnEmployeeClickListener listener) {
        this.employeeList = employeeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.bind(employee, listener);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId, textViewForename, textViewSurname;
        Button buttonDelete;

        public EmployeeViewHolder(View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewId);
            textViewForename = itemView.findViewById(R.id.textViewForename);
            textViewSurname = itemView.findViewById(R.id.textViewSurname);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }

        public void bind(Employee employee, OnEmployeeClickListener listener) {
            textViewId.setText("ID: " + employee.getId());
            textViewForename.setText("Forename: " + employee.getForename());
            textViewSurname.setText("Surname: " + employee.getSurname());

            itemView.setOnClickListener(v -> listener.onEmployeeClick(employee));

            // Set up delete button
            buttonDelete.setOnClickListener(v -> listener.onDeleteClick(employee));
        }
    }

    public interface OnEmployeeClickListener {
        void onEmployeeClick(Employee employee); // For editing
        void onDeleteClick(Employee employee); // For deleting
    }
}