package com.example.stratboxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class popUpActivityOnline extends AppCompatActivity {


    private TextView playername;
    String WinnerName;
    Button button1;
    Button button2;
    MainActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_online);

        WinnerName = getIntent().getExtras().get("Winner_Name").toString();

        playername = findViewById(R.id.playername);
        playername.setText(WinnerName + " WON !");



        //cancel all touch outside dialogbox
        setFinishOnTouchOutside(false);

        Button button1 = (Button)findViewById(R.id.restart);
        Button button2 = (Button)findViewById(R.id.mainmenu);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(popUpActivityOnline.this, OnlineMenu.class);
                startActivity(intent);
                finish();
            }

        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(popUpActivityOnline.this,MainActivity.class );
//                startActivity(intent);
                Intent intent = new Intent("finish");
                sendBroadcast(intent);
                finish();
                mActivity.mediaPlayer.start();

            }
        });
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
