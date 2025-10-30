package com.example.ruralconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruralconnect.ComplaintsAdapter; // Import Adapter
import com.example.ruralconnect.model.Complaint; // Import Model
import com.example.ruralconnect.network.ApiService; // Import Network
import com.example.ruralconnect.network.RetrofitClient; // Import Network
import com.example.ruralconnect.utils.SessionManager; // Import SessionManager

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call; // Import Retrofit
import retrofit2.Callback; // Import Retrofit
import retrofit2.Response; // Import Retrofit

public class Complaints_List extends AppCompatActivity {

    private static final String TAG = "ComplaintsListActivity"; // For logging

    private ComplaintsAdapter adapter;
    private List<Complaint> complaints; // Will be filled by API call
    private RecyclerView recyclerView;

    // --- API and Session ---
    private SessionManager sessionManager;
    private ApiService apiService;

    // --- ActivityResultLauncher ---
    private ActivityResultLauncher<Intent> addComplaintLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_list);

        // --- Initialize API and Session ---
        sessionManager = new SessionManager(getApplicationContext());
        apiService = RetrofitClient.getApiService();

        recyclerView = findViewById(R.id.complaints_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize with an empty list, API call will fill it
        complaints = new ArrayList<>();
        adapter = new ComplaintsAdapter(complaints);
        recyclerView.setAdapter(adapter);

        // --- THIS IS THE FIX ---
        adapter.setOnItemClickListener(position -> {
            Complaint clickedComplaint = complaints.get(position);
            // Use getComplaintType() instead of getTitle()
            Toast.makeText(this, "Clicked: " + clickedComplaint.getComplaintType(), Toast.LENGTH_SHORT).show();
            // Example: Open a detail view
            // Intent detailIntent = new Intent(this, ComplaintDetailActivity.class);
            // detailIntent.putExtra("complaint", clickedComplaint); // Complaint must be Serializable
            // startActivity(detailIntent);
        });
        // --- END OF FIX ---

        // --- ActivityResultLauncher (Refetch list on result) ---
        addComplaintLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Log.d(TAG, "Returned from AddComplaint, refreshing list.");
                        fetchMyComplaints(); // Re-fetch the updated list
                    }
                });

        // --- FAB Listener (Same as before) ---
        FloatingActionButton fab = findViewById(R.id.fab_add_complaint);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, Register_Complaint.class); // Opens your complaint form
            addComplaintLauncher.launch(intent);
        });


        // --- Check login and Fetch complaints from API ---
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, "Please log in to view complaints.", Toast.LENGTH_LONG).show();
            // TODO: Redirect to LoginActivity
            finish();
            return;
        }
        fetchMyComplaints(); // Fetch data when activity starts
    }

    private void fetchMyComplaints() {
        String authToken = sessionManager.getAuthToken();
        if (authToken == null) {
            Log.e(TAG, "Auth token is null, cannot fetch complaints.");
            Toast.makeText(this, "Session expired, please log in again.", Toast.LENGTH_SHORT).show();
            // TODO: Start LoginActivity
            return;
        }

        Log.d(TAG, "Fetching complaints from API..."); // Log before call
        Call<List<Complaint>> call = apiService.getMyComplaints(authToken);

        call.enqueue(new Callback<List<Complaint>>() {
            @Override
            public void onResponse(Call<List<Complaint>> call, Response<List<Complaint>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "Successfully fetched " + response.body().size() + " complaints.");
                    complaints = response.body();
                    adapter.updateComplaints(complaints); // Update the adapter's data
                } else {
                    Log.e(TAG, "Failed to fetch complaints: " + response.code());
                    Toast.makeText(Complaints_List.this, "Failed to load complaints: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Complaint>> call, Throwable t) {
                Log.e(TAG, "Network failure fetching complaints", t);
                Toast.makeText(Complaints_List.this, "Network error loading complaints.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}