package com.example.ruralconnect;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// --- Imports for API ---
import com.example.ruralconnect.model.JwtAuthResponse;
import com.example.ruralconnect.model.LoginRequest;
import com.example.ruralconnect.network.ApiService;
import com.example.ruralconnect.network.RetrofitClient;
import com.example.ruralconnect.utils.SessionManager; // Make sure you created this

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_page extends AppCompatActivity {

    private static final String TAG = "LoginActivity"; // For Logcat

    private EditText emailEditText, passwordEditText;
    private Button loginButton;

    private SessionManager sessionManager; // For saving the token

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This is your correct layout file
        setContentView(R.layout.activity_login_page);

        sessionManager = new SessionManager(getApplicationContext());

        // --- Check if already logged in ---


        // --- THIS IS THE FIX ---
        // These IDs now match your new XML file
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        // This line will no longer crash
        loginButton.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        // We get the text from the correct fields
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Create the Request POJO
        LoginRequest loginRequest = new LoginRequest(email, password);

        // 2. Get the ApiService and create the call
        ApiService apiService = RetrofitClient.getApiService();
        Call<JwtAuthResponse> call = apiService.loginUser(loginRequest);

        // 3. Make the API call
        call.enqueue(new Callback<JwtAuthResponse>() {
            @Override
            public void onResponse(Call<JwtAuthResponse> call, Response<JwtAuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // *** SUCCESS! ***
                    Log.d(TAG, "Login Successful");
                    JwtAuthResponse authResponse = response.body();

                    // 4. SAVE THE TOKEN
                    sessionManager.saveAuthToken(authResponse.getToken());

                    Toast.makeText(Login_page.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    // 5. GO TO USER INTERFACE
                    goToUserInterface();

                } else {
                    // Handle errors (wrong password, user not found)
                    Log.e(TAG, "Login failed: " + response.code());
                    Toast.makeText(Login_page.this, "Login failed. Check email or password.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JwtAuthResponse> call, Throwable t) {
                // Handle network failures (server down, no internet, wrong IP)
                Log.e(TAG, "Network Failure: " + t.getMessage());
                Toast.makeText(Login_page.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToUserInterface() {
        // This opens your User_Interface activity
        Intent intent = new Intent(Login_page.this, User_Interface.class);
        startActivity(intent);
        finish(); // Close login activity
    }
}