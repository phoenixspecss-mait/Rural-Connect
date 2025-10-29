package com.example.ruralconnect;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Progress extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textViewProgress;
    private TextView textViewStatus;
    private TextView statusLodged, statusInProgress, statusResolved;
    private Button buttonPrevious, buttonNext;

    private int currentStage = 0;
    private final String[] stages = {"Lodged", "In Progress", "Resolved"};
    private final int[] progressValues = {0, 50, 100};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        progressBar = findViewById(R.id.progressBar);
        textViewProgress = findViewById(R.id.textViewProgress);
        textViewStatus = findViewById(R.id.textViewStatus);
        statusLodged = findViewById(R.id.statusLodged);
        statusInProgress = findViewById(R.id.statusInProgress);
        statusResolved = findViewById(R.id.statusResolved);
        buttonPrevious = findViewById(R.id.buttonPrevious);
        buttonNext = findViewById(R.id.buttonNext);

        updateUI();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStage < stages.length - 1) {
                    currentStage++;
                    updateUI();
                }
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStage > 0) {
                    currentStage--;
                    updateUI();
                }
            }
        });
    }

    private void updateUI() {
        progressBar.setProgress(progressValues[currentStage]);
        textViewProgress.setText(progressValues[currentStage] + "%");
        textViewStatus.setText("Status: " + stages[currentStage]);

        // Reset all status TextViews
        statusLodged.setTypeface(null, Typeface.NORMAL);
        statusInProgress.setTypeface(null, Typeface.NORMAL);
        statusResolved.setTypeface(null, Typeface.NORMAL);

        statusLodged.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        statusInProgress.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray));
        statusResolved.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray));

        // Highlight the current stage
        switch (currentStage) {
            case 0:
                statusLodged.setTypeface(null, Typeface.BOLD);
                statusLodged.setTextColor(ContextCompat.getColor(this, R.color.black));
                break;
            case 1:
                statusInProgress.setTypeface(null, Typeface.BOLD);
                statusInProgress.setTextColor(ContextCompat.getColor(this, R.color.black));
                break;
            case 2:
                statusResolved.setTypeface(null, Typeface.BOLD);
                statusResolved.setTextColor(ContextCompat.getColor(this, R.color.black));
                break;
        }
    }
}
