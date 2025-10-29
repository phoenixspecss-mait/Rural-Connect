package com.example.ruralconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Complaints_List extends AppCompatActivity {

    private ComplaintsAdapter adapter;
    private List<Complaint> complaints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_list);

        RecyclerView recyclerView = findViewById(R.id.complaints_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        complaints = new ArrayList<>();
        complaints.add(new Complaint("Water Supply Issue", "No water in the main tank for 3 days."));
        complaints.add(new Complaint("Street Light Outage", "Street light in front of my house is not working."));
        complaints.add(new Complaint("Garbage Collection Delay", "Garbage has not been collected for a week."));

        adapter = new ComplaintsAdapter(complaints);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            Complaint clickedComplaint = complaints.get(position);
            Toast.makeText(this, clickedComplaint.getTitle(), Toast.LENGTH_SHORT).show();
        });

        ActivityResultLauncher<Intent> addComplaintLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String title = result.getData().getStringExtra("title");
                        String description = result.getData().getStringExtra("description");
                        complaints.add(new Complaint(title, description));
                        adapter.notifyItemInserted(complaints.size() - 1);
                    }
                });

        FloatingActionButton fab = findViewById(R.id.fab_add_complaint);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, Register_Complaint.class);
            addComplaintLauncher.launch(intent);
        });
    }
}
