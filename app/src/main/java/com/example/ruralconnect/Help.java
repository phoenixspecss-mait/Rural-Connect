package com.example.ruralconnect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ruralconnect.model.Faq;
import com.example.ruralconnect.model.VideoTutorial;
import com.example.ruralconnect.network.ApiService;
import com.example.ruralconnect.network.RetrofitClient;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Help extends AppCompatActivity {

    private static final String TAG = "HelpActivity"; // For logging

    private Translator englishHindiTranslator;
    private TextView tvTitle;
    private Button btnFaqs, btnVideoTutorials, btnReportProblem, btnContactUs, btnTranslate;

    // --- New variables to hold your API data ---
    private List<Faq> faqList;
    private List<VideoTutorial> videoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        tvTitle = findViewById(R.id.tvTitle);
        btnFaqs = findViewById(R.id.btnFaqs);
        btnVideoTutorials = findViewById(R.id.btnVideoTutorials);
        btnReportProblem = findViewById(R.id.btnReportProblem);
        btnContactUs = findViewById(R.id.btnContactUs);
        btnTranslate = findViewById(R.id.btnTranslate);

        // --- Your existing translator setup ---
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.HINDI)
                .build();
        englishHindiTranslator = Translation.getClient(options);

        // --- NEW: Fetch data from your backend ---
        fetchFaqs();
        fetchVideoTutorials();

        // --- UPDATED Listeners ---
        btnFaqs.setOnClickListener(v -> {
            if (faqList != null && !faqList.isEmpty()) {
                // We'll just show a Toast for now since you don't have a list activity
                Toast.makeText(Help.this, "Loaded " + faqList.size() + " FAQs", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Help.this, "Loading FAQs...", Toast.LENGTH_SHORT).show();
            }
        });

        btnVideoTutorials.setOnClickListener(v -> {
            if (videoList != null && !videoList.isEmpty()) {
                // We'll just show a Toast for now
                Toast.makeText(Help.this, "Loaded " + videoList.size() + " videos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Help.this, "Loading videos...", Toast.LENGTH_SHORT).show();
            }
        });

        btnReportProblem.setOnClickListener(v -> {
            Intent intent = new Intent(Help.this, Register_Complaint.class);
            startActivity(intent);
        });

        // --- Your existing listeners ---
        btnContactUs.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:support@example.com"));
            startActivity(intent);
        });

        btnTranslate.setOnClickListener(v -> translatePage());
    }

    // --- NEW: API Call to Fetch FAQs ---
    private void fetchFaqs() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Faq>> call = apiService.getAllFaqs();

        call.enqueue(new Callback<List<Faq>>() {
            @Override
            public void onResponse(Call<List<Faq>> call, Response<List<Faq>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    faqList = response.body();
                    Log.d(TAG, "Successfully fetched " + faqList.size() + " FAQs.");
                } else {
                    Log.e(TAG, "Failed to fetch FAQs: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<Faq>> call, Throwable t) {
                Log.e(TAG, "Network failure (FAQs): " + t.getMessage());
            }
        });
    }

    // --- NEW: API Call to Fetch Video Tutorials ---
    private void fetchVideoTutorials() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<VideoTutorial>> call = apiService.getAllTutorials();

        call.enqueue(new Callback<List<VideoTutorial>>() {
            @Override
            public void onResponse(Call<List<VideoTutorial>> call, Response<List<VideoTutorial>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    videoList = response.body();
                    Log.d(TAG, "Successfully fetched " + videoList.size() + " tutorials.");
                } else {
                    Log.e(TAG, "Failed to fetch tutorials: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<VideoTutorial>> call, Throwable t) {
                Log.e(TAG, "Network failure (Tutorials): " + t.getMessage());
            }
        });
    }

    // --- Your existing translation methods ---
    private void translatePage() {
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        englishHindiTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(aVoid -> {
                    translateText(tvTitle);
                    translateText(btnFaqs);
                    translateText(btnVideoTutorials);
                    translateText(btnReportProblem);
                    translateText(btnContactUs);
                    translateText(btnTranslate);
                })
                .addOnFailureListener(e -> Toast.makeText(Help.this, "Model download failed", Toast.LENGTH_SHORT).show());
    }

    private void translateText(TextView view) {
        englishHindiTranslator.translate(view.getText().toString())
                .addOnSuccessListener(view::setText)
                .addOnFailureListener(e -> Toast.makeText(Help.this, "Translation failed", Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        englishHindiTranslator.close();
    }
}