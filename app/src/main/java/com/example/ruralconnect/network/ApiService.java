package com.example.ruralconnect.network;

import com.example.ruralconnect.model.*;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // --- Auth ---
    @POST("api/auth/register")
    Call<String> registerUser(@Body RegisterRequest registerRequest); // Backend sends a plain string

    @POST("api/auth/login")
    Call<JwtAuthResponse> loginUser(@Body LoginRequest loginRequest);

    // --- Products ---
    @GET("api/products")
    Call<List<Product>> getAllProducts();

    // --- Schemes ---
    @GET("api/schemes")
    Call<List<Scheme>> getAllSchemes();

    // --- Complaints (Authenticated Calls) ---
    @POST("api/complaints")
    Call<Complaint> createComplaint(
            @Header("Authorization") String token, // This is how you send the JWT
            @Body Complaint complaint
    );

    @GET("api/complaints/my-complaints")
    Call<List<Complaint>> getMyComplaints(@Header("Authorization") String token);

    // --- Feedback (Authenticated) ---
    @POST("api/feedback")
    Call<Feedback> createFeedback(
            @Header("Authorization") String token,
            @Body Feedback feedback
    );

    // --- Help Center (Public) ---
    @GET("api/faqs")
    Call<List<Faq>> getAllFaqs();

    @GET("api/tutorials")
    Call<List<VideoTutorial>> getAllTutorials();

    // --- Admin Calls (for your admin app) ---
    @GET("api/complaints")
    Call<List<Complaint>> getAllComplaintsAdmin(@Header("Authorization") String token);

    @PUT("api/complaints/{id}/status")
    Call<Complaint> updateComplaintStatus(
            @Header("Authorization") String token,
            @Path("id") Long complaintId,
            @Body java.util.Map<String, String> statusUpdate // For { "status": "In Progress" }
    );
}