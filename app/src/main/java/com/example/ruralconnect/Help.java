package com.example.ruralconnect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class Help extends AppCompatActivity {

    private Translator englishHindiTranslator;
    private TextView tvTitle;
    private Button btnFaqs, btnVideoTutorials, btnReportProblem, btnContactUs, btnTranslate;

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

        // Create an English-Hindi translator:
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.HINDI)
                .build();
        englishHindiTranslator = Translation.getClient(options);

        btnFaqs.setOnClickListener(v -> Toast.makeText(Help.this, "FAQs clicked", Toast.LENGTH_SHORT).show());

        btnVideoTutorials.setOnClickListener(v -> Toast.makeText(Help.this, "Video Tutorials clicked", Toast.LENGTH_SHORT).show());

        btnReportProblem.setOnClickListener(v -> Toast.makeText(Help.this, "Report a Problem clicked", Toast.LENGTH_SHORT).show());

        btnContactUs.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:support@example.com"));
            startActivity(intent);
        });

        btnTranslate.setOnClickListener(v -> translatePage());
    }

    private void translatePage() {
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        englishHindiTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(aVoid -> {
                    // Model downloaded successfully. Okay to start translating.
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
