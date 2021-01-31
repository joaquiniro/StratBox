package com.example.stratboxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Game extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button back;
    private Button play;
    private SoundPool soundPool;
    private int buttonpress;
    private RadioGroup radioGroup;
    private RadioButton Computer, Pvp, Online;
    private String sizes ="";
    private String GameMode = " ";
    MainActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GameMode = "PVC";

        Spinner spinner = findViewById(R.id.rcsize);
        radioGroup = findViewById(R.id.radiogroup);
        play = findViewById(R.id.play);
        radioGroup = findViewById(R.id.radiogroup);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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

        buttonpress = soundPool.load(this, R.raw.buttonpress,1);

        back = findViewById(R.id.back);

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

        radioGroup =  findViewById(R.id.radiogroup);
        Computer = (RadioButton) findViewById(R.id.pvc);
        Pvp = (RadioButton) findViewById(R.id.pvp);
        Online = (RadioButton) findViewById(R.id.online);




        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.pvc){
                    GameMode = "PVC";
                    Log.d("gamemode", "PVC");
                }
                else if(checkedId==R.id.pvp){
                    GameMode = "PVP";
                    Log.d("gamemode", "PVP");
                }
                else if(checkedId==R.id.online){
                    GameMode = "ONLINE";
                    Log.d("gamemode", "ONLINE");
                }


            }
        });


        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("modeandsize","Gamemode: "+GameMode + "Size: " + sizes);
                if(GameMode =="PVC" && sizes == "3x3"){
                    Log.d("Computer", "Clicked");
                    finish();
                    Intent intent = new Intent(Game.this , OfflineAi3x3.class);
                    startActivity(intent);
                    mActivity.mediaPlayer.pause();
                }
                else if(GameMode =="PVC" && sizes == "4x4"){
                    Log.d("Computer", "Clicked");
                    finish();
                    Intent intent = new Intent(Game.this , OfflineAi4x4.class);
                    startActivity(intent);
                    mActivity.mediaPlayer.pause();
                }
                else if(GameMode =="PVC" && sizes == "5x5"){
                    Log.d("Computer", "Clicked");
                    finish();
                    Intent intent = new Intent(Game.this , OfflineAi5x5.class);
                    startActivity(intent);
                    mActivity.mediaPlayer.pause();
                }
                else if(GameMode == "PVP" && sizes == "5x5"){
                    finish();
                    Intent intent = new Intent(Game.this, OfflinePVP5x5.class);
                    startActivity(intent);
                    mActivity.mediaPlayer.pause();

                }
                else if(GameMode == "PVP" && sizes == "3x3"){
                    finish();
                    Intent intent = new Intent(Game.this, OfflinePVP3x3.class);
                    startActivity(intent);
                    mActivity.mediaPlayer.pause();

                }
                else if(GameMode == "PVP" && sizes == "4x4"){
                    finish();
                    Intent intent = new Intent(Game.this, OfflinePVP4x4.class);
                    startActivity(intent);
                    mActivity.mediaPlayer.pause();



                }
                else if(GameMode == "ONLINE"){
                    finish();
                    Intent intent = new Intent(Game.this, OnlineMenu.class);
                    startActivity(intent);
                    mActivity.mediaPlayer.pause();
                }

                else if(sizes == " "){
                    Toast.makeText(Game.this, "PLEASE SELECT ZONE SIZE! ",Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(Game.this,"PLEASE SELECT GAME MODE! ",Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                sizes = " ";
                Log.d("size" , "no size");
                break;
            case 1:
                sizes = "3x3";
                Log.d("size" , "3x3");
                break;
            case 2:
                sizes = "4x4";
                Log.d("size" , "4x4");
                break;
            case 3:
                sizes = "5x5";
                Log.d("size" , "5x5");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(Game.this,"PLEASE SELECT SIZES! ",Toast.LENGTH_SHORT).show();
    }
}
