package com.example.ruralconnect;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Feedback extends AppCompatActivity {

    private EditText editTextFeedback;
    private RatingBar ratingBar;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        editTextFeedback = findViewById(R.id.editTextFeedback);
        ratingBar = findViewById(R.id.ratingBar);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedbackText = editTextFeedback.getText().toString();
                float rating = ratingBar.getRating();

                // TODO: Implement your feedback submission logic here

                Toast.makeText(Feedback.this, "Feedback submitted! Thank you.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
