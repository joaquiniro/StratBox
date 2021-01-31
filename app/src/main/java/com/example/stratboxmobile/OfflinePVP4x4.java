package com.example.stratboxmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class OfflinePVP4x4 extends AppCompatActivity implements View.OnClickListener {

    Random r = new Random();
    int player = 0;

    private Button[][] buttons = new Button[9][9];
    String box[][] = new String[9][9];
    String checkbox[][] =new String[9][9];
    TextView playerTurnTxt;

    private CountDownTimer count;

    private TextView timerText;
    private long time = 30000; //timer 60sec
    private boolean timeRunning;

    private TextView p1point,p2point;
    int p1,p2;
    boolean scored;


    //winner variable
    private int winner=0;
    private String WinnerName = "";

    //to pass
    public String size = "4x4";

    public static MediaPlayer mediaPlayer;
    MainActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_pvp4x4);
        mediaPlayer = MediaPlayer.create(this,R.raw.ingame);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();

        timerText = findViewById(R.id.timerText);
        StartStop();



        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 8; j++) {
                String buttonID = "btn" + i + "" + j;
                //  Toast.makeText(MainActivity.this,buttonID,Toast.LENGTH_SHORT).show();
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);

            }
            player = r.nextInt(3 - 1) + 1;

            if (player == 2) {
                playerTurnTxt = findViewById(R.id.playerturn);
                playerTurnTxt.setText("Player 2 turn");
                playerTurnTxt.setTextColor(Color.parseColor("#a83242"));
                //Toast.makeText(OfflinePVP3x3.this, "Player " + (player)+" turn!", Toast.LENGTH_SHORT).show();
            } else if (player == 1) {
                playerTurnTxt = findViewById(R.id.playerturn);
                playerTurnTxt.setText("Player 1 turn");
                playerTurnTxt.setTextColor(Color.parseColor("#292fab"));
            }

        //    Toast.makeText(OfflinePVP4x4.this, "Player " + (player)+" turn!", Toast.LENGTH_SHORT).show();


        }

        for (int i = 1; i <= 8; i++) {
            if (i % 2 !=0) {
                for (int j = 1; j <= 8; j++) {
                    if (j % 2 != 0) {
                        buttons[i][j].setEnabled(false);
                    }
                }
            }
        }
        BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish")) {
                    //finishing the activity
                    finish();
                }
            }
        };
        registerReceiver(broadcast_reciever, new IntentFilter("finish"));



    }



    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
        Log.d("test2","game pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        StartStop();
        Log.d("test2","game Resume");
    }


    public void StartStop(){
        if(timeRunning){
            stopTimer();
            startTimer();
        }
        else{
            startTimer();
        }
    }

    public void startTimer(){
        count = new CountDownTimer(time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                RotatePlayer();
                time = 30000;
                startTimer();
            }
        }.start();
        timeRunning = true;
    }
    public void stopTimer(){
        count.cancel();
        timeRunning = true;
    }
    public void updateTimer(){
        int sec = (int) time %60000 / 1000;
        timerText.setText(String.valueOf(sec));
    }

    @Override
    public void onClick(View v) {
        if(v.isClickable()==false){

        }
        else if(v.isClickable()!=false){
            if (player == 1) {
                ((Button) v).setBackgroundResource(R.drawable.blue2);
                ((Button) v).setEnabled(false);
                switch (v.getId()) {

                    case R.id.btn01:
                        box[0][1] = "1";
                        break;
                    case R.id.btn03:
                        box[0][3] = "1";
                        break;
                    case R.id.btn05:
                        box[0][5] = "1";
                        break;
                    case R.id.btn07:
                        box[0][7] = "1";
                        break;



                    case R.id.btn10:
                        box[1][0] = "1";
                        break;
                    case R.id.btn12:
                        box[1][2] = "1";
                        break;
                    case R.id.btn14:
                        box[1][4] = "1";
                        break;
                    case R.id.btn16:
                        box[1][6] = "1";
                        break;
                    case R.id.btn18:
                        box[1][8] = "1";
                        break;

                    case R.id.btn21:
                        box[2][1] = "1";
                        break;
                    case R.id.btn23:
                        box[2][3] = "1";
                        break;
                    case R.id.btn25:
                        box[2][5] = "1";
                        break;
                    case R.id.btn27:
                        box[2][7] = "1";
                        break;


                    case R.id.btn30:
                        box[3][0] = "1";
                        break;
                    case R.id.btn32:
                        box[3][2] = "1";
                        break;
                    case R.id.btn34:
                        box[3][4] = "1";
                        break;
                    case R.id.btn36:
                        box[3][6] = "1";
                        break;
                    case R.id.btn38:
                        box[3][8] = "1";
                        break;

                    case R.id.btn41:
                        box[4][1] = "1";
                        break;
                    case R.id.btn43:
                        box[4][3] = "1";
                        break;
                    case R.id.btn45:
                        box[4][5] = "1";
                        break;
                    case R.id.btn47:
                        box[4][7] = "1";
                        break;


                    case R.id.btn50:
                        box[5][0] = "1";
                        break;
                    case R.id.btn52:
                        box[5][2] = "1";
                        break;
                    case R.id.btn54:
                        box[5][4] = "1";
                        break;
                    case R.id.btn56:
                        box[5][6] = "1";
                        break;
                    case R.id.btn58:
                        box[5][8] = "1";
                        break;

                    case R.id.btn61:
                        box[6][1] = "1";
                        break;
                    case R.id.btn63:
                        box[6][3] = "1";
                        break;
                    case R.id.btn65:
                        box[6][5] = "1";
                        break;
                    case R.id.btn67:
                    box[6][7] = "1";
                        break;

                    case R.id.btn70:
                        box[7][0] = "1";
                        break;
                    case R.id.btn72:
                        box[7][2] = "1";
                        break;
                    case R.id.btn74:
                        box[7][4] = "1";
                        break;
                    case R.id.btn76:
                        box[7][6] = "1";
                        break;
                    case R.id.btn78:
                        box[7][8] = "1";
                        break;

                    case R.id.btn81:
                        box[8][1] = "1";
                        break;
                    case R.id.btn83:
                        box[8][3] = "1";
                        break;
                    case R.id.btn85:
                        box[8][5] = "1";
                        break;
                    case R.id.btn87:
                        box[8][7] = "1";
                        break;



                }
            } else if (player == 2) {
                ((Button) v).setBackgroundResource(R.drawable.red2);
                ((Button) v).setEnabled(false);
                switch (v.getId()) {
                    case R.id.btn01:
                        box[0][1] = "1";
                        break;
                    case R.id.btn03:
                        box[0][3] = "1";
                        break;
                    case R.id.btn05:
                        box[0][5] = "1";
                        break;
                    case R.id.btn07:
                        box[0][7] = "1";
                        break;



                    case R.id.btn10:
                        box[1][0] = "1";
                        break;
                    case R.id.btn12:
                        box[1][2] = "1";
                        break;
                    case R.id.btn14:
                        box[1][4] = "1";
                        break;
                    case R.id.btn16:
                        box[1][6] = "1";
                        break;
                    case R.id.btn18:
                        box[1][8] = "1";
                        break;

                    case R.id.btn21:
                        box[2][1] = "1";
                        break;
                    case R.id.btn23:
                        box[2][3] = "1";
                        break;
                    case R.id.btn25:
                        box[2][5] = "1";
                        break;
                    case R.id.btn27:
                        box[2][7] = "1";
                        break;


                    case R.id.btn30:
                        box[3][0] = "1";
                        break;
                    case R.id.btn32:
                        box[3][2] = "1";
                        break;
                    case R.id.btn34:
                        box[3][4] = "1";
                        break;
                    case R.id.btn36:
                        box[3][6] = "1";
                        break;
                    case R.id.btn38:
                        box[3][8] = "1";
                        break;

                    case R.id.btn41:
                        box[4][1] = "1";
                        break;
                    case R.id.btn43:
                        box[4][3] = "1";
                        break;
                    case R.id.btn45:
                        box[4][5] = "1";
                        break;
                    case R.id.btn47:
                        box[4][7] = "1";
                        break;


                    case R.id.btn50:
                        box[5][0] = "1";
                        break;
                    case R.id.btn52:
                        box[5][2] = "1";
                        break;
                    case R.id.btn54:
                        box[5][4] = "1";
                        break;
                    case R.id.btn56:
                        box[5][6] = "1";
                        break;
                    case R.id.btn58:
                        box[5][8] = "1";
                        break;

                    case R.id.btn61:
                        box[6][1] = "1";
                        break;
                    case R.id.btn63:
                        box[6][3] = "1";
                        break;
                    case R.id.btn65:
                        box[6][5] = "1";
                        break;
                    case R.id.btn67:
                        box[6][7] = "1";
                        break;

                    case R.id.btn70:
                        box[7][0] = "1";
                        break;
                    case R.id.btn72:
                        box[7][2] = "1";
                        break;
                    case R.id.btn74:
                        box[7][4] = "1";
                        break;
                    case R.id.btn76:
                        box[7][6] = "1";
                        break;
                    case R.id.btn78:
                        box[7][8] = "1";
                        break;

                    case R.id.btn81:
                        box[8][1] = "1";
                        break;
                    case R.id.btn83:
                        box[8][3] = "1";
                        break;
                    case R.id.btn85:
                        box[8][5] = "1";
                        break;
                    case R.id.btn87:
                        box[8][7] = "1";
                        break;
                }
            }
        }



        checkForBox();
        RotatePlayer();
        time = 30000;
        StartStop();
        checkWinner();



        //Toast.makeText(MainActivity.this,String.valueOf(getID),Toast.LENGTH_SHORT).show();


    }

    public void checkForBox() {

        //check box1

        for (int i = 1; i <= 8; i++) {
            if (i %2 != 0) {
                for (int j = 1; j <= 8; j++) {
                    if (j % 2 != 0) {
                        if ((box[i - 1][j] == "1") && (box[i + 1][j] == "1") && (box[i][j - 1] == "1") && (box[i][j + 1]) == "1") {
                            if(checkbox[i][j]!="x"){
                                if (player == 1) {

                                    buttons[i][j].setBackgroundResource(R.drawable.blue);
                                    checkbox[i][j]="x";
                                    p1point = findViewById(R.id.p1point);
                                    p1=p1+1;
                                    p1point.setText("Player 1: "+p1);
                                    scored = true;
                                } else if (player == 2) {
                                    buttons[i][j].setBackgroundResource(R.drawable.red);
                                    checkbox[i][j]="x";
                                    p2point = findViewById(R.id.p2point);
                                    p2=p2+1;
                                    p2point.setText("Player 2: "+p2);
                                    scored = true;
                                }
                            }


                        }
                    }
                }
            }
        }


    }

    public void RotatePlayer() {
        if (scored!= true){
            if (player == 1) {
                player = 2;
                playerTurnTxt = findViewById(R.id.playerturn);
                playerTurnTxt.setText("Player 2 turn");
                playerTurnTxt.setTextColor(Color.parseColor("#a83242"));
                //Toast.makeText(OfflinePVP3x3.this, "Player " + (player)+" turn!", Toast.LENGTH_SHORT).show();
            } else if (player == 2) {
                player = 1;
                playerTurnTxt = findViewById(R.id.playerturn);
                playerTurnTxt.setText("Player 1 turn");
                playerTurnTxt.setTextColor(Color.parseColor("#292fab"));

            }
        } else {
            scored = false;
        }
    }
    public void checkWinner(){
        if(p1>=9){
            winner =1;
        }
        else if(p2>=9){
            winner =2;
        }

        if(winner !=0){
            if(winner ==1){
                WinnerName = "Player 1";
            }
            else{
                WinnerName = "Player 2";
            }
            popupWindow();

        }
    }

    public void popupWindow(){
        Intent intent = new Intent(OfflinePVP4x4.this, popUpActivityOffline.class);
        intent.putExtra("Winner_Name",WinnerName);
        intent.putExtra("size",size);
        mediaPlayer.release();
        mediaPlayer = null;
        stopTimer();
        timerText.setText("30");
        startActivity(intent);
        resetVariables();
        //  finish();
    }
    public void resetVariables() {
        p1 = 0;
        p2 = 0;

        for (int i = 1; i <=8; i++) {
            if (i % 2 != 0) {
                for (int j = 1; j <=8; j++) {
                    if (j % 2 != 0) {
                        checkbox[i][j] = " ";
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        mediaPlayer.release();
        mediaPlayer = null;
        mediaPlayer = null;

        finish();
        mActivity.mediaPlayer.start();
    }

}
