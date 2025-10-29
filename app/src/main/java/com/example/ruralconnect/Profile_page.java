package com.example.ruralconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Profile_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void openHomePage(View view) {
        Toast.makeText(this, "Opening Home Page....", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, User_Interface.class);
        startActivity(intent);
    }

    public void openNewComp(View View) {
        Toast.makeText(this, "Opening...", Toast.LENGTH_SHORT).show();
        Intent intent6 = new Intent(this, Register_Complaint.class);
        startActivity(intent6);
    }
    public void openAbout(View View) {
        Toast.makeText(this, "Opening...", Toast.LENGTH_SHORT).show();
        Intent intent7 = new Intent(this, About.class);
        startActivity(intent7);
    }
    public void openPrivacy(View View) {
        Toast.makeText(this, "Opening...", Toast.LENGTH_SHORT).show();
        Intent intent8 = new Intent(this, Privacy_Policy.class);
        startActivity(intent8);
    }
    public void openTerms(View View) {
        Toast.makeText(this, "Opening...", Toast.LENGTH_SHORT).show();
        Intent intent9 = new Intent(this, Terms_Conditions.class);
        startActivity(intent9);
    }
}
