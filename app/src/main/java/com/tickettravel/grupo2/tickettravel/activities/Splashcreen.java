package com.tickettravel.grupo2.tickettravel.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.tickettravel.grupo2.tickettravel.R;

public class Splashcreen extends AppCompatActivity {

    private LottieAnimationView lottie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashcreen);
        loadPreference();
    }

    private void loadPreference() {
        if(getSharedPreferences("userSesion",0).contains("user"))
        {
            SharedPreferences preferences= getSharedPreferences("userSesion", Context.MODE_PRIVATE);
            String name=preferences.getString("user",null);
            login(name);
        }
        else
        {Splashcreen();}
    }

    private void login(String name) {

        Intent toHome=new Intent(this,MainActivity.class );
        Bundle parametros = new Bundle();
        parametros.putString("name",name);
        toHome.putExtras(parametros);
        startActivity(toHome);
        finish();
    }

    private void Splashcreen(){
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        lottie = findViewById(R.id.lottieSplashcreem);
        lottie.playAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Splashcreen.this.startActivity(new Intent(Splashcreen.this,LoginActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                Splashcreen.this.finish();
            }
        },2500);
    }
}
