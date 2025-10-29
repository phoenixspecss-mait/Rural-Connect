package com.example.ruralconnect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Register_Complaint extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private EditText editTextName, editTextContact, editTextComplaint, editTextLocation, editTextOtherComplaint;
    private Button buttonSubmit, buttonAddImage;
    private ImageView imageViewPreview;
    private Spinner spinnerComplaintType;
    private TextInputLayout textFieldOtherComplaint;
    private Uri imageUri;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_complaint);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        editTextName = findViewById(R.id.editTextName);
        editTextContact = findViewById(R.id.editTextContact);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextComplaint = findViewById(R.id.editTextComplaint);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonAddImage = findViewById(R.id.buttonAddImage);
        imageViewPreview = findViewById(R.id.imageViewPreview);
        spinnerComplaintType = findViewById(R.id.spinnerComplaintType);
        textFieldOtherComplaint = findViewById(R.id.textFieldOtherComplaint);
        editTextOtherComplaint = findViewById(R.id.editTextOtherComplaint);

        // Populate Spinner
        List<String> complaintTypes = new ArrayList<>();
        complaintTypes.add("Electricity");
        complaintTypes.add("Water Supply");
        complaintTypes.add("Roads");
        complaintTypes.add("Healthcare");
        complaintTypes.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, complaintTypes);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerComplaintType.setAdapter(adapter);

        spinnerComplaintType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    textFieldOtherComplaint.setVisibility(View.VISIBLE);
                } else {
                    textFieldOtherComplaint.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        editTextLocation.setFocusable(false);
        editTextLocation.setClickable(true);
        editTextLocation.setOnClickListener(v -> fetchLocation());

        buttonAddImage.setOnClickListener(v -> openFileChooser());

        buttonSubmit.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String contact = editTextContact.getText().toString();
            String location = editTextLocation.getText().toString();
            String complaint = editTextComplaint.getText().toString();
            String complaintType = spinnerComplaintType.getSelectedItem().toString();

            if (complaintType.equals("Other")) {
                complaintType = editTextOtherComplaint.getText().toString();
            }

            Toast.makeText(Register_Complaint.this, "Complaint Registered!", Toast.LENGTH_SHORT).show();
        });
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLocation();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(Register_Complaint.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (addresses != null && !addresses.isEmpty()) {
                            String address = addresses.get(0).getAddressLine(0);
                            editTextLocation.setText(address);
                        } else {
                            editTextLocation.setText("Lat: " + location.getLatitude() + ", Lon: " + location.getLongitude());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(Register_Complaint.this, "Failed to get address", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register_Complaint.this, "Unable to get location. Please enable GPS.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageViewPreview.setImageURI(imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                getLocation();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
