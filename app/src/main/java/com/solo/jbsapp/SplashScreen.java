package com.solo.jbsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import android.os.Handler;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView gif = ((ImageView) findViewById(R.id.gif));

        Glide.with(this)
                .load("https://camo.githubusercontent.com/bfb2b63eeb7b21626c1a896e6e58a55838135977ce8b5d7ee13a60080b56a1e7/68747470733a2f2f6173736574732d76322e6c6f7474696566696c65732e636f6d2f612f30336364633665302d313138622d313165652d626630382d3037643838613934316362642f696969774730764a514e2e676966")
                .into(gif);

        new Handler().postDelayed(this:: abrirTela, 3000);
    }

    private void abrirTela(){
        Intent rota = new Intent(this, LoginActivity.class);
        startActivity(rota);
        finish();
    }
}