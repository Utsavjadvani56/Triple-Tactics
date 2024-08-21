package com.example.tripletactics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tripletactics.databinding.ActivityLandingPageBinding;

public class LandingPage extends AppCompatActivity {

    ActivityLandingPageBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        bind = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(bind.edPlayer1.getText().toString().equals("") || bind.edPlayer2.getText().toString().equals("") ){
                    Toast.makeText(LandingPage.this, "Please Enter Players Names!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(LandingPage.this,MainActivity.class);
                    intent.putExtra("p1",bind.edPlayer1.getText().toString());
                    intent.putExtra("p2",bind.edPlayer2.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}