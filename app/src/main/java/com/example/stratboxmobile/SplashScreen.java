 package com.example.stratboxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

 public class SplashScreen extends AppCompatActivity {
     private SoundPool soundPool;
     private int buttonpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else{
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC,0);
        }

        buttonpress = soundPool.load(this, R.raw.opening,1);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent move = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(move);

                finish();
            }
        }, 3000);
    }
}
