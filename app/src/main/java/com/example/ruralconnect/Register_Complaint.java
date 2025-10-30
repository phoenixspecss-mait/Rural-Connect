package com.example.ruralconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView; // Import AdapterView
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText; // Use regular EditText if not using TextInputEditText in Java
import android.widget.ImageView; // Import ImageView
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout; // Import TextInputLayout

// --- Imports for API ---
import com.example.ruralconnect.model.Complaint;
import com.example.ruralconnect.network.ApiService;
import com.example.ruralconnect.network.RetrofitClient;
import com.example.ruralconnect.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_Complaint extends AppCompatActivity {

    private static final String TAG = "RegisterComplaint";
    // --- Views matching your new XML IDs ---
    private EditText nameEditText, contactEditText, locationEditText, complaintTextEditText, otherComplaintEditText;
    private Spinner complaintTypeSpinner;
    private TextInputLayout otherComplaintLayout; // To show/hide the "Other" field
    private Button submitButton, addImageButton; // Correct button ID
    private ImageView imageViewPreview; // For image preview
    private ProgressBar progressBar; // Added ProgressBar

    private SessionManager sessionManager;
    private ApiService apiService;

    // --- Complaint Type Options ---
    // Add "Other" to the list
    private final String[] complaintTypes = {"Electricity", "Water Supply", "Street Light", "Garbage Collection", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Use the layout file name matching your XML
        setContentView(R.layout.activity_add_complaint);

        // --- Setup ---
        sessionManager = new SessionManager(getApplicationContext());
        apiService = RetrofitClient.getApiService();

        // --- Find Views using IDs from your XML ---
        nameEditText = findViewById(R.id.editTextName);
        contactEditText = findViewById(R.id.editTextContact);
        complaintTypeSpinner = findViewById(R.id.spinnerComplaintType);
        otherComplaintLayout = findViewById(R.id.textFieldOtherComplaint); // Find the layout
        otherComplaintEditText = findViewById(R.id.editTextOtherComplaint); // Find the EditText inside
        locationEditText = findViewById(R.id.editTextLocation);
        complaintTextEditText = findViewById(R.id.editTextComplaint);
        submitButton = findViewById(R.id.buttonSubmit); // Correct ID
        addImageButton = findViewById(R.id.buttonAddImage); // Add Image button
        imageViewPreview = findViewById(R.id.imageViewPreview); // Image preview
        progressBar = findViewById(R.id.progressBar); // Find ProgressBar

        // --- Setup Spinner ---
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, complaintTypes);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaintTypeSpinner.setAdapter(spinnerAdapter);

        // --- Spinner Logic for "Other" ---
        complaintTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (complaintTypes[position].equals("Other")) {
                    otherComplaintLayout.setVisibility(View.VISIBLE);
                } else {
                    otherComplaintLayout.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                otherComplaintLayout.setVisibility(View.GONE);
            }
        });

        // --- Check if user is logged in ---
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, "You must be logged in to report a problem", Toast.LENGTH_LONG).show();
            // TODO: Send user to LoginActivity
            finish();
            return;
        }

        submitButton.setOnClickListener(v -> submitComplaint());

        // --- TODO: Add Image Button Listener ---
        addImageButton.setOnClickListener(v -> {
            // We are skipping image handling for now
            Toast.makeText(this, "Image upload not implemented yet", Toast.LENGTH_SHORT).show();
            // Implement image picking here later (startActivityForResult or new ActivityResultLauncher)
        });
    }

    private void submitComplaint() {
        // 1. Get Values from Views
        String name = nameEditText.getText().toString().trim();
        String contact = contactEditText.getText().toString().trim();
        String selectedType = complaintTypeSpinner.getSelectedItem().toString();
        String location = locationEditText.getText().toString().trim();
        String text = complaintTextEditText.getText().toString().trim();
        String complaintType = selectedType;

        // If "Other" is selected, use the text from the other field
        if (selectedType.equals("Other")) {
            complaintType = otherComplaintEditText.getText().toString().trim();
            if (complaintType.isEmpty()) {
                Toast.makeText(this, "Please specify the complaint type", Toast.LENGTH_SHORT).show();
                otherComplaintEditText.setError("Required"); // Set error on the EditText
                return;
            }
        }

        // 2. Basic Validation
        if (name.isEmpty() || contact.isEmpty() || location.isEmpty() || text.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            // Optional: Set errors on the specific empty fields
            if (name.isEmpty()) nameEditText.setError("Required");
            if (contact.isEmpty()) contactEditText.setError("Required");
            if (location.isEmpty()) locationEditText.setError("Required");
            if (text.isEmpty()) complaintTextEditText.setError("Required");
            return;
        } else {
            // Clear errors if fields are filled
            nameEditText.setError(null);
            contactEditText.setError(null);
            locationEditText.setError(null);
            complaintTextEditText.setError(null);
            otherComplaintEditText.setError(null);
        }


        // 3. Show Loading State
        submitButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        // 4. Get Auth Token
        String authToken = sessionManager.getAuthToken();
        if (authToken == null) {
            Toast.makeText(this, "Session expired. Please log in again.", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            submitButton.setEnabled(true);
            // TODO: Send to LoginActivity
            finish();
            return;
        }

        // 5. Create Complaint POJO (using all fields)
        Complaint newComplaint = new Complaint();
        newComplaint.setName(name);
        newComplaint.setContactNumber(contact);
        newComplaint.setComplaintType(complaintType); // Use the final type
        newComplaint.setLocation(location);
        newComplaint.setComplaintText(text);
        // newComplaint.setImageUrl(null); // Skipping image URL for now

        Log.d(TAG, "Attempting to submit complaint: Name=" + name + ", Type=" + complaintType + ", Location=" + location); // Log key details

        // 6. Make API Call
        Call<Complaint> call = apiService.createComplaint(authToken, newComplaint);
        call.enqueue(new Callback<Complaint>() {
            @Override
            public void onResponse(Call<Complaint> call, Response<Complaint> response) {
                // Hide Loading State
                progressBar.setVisibility(View.GONE);
                submitButton.setEnabled(true);

                if (response.isSuccessful()) {
                    Log.d(TAG, "Complaint submitted successfully! ID: " + (response.body() != null ? response.body().getId() : "null"));
                    Toast.makeText(Register_Complaint.this, "Complaint submitted!", Toast.LENGTH_SHORT).show();

                    // Return to previous screen (Complaints_List will refetch)
                    setResult(RESULT_OK); // Signal success
                    finish();

                } else {
                    Log.e(TAG, "API Error! Code: " + response.code() + " Body: " + response.errorBody());
                    try {
                        Log.e(TAG, "Error Body String: " + response.errorBody().string()); // Log the error message from backend
                    } catch (Exception e) { Log.e(TAG, "Error reading error body", e); }
                    Toast.makeText(Register_Complaint.this, "Failed to submit. Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Complaint> call, Throwable t) {
                // Hide Loading State
                progressBar.setVisibility(View.GONE);
                submitButton.setEnabled(true);
                Log.e(TAG, "Network Failure!", t);
                Toast.makeText(Register_Complaint.this, "Network error. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }
}