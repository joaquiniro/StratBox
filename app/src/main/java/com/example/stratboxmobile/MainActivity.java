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
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity  {
    private Button start, guide, settings, quit;
    private int buttonpress;
    private ImageView logo;
    private SoundPool soundPool;
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this,R.raw.stratboxbg);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();


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

        start = findViewById(R.id.start);
        guide = findViewById(R.id.guide);
        settings = findViewById(R.id.settings);
        quit = findViewById(R.id.quit);
        logo = findViewById(R.id.logo);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Bounce)
                        .duration(100)
                        .repeat(1)
                        .playOn(start);
                soundPool.play(buttonpress, 1,1, 0,0,1);
                Intent move = new Intent(MainActivity.this, Game.class);
                startActivity(move);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Bounce)
                        .duration(100)
                        .repeat(1)
                        .playOn(guide);
                soundPool.play(buttonpress, 1,1, 0,0,1);
                Intent move = new Intent(MainActivity.this, Guide.class);
                startActivity(move);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Bounce)
                        .duration(100)
                        .repeat(1)
                        .playOn(settings);
                soundPool.play(buttonpress, 1,1, 0,0,1);
                Intent move = new Intent(MainActivity.this, Settings.class);
                startActivity(move);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPool.play(buttonpress, 1,1, 0,0,1);
                finish();
            }
        });
    }

    public void onWindowFocusChanged(boolean hasFocus){
        if(hasFocus){
            YoYo.with(Techniques.Pulse)
                    .duration(2000)
                    .repeat(-1)
                    .playOn(start);
            YoYo.with(Techniques.Pulse)
                    .duration(2000)
                    .repeat(-1)
                    .playOn(guide);
            YoYo.with(Techniques.Pulse)
                    .duration(2000)
                    .repeat(-1)
                    .playOn(settings);
            YoYo.with(Techniques.Pulse)
                    .duration(2000)
                    .repeat(-1)
                    .playOn(quit);
            YoYo.with(Techniques.Bounce)
                    .duration(2000)
                    .repeat(-1)
                    .playOn(logo);

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
