package com.example.salesmartnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RegisterActivity extends AppCompatActivity {

    EditText rFullName, rUserName, rPassword, rConfirmPass;
    ImageView rAddPhoto;
    Button btRegister;
    RelativeLayout RL1;
    ImageView bacKArrow;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            RL1.setVisibility(View.VISIBLE);
            bacKArrow.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initialize the view
        RL1 = findViewById(R.id.RL1_Register);
        bacKArrow = findViewById(R.id.image1_Register);
        rFullName = findViewById(R.id.ET1_Register);
        rUserName = findViewById(R.id.ET2_Register);
        rPassword = findViewById(R.id.ET3_Register);
        rConfirmPass = findViewById(R.id.ET4_Register);
        rAddPhoto = findViewById(R.id.addImage_Register);

        handler.postDelayed(runnable, 1000);

        bacKArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(goBack);
            }
        });

        btRegister = findViewById(R.id.button1_Register);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intentRegister);
            }
        });


        rAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the runtime permission from the phone
                ActivityCompat.requestPermissions();
            }
        });
    }
}
