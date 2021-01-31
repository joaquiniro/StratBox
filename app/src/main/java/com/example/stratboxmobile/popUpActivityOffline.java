package com.example.stratboxmobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class popUpActivityOffline extends AppCompatActivity {

    private TextView playername;
    String WinnerName;
    String size;
    String gameMode;
    Button button1;
    Button button2;
    MainActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);
        size = " ";
        WinnerName = " ";

        WinnerName = getIntent().getExtras().get("Winner_Name").toString();
        size = getIntent().getExtras().get("size").toString();



        playername = findViewById(R.id.playername);
        playername.setText(WinnerName + " WON !");



        //cancel all touch outside dialogbox
        setFinishOnTouchOutside(false);

        Button button1 = (Button)findViewById(R.id.restart);
        Button button2 = (Button)findViewById(R.id.mainmenu);

        Log.d("pass2" , size);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSize();
            }

        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(popUpActivityOffline.this, MainActivity.class);
//                startActivity(intent);
                Intent intent = new Intent("finish");
                sendBroadcast(intent);
                finish();
                mActivity.mediaPlayer.start();
            }
        });
    }
    public void checkSize(){
        Intent intent = new Intent(popUpActivityOffline.this,OfflinePVP3x3.class);

        if(size.equals("PVC3x3")){
            intent = new Intent(popUpActivityOffline.this,OfflineAi3x3.class);
        }
        else if(size.equals("PVC4x4")){
            intent = new Intent(popUpActivityOffline.this,OfflineAi4x4.class);
        }
        else if(size.equals("PVC5x5")){
            intent = new Intent(popUpActivityOffline.this,OfflineAi5x5.class);
        }
        else if(size.equals("3x3")){
            intent = new Intent(popUpActivityOffline.this, OfflinePVP3x3.class);
        }
        else if(size.equals("5x5")){
            intent = new Intent(popUpActivityOffline.this, OfflinePVP5x5.class);
        }
        else if(size.equals("4x4")){
            intent = new Intent(popUpActivityOffline.this, OfflinePVP4x4.class);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent("finish");
        sendBroadcast(intent);
        finish();
        mActivity.mediaPlayer.start();
    }
}