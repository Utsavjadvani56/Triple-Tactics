package com.example.tripletactics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripletactics.databinding.ActivityLandingPageBinding;

public class LandingPage extends AppCompatActivity {

    ActivityLandingPageBinding bind; // Initialize View binding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        // Initialize view
        bind = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        // Let's Begin button's onclick event
        bind.btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check if either player name is empty or not
                if(bind.edPlayer1.getText().toString().equals("") || bind.edPlayer2.getText().toString().equals("") ){
                    Toast.makeText(LandingPage.this, "Please Enter Players Names!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(LandingPage.this,MainActivity.class);

                    // Pass the player names to Main page
                    intent.putExtra("p1",bind.edPlayer1.getText().toString());
                    intent.putExtra("p2",bind.edPlayer2.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}