package com.example.ruralconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.mlkit.nl.translate.TranslateLanguage;

public class User_Interface extends AppCompatActivity implements TranslationHelper.TranslationCallback {

    private TranslationHelper translationHelper;
    private Spinner languageSpinner;
    private TextView textView6, textView7;
    private ExtendedFloatingActionButton floatingActionButton3, floatingActionButton4, floatingActionButton5, floatingActionButton6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_interface);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        translationHelper = new TranslationHelper();

        languageSpinner = findViewById(R.id.language_spinner);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        floatingActionButton3 = findViewById(R.id.floatingActionButton3);
        floatingActionButton4 = findViewById(R.id.floatingActionButton4);
        floatingActionButton5 = findViewById(R.id.floatingActionButton5);
        floatingActionButton6 = findViewById(R.id.floatingActionButton6);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                translatePage(selectedLanguage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void translatePage(String selectedLanguage) {
        String targetLanguage;
        switch (selectedLanguage) {
            case "Hindi":
                targetLanguage = TranslateLanguage.HINDI;
                break;
            case "Urdu":
                targetLanguage = TranslateLanguage.URDU;
                break;
            default:
                targetLanguage = TranslateLanguage.ENGLISH;
                break;
        }

        translationHelper.createTranslator(TranslateLanguage.ENGLISH, targetLanguage, this);

        translationHelper.translate(textView6.getText().toString(), new TranslationHelper.TranslationCallback() {
            @Override
            public void onTranslationSuccess(String translatedText) {
                textView6.setText(translatedText);
            }

            @Override
            public void onTranslationFailure(String error) {
                Toast.makeText(User_Interface.this, error, Toast.LENGTH_SHORT).show();
            }
        });
        translationHelper.translate(textView7.getText().toString(), new TranslationHelper.TranslationCallback() {
            @Override
            public void onTranslationSuccess(String translatedText) {
                textView7.setText(translatedText);
            }

            @Override
            public void onTranslationFailure(String error) {
                Toast.makeText(User_Interface.this, error, Toast.LENGTH_SHORT).show();
            }
        });
        translationHelper.translate(floatingActionButton3.getText().toString(), new TranslationHelper.TranslationCallback() {
            @Override
            public void onTranslationSuccess(String translatedText) {
                floatingActionButton3.setText(translatedText);
            }

            @Override
            public void onTranslationFailure(String error) {
                Toast.makeText(User_Interface.this, error, Toast.LENGTH_SHORT).show();
            }
        });
        translationHelper.translate(floatingActionButton4.getText().toString(), new TranslationHelper.TranslationCallback() {
            @Override
            public void onTranslationSuccess(String translatedText) {
                floatingActionButton4.setText(translatedText);
            }

            @Override
            public void onTranslationFailure(String error) {
                Toast.makeText(User_Interface.this, error, Toast.LENGTH_SHORT).show();
            }
        });
        translationHelper.translate(floatingActionButton5.getText().toString(), new TranslationHelper.TranslationCallback() {
            @Override
            public void onTranslationSuccess(String translatedText) {
                floatingActionButton5.setText(translatedText);
            }

            @Override
            public void onTranslationFailure(String error) {
                Toast.makeText(User_Interface.this, error, Toast.LENGTH_SHORT).show();
            }
        });
        translationHelper.translate(floatingActionButton6.getText().toString(), new TranslationHelper.TranslationCallback() {
            @Override
            public void onTranslationSuccess(String translatedText) {
                floatingActionButton6.setText(translatedText);
            }

            @Override
            public void onTranslationFailure(String error) {
                Toast.makeText(User_Interface.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openProfilepage(View view){
        Toast.makeText(this, "Opening Profile Page....", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Profile_page.class);
        startActivity(intent);
    }
    public void openComplaint(View view){
        Toast.makeText(this, "Opening Complaints", Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent(this, Complaints_List.class);
        startActivity(intent2);
    }
    public void openProgress(View view){
        Toast.makeText(this, "Opening Progress...", Toast.LENGTH_SHORT).show();
        Intent intent3 = new Intent(this, Progress.class);
        startActivity(intent3);
    }
    public void openFeedback(View view){
        Toast.makeText(this, "Opening Feedback page...", Toast.LENGTH_SHORT).show();
        Intent intent4 = new Intent(this, Feedback.class);
        startActivity(intent4);
    }
    public void openHelp(View view){
        Toast.makeText(this, "Opening Help Page...", Toast.LENGTH_SHORT).show();
        Intent intent5 = new Intent(this, Help.class);
        startActivity(intent5);
    }
    public void openNewComp(View View){
        Toast.makeText(this, "Opening...", Toast.LENGTH_SHORT).show();
        Intent intent6 = new Intent(this, Register_Complaint.class);
        startActivity(intent6);
    }

    @Override
    public void onTranslationSuccess(String translatedText) {

    }

    @Override
    public void onTranslationFailure(String error) {

    }
}