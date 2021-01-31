package com.example.stratboxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Settings extends AppCompatActivity {
    public static final String EXTRA_NUMBER = "com.example.stratbox.EXTRA_NUMBER";
    private Button back, musicy, musicn, soundfxy, soundfxn;
    private SoundPool soundPool;
    private int buttonpress;
    MainActivity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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

        buttonpress = soundPool.load(this, R.raw.button,1);


        back = findViewById(R.id.back);
        musicy = findViewById(R.id.musicy);
        musicn = findViewById(R.id.musicn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                YoYo.with(Techniques.Bounce)
                        .duration(1000)
                        .repeat(0)
                        .playOn(back);
                soundPool.play(buttonpress, 1,1, 0,0,1);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        musicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Bounce)
                        .duration(1000)
                        .repeat(0)
                        .playOn(musicy);
                soundPool.play(buttonpress, 1,1, 0,0,1);
                mActivity.mediaPlayer.start();
            }
        });

        musicn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Bounce)
                        .duration(1000)
                        .repeat(0)
                        .playOn(musicn);
                soundPool.play(buttonpress, 1,1, 0,0,1);
                mActivity.mediaPlayer.pause();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void onWindowFocusChanged(boolean hasFocus){
        if(hasFocus){
            YoYo.with(Techniques.Pulse)
                    .duration(3000)
                    .repeat(-1)
                    .playOn(back);
            YoYo.with(Techniques.Pulse)
                    .duration(3000)
                    .repeat(-1)
                    .playOn(musicy);
            YoYo.with(Techniques.Pulse)
                    .duration(3000)
                    .repeat(-1)
                    .playOn(musicn);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }
}
