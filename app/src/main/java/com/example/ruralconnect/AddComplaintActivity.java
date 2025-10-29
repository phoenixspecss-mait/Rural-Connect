package com.example.ruralconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddComplaintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);

        EditText title = findViewById(R.id.edit_text_title);
        EditText description = findViewById(R.id.edit_text_description);
        Button saveButton = findViewById(R.id.button_save);

        saveButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("title", title.getText().toString());
            resultIntent.putExtra("description", description.getText().toString());
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
