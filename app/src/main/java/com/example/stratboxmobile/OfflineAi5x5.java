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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OfflineAi5x5 extends AppCompatActivity implements View.OnClickListener {
    Random r = new Random();
    int player = 0;


    //Computer move variables
    int computerMoveRow=0;

    private Button[][] buttons = new Button[11][11];
    String box[][] = new String[11][11];
    String checkbox[][] =new String[11][11];

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
    public String size = "PVC5x5";

    //computer moves
    List<Integer> computerMove;
    int computerRandomTime;

    //int[] computerMove = {1, 3 ,5};
    public static MediaPlayer mediaPlayer;
    MainActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_ai5x5);

        mediaPlayer = MediaPlayer.create(this,R.raw.ingame);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();

        timerText = findViewById(R.id.timerText);
        StartStop();


        //computer moves
        computerMove = new ArrayList<>();
        computerMove.add(1);
        computerMove.add(3);
        computerMove.add(5);
        computerMove.add(7);

        computerMove.add(10);
        computerMove.add(12);
        computerMove.add(14);
        computerMove.add(16);
        computerMove.add(18);

        computerMove.add(21);
        computerMove.add(23);
        computerMove.add(25);
        computerMove.add(27);

        computerMove.add(30);
        computerMove.add(32);
        computerMove.add(34);
        computerMove.add(36);
        computerMove.add(38);

        computerMove.add(41);
        computerMove.add(43);
        computerMove.add(45);
        computerMove.add(47);

        computerMove.add(50);
        computerMove.add(52);
        computerMove.add(54);
        computerMove.add(56);
        computerMove.add(58);

        computerMove.add(61);
        computerMove.add(63);
        computerMove.add(65);
        computerMove.add(67);

        computerMove.add(70);
        computerMove.add(72);
        computerMove.add(74);
        computerMove.add(76);
        computerMove.add(78);

        computerMove.add(81);
        computerMove.add(83);
        computerMove.add(85);
        computerMove.add(87);

        computerMove.add(90);
        computerMove.add(92);
        computerMove.add(94);
        computerMove.add(96);
        computerMove.add(98);
        computerMove.add(910);

        computerMove.add(101);
        computerMove.add(103);
        computerMove.add(105);
        computerMove.add(107);
        computerMove.add(109);





        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                String buttonID = "btn" + i + "" + j;
                //  Toast.makeText(MainActivity.this,buttonID,Toast.LENGTH_SHORT).show();
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);

            }
            //player = r.nextInt(3 - 1) + 1;
            player = 1;

            if (player == 1) {
                playerTurnTxt = findViewById(R.id.playerturn);
                playerTurnTxt.setText("Player 1 turn");
                playerTurnTxt.setTextColor(Color.parseColor("#292fab"));
            }

            //     Toast.makeText(OfflinePVP3x3.this, "Player " + (player)+" turn!", Toast.LENGTH_SHORT).show();
        }
        for (int i = 1; i <= 10; i++) {
            if (i % 2 !=0) {
                for (int j = 1; j <= 10; j++) {
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
        Log.d("time",""+sec +" " + computerRandomTime);
        timerText.setText(String.valueOf(sec));

        if(player ==2){
            if(computerRandomTime==5){
                if(sec == 25){
                    ComputerMove();

                }
            }
            else if(computerRandomTime==4){
                if(sec == 26){
                    ComputerMove();

                }
            }
            else if(computerRandomTime==3){
                if(sec == 27){
                    ComputerMove();

                }
            }
            else if(computerRandomTime==2){
                if(sec == 28) {
                    ComputerMove();

                }
            }
        }


    }

    @Override
    public void onClick(View v) {
        if(v.isClickable()==false){
        }
        else if(v.isClickable()!=false){
            if (player == 1) {

                ((Button) v).setBackgroundResource(R.drawable.blue2);
                ((Button) v).setClickable(false);
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
                    case R.id.btn09:
                        box[0][9] = "1";
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
                    case R.id.btn29:
                        box[2][9] = "1";
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
                    case R.id.btn49:
                        box[4][9] = "1";
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
                    case R.id.btn69:
                        box[6][9] = "1";
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
                    case R.id.btn89:
                        box[8][9] = "1";
                        break;
                    case R.id.btn101:
                        box[10][1] = "1";
                        break;
                    case R.id.btn103:
                        box[10][3] = "1";
                        break;
                    case R.id.btn105:
                        box[10][5] = "1";
                        break;
                    case R.id.btn107:
                        box[10][7] = "1";
                        break;
                    case R.id.btn109:
                        box[10][9] = "1";
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
                    case R.id.btn110:
                        box[1][10] = "1";
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
                    case R.id.btn310:
                        box[3][10] = "1";
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
                    case R.id.btn510:
                        box[5][10] = "1";
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
                    case R.id.btn710:
                        box[7][10] = "1";
                        break;
                    case R.id.btn90:
                        box[9][0] = "1";
                        break;
                    case R.id.btn92:
                        box[9][2] = "1";
                        break;
                    case R.id.btn94:
                        box[9][4] = "1";
                        break;
                    case R.id.btn96:
                        box[9][6] = "1";
                        break;
                    case R.id.btn98:
                        box[9][8] = "1";
                        break;
                    case R.id.btn910:
                        box[9][10] = "1";
                        break;
                }
            }
        }



        checkForBox();
        RotatePlayer();
        ComputerMoveTime();
        time = 30000;
        StartStop();
        checkWinner();



        //Toast.makeText(MainActivity.this,String.valueOf(getID),Toast.LENGTH_SHORT).show();


    }


    public void ComputerMoveTime(){
        computerRandomTime = r.nextInt(5 -2) +2;
    }






    String ComputerMoveAgain = "true";

    public void ComputerMove(){

        // computerMoveRow - move of computer

        Log.d("ComputerSize" , ""+computerMove.size());
        String skip = " ";

        if(computerMove.size()==0){
            checkWinner();
        }
        else if(computerMove.size()>2){

            computerMoveRow = computerMove.get(r.nextInt(computerMove.size()));
            String ComputerMove = String.valueOf(computerMoveRow);



            Button button01 = (Button) findViewById(R.id.btn01);
            Button button03 = (Button) findViewById(R.id.btn03);
            Button button05 = (Button) findViewById(R.id.btn05);
            Button button07 = (Button) findViewById(R.id.btn07);
            Button button09 = (Button) findViewById(R.id.btn09);

            Button button10 = (Button) findViewById(R.id.btn10);
            Button button12 = (Button) findViewById(R.id.btn12);
            Button button14 = (Button) findViewById(R.id.btn14);
            Button button16 = (Button) findViewById(R.id.btn16);
            Button button18 = (Button) findViewById(R.id.btn18);
            Button button110 = (Button) findViewById(R.id.btn110);

            Button button21 = (Button) findViewById(R.id.btn21);
            Button button23 = (Button) findViewById(R.id.btn23);
            Button button25 = (Button) findViewById(R.id.btn25);
            Button button27 = (Button) findViewById(R.id.btn27);
            Button button29 = (Button) findViewById(R.id.btn29);

            Button button30 = (Button) findViewById(R.id.btn30);
            Button button32 = (Button) findViewById(R.id.btn32);
            Button button34 = (Button) findViewById(R.id.btn34);
            Button button36 = (Button) findViewById(R.id.btn36);
            Button button38 = (Button) findViewById(R.id.btn38);
            Button button310 = (Button) findViewById(R.id.btn310);

            Button button41 = (Button) findViewById(R.id.btn41);
            Button button43 = (Button) findViewById(R.id.btn43);
            Button button45 = (Button) findViewById(R.id.btn45);
            Button button47 = (Button) findViewById(R.id.btn47);
            Button button49 = (Button) findViewById(R.id.btn49);

            Button button50 = (Button) findViewById(R.id.btn50);
            Button button52 = (Button) findViewById(R.id.btn52);
            Button button54 = (Button) findViewById(R.id.btn54);
            Button button56 = (Button) findViewById(R.id.btn56);
            Button button58 = (Button) findViewById(R.id.btn58);
            Button button510 = (Button) findViewById(R.id.btn510);

            Button button61 = (Button) findViewById(R.id.btn61);
            Button button63 = (Button) findViewById(R.id.btn63);
            Button button65 = (Button) findViewById(R.id.btn65);
            Button button67 = (Button) findViewById(R.id.btn67);
            Button button69 = (Button) findViewById(R.id.btn69);

            Button button70 = (Button) findViewById(R.id.btn70);
            Button button72 = (Button) findViewById(R.id.btn72);
            Button button74 = (Button) findViewById(R.id.btn74);
            Button button76 = (Button) findViewById(R.id.btn76);
            Button button78 = (Button) findViewById(R.id.btn78);
            Button button710 = (Button) findViewById(R.id.btn710);

            Button button81 = (Button) findViewById(R.id.btn81);
            Button button83 = (Button) findViewById(R.id.btn83);
            Button button85 = (Button) findViewById(R.id.btn85);
            Button button87 = (Button) findViewById(R.id.btn87);
            Button button89 = (Button) findViewById(R.id.btn89);

            Button button90 = (Button) findViewById(R.id.btn90);
            Button button92 = (Button) findViewById(R.id.btn92);
            Button button94 = (Button) findViewById(R.id.btn94);
            Button button96 = (Button) findViewById(R.id.btn96);
            Button button98 = (Button) findViewById(R.id.btn98);
            Button button910 = (Button) findViewById(R.id.btn910);

            Button button101 = (Button) findViewById(R.id.btn101);
            Button button103 = (Button) findViewById(R.id.btn103);
            Button button105 = (Button) findViewById(R.id.btn105);
            Button button107 = (Button) findViewById(R.id.btn107);
            Button button109 = (Button) findViewById(R.id.btn109);
            //top bottom left right

            //row 0
            // 0 1 1 1  ...  01 21 10 12

//                if((box[0][1] != "1") && (box[2][1] == "1") && (box[1][0] == "1") && (box[1][2]) == "1") {
//                    if (box[0][1] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[0][1] != "1") {
//                        box[0][1] = "1";
//                        ComputerMoveAgain = "false";
//                        button01.setBackgroundResource(R.drawable.red2);
//                        button01.setClickable(false);
//                        skip = "true";
//                    }
//
//                }
//                // 0 1 1 1 ... 03 23 12 14
//                else if((box[0][3] != "1") && (box[2][3] == "1") && (box[1][2] == "1") && (box[1][4]) == "1") {
//                    if (box[0][3] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[0][3] != "1") {
//                        box[0][3] = "1";
//                        ComputerMoveAgain = "false";
//                        button03.setBackgroundResource(R.drawable.red2);
//                        button03.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 0 1 1 1 ... 05 25 14 16
//                else if((box[0][5] != "1") && (box[2][5] == "1") && (box[1][4] == "1") && (box[1][6]) == "1") {
//                    if (box[0][5] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[0][5] != "1") {
//                        box[0][5] = "1";
//                        ComputerMoveAgain = "false";
//                        button05.setBackgroundResource(R.drawable.red2);
//                        button05.setClickable(false);
//                        skip = "true";
//                    }
//                }
//
//
//                // row 1
//                // 1 1 0 1  ...  01 21 10 12
//                else if((box[0][1] == "1") && (box[2][1] == "1") && (box[1][0] != "1") && (box[1][2]) == "1") {
//                    if (box[1][0] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[1][0] != "1") {
//                        box[1][0] = "1";
//                        ComputerMoveAgain = "false";
//                        button10.setBackgroundResource(R.drawable.red2);
//                        button10.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 1 1 1 0 ... 03 23 12 14
//                else if((box[0][3] == "1") && (box[2][3] == "1") && (box[1][2] == "1") && (box[1][4]) != "1") {
//                    if (box[1][2] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[1][2] != "1") {
//                        box[1][2] = "1";
//                        ComputerMoveAgain = "false";
//                        button12.setBackgroundResource(R.drawable.red2);
//                        button12.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 1 1 0 1 ... 03 23 12 14
//                else if((box[0][3] == "1") && (box[2][3] == "1") && (box[1][2] != "1") && (box[1][4]) == "1") {
//                    if (box[1][2] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[1][2] != "1") {
//                        box[1][2] = "1";
//                        ComputerMoveAgain = "false";
//                        button12.setBackgroundResource(R.drawable.red2);
//                        button12.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 1 1 0 1 ... 05 25 14 16
//                else if((box[0][5] == "1") && (box[2][5] == "1") && (box[1][4] != "1") && (box[1][6]) == "1") {
//                    if (box[1][4] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[1][4] != "1") {
//                        box[1][4] = "1";
//                        ComputerMoveAgain = "false";
//                        button14.setBackgroundResource(R.drawable.red2);
//                        button14.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 1 1 1 0 ... 05 25 14 16
//                else if((box[0][5] == "1") && (box[2][5] == "1") && (box[1][4] == "1") && (box[1][6]) != "1") {
//                    if (box[1][6] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[1][6] != "1") {
//                        box[1][6] = "1";
//                        ComputerMoveAgain = "false";
//                        button16.setBackgroundResource(R.drawable.red2);
//                        button16.setClickable(false);
//                        skip = "true";
//                    }
//                }
//
//
//                //row2
//                // 0 1 1 1  ...  21 41 30 32
//                else if((box[2][1] != "1") && (box[4][1] == "1") && (box[3][0] == "1") && (box[3][2]) == "1") {
//                    if (box[2][1] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[2][1] != "1") {
//                        box[2][1] = "1";
//                        ComputerMoveAgain = "false";
//                        button21.setBackgroundResource(R.drawable.red2);
//                        button21.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 0 1 1 1  ...  23 43 32 34
//                else if((box[2][3] != "1") && (box[4][3] == "1") && (box[3][2] == "1") && (box[3][4]) == "1") {
//                    if (box[2][3] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[2][3] != "1") {
//                        box[2][3] = "1";
//                        ComputerMoveAgain = "false";
//                        button23.setBackgroundResource(R.drawable.red2);
//                        button23.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 0 1 1 1  ...  23 43 32 34
//                else if((box[2][5] != "1") && (box[4][5] == "1") && (box[3][4] == "1") && (box[3][6]) == "1") {
//                    if (box[2][5] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[2][5] != "1") {
//                        box[2][5] = "1";
//                        ComputerMoveAgain = "false";
//                        button25.setBackgroundResource(R.drawable.red2);
//                        button25.setClickable(false);
//                        skip = "true";
//                    }
//                }
//
//
//                // row 3
//                // 1 1 0 1  ...  21 41 30 32
//                else if((box[2][1] == "1") && (box[4][1] == "1") && (box[3][0] != "1") && (box[3][2]) == "1") {
//                    if (box[3][0] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[3][0] != "1") {
//                        box[3][0] = "1";
//                        ComputerMoveAgain = "false";
//                        button30.setBackgroundResource(R.drawable.red2);
//                        button30.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 1 1 1 0  ...  23 43 32 34
//                else if((box[2][3] == "1") && (box[4][3] == "1") && (box[3][2] == "1") && (box[3][4]) != "1") {
//                    if (box[3][2] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[3][2] != "1") {
//                        box[3][2] = "1";
//                        ComputerMoveAgain = "false";
//                        button32.setBackgroundResource(R.drawable.red2);
//                        button32.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 1 1 0 1  ...  23 43 32 34
//                else if((box[2][3] == "1") && (box[4][3] == "1") && (box[3][2] != "1") && (box[3][4]) == "1") {
//                    if (box[3][2] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[3][2] != "1") {
//                        box[3][2] = "1";
//                        ComputerMoveAgain = "false";
//                        button32.setBackgroundResource(R.drawable.red2);
//                        button32.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 1 1 0 1  ...  25 45 34 36
//                else if((box[2][5] == "1") && (box[4][5] == "1") && (box[3][4] != "1") && (box[3][6]) == "1") {
//                    if (box[3][4] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[3][4] != "1") {
//                        box[3][4] = "1";
//                        ComputerMoveAgain = "false";
//                        button34.setBackgroundResource(R.drawable.red2);
//                        button34.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 1 1 1 0  ...  25 45 34 36
//                else if((box[2][5] == "1") && (box[4][5] == "1") && (box[3][4] == "1") && (box[3][6]) != "1") {
//                    if (box[3][6] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[3][6] != "1") {
//                        box[3][6] = "1";
//                        ComputerMoveAgain = "false";
//                        button36.setBackgroundResource(R.drawable.red2);
//                        button36.setClickable(false);
//                        skip = "true";
//                    }
//                }
//
//                //row 4
//                // 0 1 1 1  ...  41 61 50 52
//                else if((box[4][1] != "1") && (box[6][1] == "1") && (box[5][0] == "1") && (box[5][2]) == "1") {
//                    if (box[4][1] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[4][1] != "1") {
//                        box[4][1] = "1";
//                        ComputerMoveAgain = "false";
//                        button41.setBackgroundResource(R.drawable.red2);
//                        button41.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 0 1 1 1  ...  43 63 52 54
//                else if((box[4][3] != "1") && (box[6][3] == "1") && (box[5][2] == "1") && (box[5][4]) == "1") {
//                    if (box[4][3] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[4][3] != "1") {
//                        box[4][3] = "1";
//                        ComputerMoveAgain = "false";
//                        button43.setBackgroundResource(R.drawable.red2);
//                        button43.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 0 1 1 1  ...  45 65 54 56
//                else if((box[4][5] != "1") && (box[6][5] == "1") && (box[5][4] == "1") && (box[5][6]) == "1") {
//                    if (box[4][5] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[4][5] != "1") {
//                        box[4][5] = "1";
//                        ComputerMoveAgain = "false";
//                        button45.setBackgroundResource(R.drawable.red2);
//                        button45.setClickable(false);
//                        skip = "true";
//                    }
//                }
//
//                // row 5
//                // 1 1 0 1  ...  41 61 50 52
//                else if((box[4][1] == "1") && (box[6][1] == "1") && (box[5][0] != "1") && (box[5][2]) == "1") {
//                    if (box[5][0] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[5][0] != "1") {
//                        box[5][0] = "1";
//                        ComputerMoveAgain = "false";
//                        button50.setBackgroundResource(R.drawable.red2);
//                        button50.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 1 1 0 1  ...  41 61 50 52
//                else if((box[4][1] == "1") && (box[6][1] == "1") && (box[5][0] == "1") && (box[5][2]) != "1") {
//                    if (box[5][2] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[5][2] != "1") {
//                        box[5][2] = "1";
//                        ComputerMoveAgain = "false";
//                        button52.setBackgroundResource(R.drawable.red2);
//                        button52.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 1 1 0 1  ...  43 63 52 54
//                else if((box[4][3] == "1") && (box[6][3] == "1") && (box[5][2] != "1") && (box[5][4]) == "1") {
//                    if (box[5][2] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[5][2] != "1") {
//                        box[5][2] = "1";
//                        ComputerMoveAgain = "false";
//                        button52.setBackgroundResource(R.drawable.red2);
//                        button52.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 1 1 0 1  ...  45 65 54 56
//                else if((box[4][5] == "1") && (box[6][5] == "1") && (box[5][4] != "1") && (box[5][6]) == "1") {
//                    if (box[5][4] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[5][4] != "1") {
//                        box[5][4] = "1";
//                        ComputerMoveAgain = "false";
//                        button54.setBackgroundResource(R.drawable.red2);
//                        button54.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 1 1 0 1  ...  45 65 54 56
//                else if((box[4][5] == "1") && (box[6][5] == "1") && (box[5][4] == "1") && (box[5][6]) != "1") {
//                    if (box[5][6] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[5][6] != "1") {
//                        box[5][6] = "1";
//                        ComputerMoveAgain = "false";
//                        button56.setBackgroundResource(R.drawable.red2);
//                        button56.setClickable(false);
//                        skip = "true";
//                    }
//                }
//
//                //row 6
//                // 1 0 1 1  ...  41 61 50 52
//                else if((box[4][1] == "1") && (box[6][1] != "1") && (box[5][0] == "1") && (box[5][2]) == "1") {
//                    if (box[6][1] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[6][1] != "1") {
//                        box[6][1] = "1";
//                        ComputerMoveAgain = "false";
//                        button61.setBackgroundResource(R.drawable.red2);
//                        button61.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 1 0 1 1  ...  43 63 52 54
//                else if((box[4][3] == "1") && (box[6][3] != "1") && (box[5][2] == "1") && (box[5][4]) == "1") {
//                    if (box[6][3] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[6][3] != "1") {
//                        box[6][3] = "1";
//                        ComputerMoveAgain = "false";
//                        button63.setBackgroundResource(R.drawable.red2);
//                        button63.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                // 1 0 1 1  ...  45 65 54 56
//                else if((box[4][5] == "1") && (box[6][5] != "1") && (box[5][4] == "1") && (box[5][6]) == "1") {
//                    if (box[6][5] == "1") {
//                        ComputerMoveAgain = "true";
//                    } else if (box[6][5] != "1") {
//                        box[6][5] = "1";
//                        ComputerMoveAgain = "false";
//                        button65.setBackgroundResource(R.drawable.red2);
//                        button65.setClickable(false);
//                        skip = "true";
//                    }
//                }
//                else{
//                    skip = "false";
//                }
            Log.d("noneSkip", skip);




            if(skip == " "){
                switch (ComputerMove) {
                    case "1":
                        if (box[0][1] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[0][1] != "1") {
                            box[0][1] = "1";
                            ComputerMoveAgain = "false";
                            button01.setBackgroundResource(R.drawable.red2);
                            button01.setClickable(false);
                        }
                        break;
                    case "3":
                        if (box[0][3] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[0][3] != "1") {
                            box[0][3] = "1";
                            ComputerMoveAgain = "false";
                            button03.setBackgroundResource(R.drawable.red2);
                            button03.setClickable(false);
                        }
                        break;
                    case "5":
                        if (box[0][5] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[0][5] != "1") {
                            box[0][5] = "1";
                            ComputerMoveAgain = "false";
                            button05.setBackgroundResource(R.drawable.red2);
                            button05.setClickable(false);
                        }
                        break;
                    case "7":
                        if (box[0][7] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[0][7] != "1") {
                            box[0][7] = "1";
                            ComputerMoveAgain = "false";
                            button07.setBackgroundResource(R.drawable.red2);
                            button07.setClickable(false);
                        }
                        break;
                    case "9":
                        if (box[0][9] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[0][9] != "1") {
                            box[0][9] = "1";
                            ComputerMoveAgain = "false";
                            button09.setBackgroundResource(R.drawable.red2);
                            button09.setClickable(false);
                        }
                        break;
                    case "10":
                        if (box[1][0] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[1][0] != "1") {
                            box[1][0] = "1";
                            ComputerMoveAgain = "false";
                            button10.setBackgroundResource(R.drawable.red2);
                            button10.setClickable(false);
                        }
                        break;
                    case "12":
                        if (box[1][2] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[1][2] != "1") {
                            box[1][2] = "1";
                            ComputerMoveAgain = "false";
                            button12.setBackgroundResource(R.drawable.red2);
                            button12.setClickable(false);
                        }
                        break;
                    case "14":
                        if (box[1][4] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[1][4] != "1") {
                            box[1][4] = "1";
                            ComputerMoveAgain = "false";
                            button14.setBackgroundResource(R.drawable.red2);
                            button14.setClickable(false);
                        }
                        break;
                    case "16":
                        if (box[1][6] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[1][6] != "1") {
                            box[1][6] = "1";
                            ComputerMoveAgain = "false";
                            button16.setBackgroundResource(R.drawable.red2);
                            button16.setClickable(false);
                        }
                        break;
                    case "18":
                        if (box[1][8] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[1][8] != "1") {
                            box[1][8] = "1";
                            ComputerMoveAgain = "false";
                            button18.setBackgroundResource(R.drawable.red2);
                            button18.setClickable(false);
                        }
                        break;
                    case "110":
                        if (box[1][10] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[1][10] != "1") {
                            box[1][10] = "1";
                            ComputerMoveAgain = "false";
                            button110.setBackgroundResource(R.drawable.red2);
                            button110.setClickable(false);
                        }
                        break;
                    case "21":
                        if (box[2][1] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[2][1] != "1") {
                            box[2][1] = "1";
                            ComputerMoveAgain = "false";
                            button21.setBackgroundResource(R.drawable.red2);
                            button21.setClickable(false);
                        }
                        break;
                    case "23":
                        if (box[2][3] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[2][3] != "1") {
                            box[2][3] = "1";
                            ComputerMoveAgain = "false";
                            button23.setBackgroundResource(R.drawable.red2);
                            button23.setClickable(false);
                        }
                        break;
                    case "25":
                        if (box[2][5] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[2][5] != "1") {
                            box[2][5] = "1";
                            ComputerMoveAgain = "false";
                            button25.setBackgroundResource(R.drawable.red2);
                            button25.setClickable(false);
                        }
                        break;
                    case "27":
                        if (box[2][7] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[2][7] != "1") {
                            box[2][7] = "1";
                            ComputerMoveAgain = "false";
                            button27.setBackgroundResource(R.drawable.red2);
                            button27.setClickable(false);
                        }
                        break;
                    case "29":
                        if (box[2][9] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[2][9] != "1") {
                            box[2][9] = "1";
                            ComputerMoveAgain = "false";
                            button29.setBackgroundResource(R.drawable.red2);
                            button29.setClickable(false);
                        }
                        break;
                    case "30":
                        if (box[3][0] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[3][0] != "1") {
                            box[3][0] = "1";
                            ComputerMoveAgain = "false";
                            button30.setBackgroundResource(R.drawable.red2);
                            button30.setClickable(false);
                        }
                        break;
                    case "32":
                        if (box[3][2] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[3][2] != "1") {
                            box[3][2] = "1";
                            ComputerMoveAgain = "false";
                            button32.setBackgroundResource(R.drawable.red2);
                            button32.setClickable(false);
                        }
                        break;
                    case "34":
                        if (box[3][4] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[3][4] != "1") {
                            box[3][4] = "1";
                            ComputerMoveAgain = "false";
                            button34.setBackgroundResource(R.drawable.red2);
                            button34.setClickable(false);
                        }
                        break;
                    case "36":
                        if (box[3][6] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[3][6] != "1") {
                            box[3][6] = "1";
                            ComputerMoveAgain = "false";
                            button36.setBackgroundResource(R.drawable.red2);
                            button36.setClickable(false);
                        }
                        break;
                    case "38":
                        if (box[3][8] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[3][8] != "1") {
                            box[3][8] = "1";
                            ComputerMoveAgain = "false";
                            button38.setBackgroundResource(R.drawable.red2);
                            button38.setClickable(false);
                        }
                        break;
                    case "310":
                        if (box[3][10] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[3][10] != "1") {
                            box[3][10] = "1";
                            ComputerMoveAgain = "false";
                            button310.setBackgroundResource(R.drawable.red2);
                            button310.setClickable(false);
                        }
                        break;
                    case "41":
                        if (box[4][1] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[4][1] != "1") {
                            box[4][1] = "1";
                            ComputerMoveAgain = "false";
                            button41.setBackgroundResource(R.drawable.red2);
                            button41.setClickable(false);
                        }
                        break;
                    case "43":
                        if (box[4][3] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[4][3] != "1") {
                            box[4][3] = "1";
                            ComputerMoveAgain = "false";
                            button43.setBackgroundResource(R.drawable.red2);
                            button43.setClickable(false);
                        }
                        break;
                    case "45":
                        if (box[4][5] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[4][5] != "1") {
                            box[4][5] = "1";
                            ComputerMoveAgain = "false";
                            button45.setBackgroundResource(R.drawable.red2);
                            button45.setClickable(false);
                        }
                        break;
                    case "47":
                        if (box[4][7] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[4][7] != "1") {
                            box[4][7] = "1";
                            ComputerMoveAgain = "false";
                            button47.setBackgroundResource(R.drawable.red2);
                            button47.setClickable(false);
                        }
                        break;
                    case "49":
                        if (box[4][9] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[4][9] != "1") {
                            box[4][9] = "1";
                            ComputerMoveAgain = "false";
                            button49.setBackgroundResource(R.drawable.red2);
                            button49.setClickable(false);
                        }
                        break;
                    case "50":
                        if (box[5][0] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[5][0] != "1") {
                            box[5][0] = "1";
                            ComputerMoveAgain = "false";
                            button50.setBackgroundResource(R.drawable.red2);
                            button50.setClickable(false);
                        }
                        break;
                    case "52":
                        if (box[5][2] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[5][2] != "1") {
                            box[5][2] = "1";
                            ComputerMoveAgain = "false";
                            button52.setBackgroundResource(R.drawable.red2);
                            button52.setClickable(false);
                        }
                        break;
                    case "54":
                        if (box[5][4] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[5][4] != "1") {
                            box[5][4] = "1";
                            ComputerMoveAgain = "false";
                            button54.setBackgroundResource(R.drawable.red2);
                            button54.setClickable(false);
                        }
                        break;
                    case "56":
                        if (box[5][6] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[5][6] != "1") {
                            box[5][6] = "1";
                            ComputerMoveAgain = "false";
                            button56.setBackgroundResource(R.drawable.red2);
                            button56.setClickable(false);
                        }
                        break;
                    case "58":
                        if (box[5][8] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[5][8] != "1") {
                            box[5][8] = "1";
                            ComputerMoveAgain = "false";
                            button58.setBackgroundResource(R.drawable.red2);
                            button58.setClickable(false);
                        }
                        break;
                    case "510":
                        if (box[5][10] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[5][10] != "1") {
                            box[5][10] = "1";
                            ComputerMoveAgain = "false";
                            button510.setBackgroundResource(R.drawable.red2);
                            button510.setClickable(false);
                        }
                        break;
                    case "61":
                        if (box[6][1] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[6][1] != "1") {
                            box[6][1] = "1";
                            ComputerMoveAgain = "false";
                            button61.setBackgroundResource(R.drawable.red2);
                            button61.setClickable(false);
                        }
                        break;
                    case "63":
                        if (box[6][3] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[6][3] != "1") {
                            box[6][3] = "1";
                            ComputerMoveAgain = "false";
                            button63.setBackgroundResource(R.drawable.red2);
                            button63.setClickable(false);
                        }
                        break;
                    case "65":
                        if (box[6][5] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[6][5] != "1") {
                            box[6][5] = "1";
                            ComputerMoveAgain = "false";
                            button65.setBackgroundResource(R.drawable.red2);
                            button65.setClickable(false);
                        }
                        break;
                    case "67":
                        if (box[6][7] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[6][7] != "1") {
                            box[6][7] = "1";
                            ComputerMoveAgain = "false";
                            button67.setBackgroundResource(R.drawable.red2);
                            button67.setClickable(false);
                        }
                        break;
                    case "69":
                        if (box[6][9] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[6][9] != "1") {
                            box[6][9] = "1";
                            ComputerMoveAgain = "false";
                            button69.setBackgroundResource(R.drawable.red2);
                            button69.setClickable(false);
                        }
                        break;
                    case "70":
                        if (box[7][0] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[7][0] != "1") {
                            box[7][0] = "1";
                            ComputerMoveAgain = "false";
                            button70.setBackgroundResource(R.drawable.red2);
                            button70.setClickable(false);
                        }
                        break;
                    case "72":
                        if (box[7][2] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[7][2] != "1") {
                            box[7][2] = "1";
                            ComputerMoveAgain = "false";
                            button72.setBackgroundResource(R.drawable.red2);
                            button72.setClickable(false);
                        }
                        break;
                    case "74":
                        if (box[7][4] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[7][4] != "1") {
                            box[7][4] = "1";
                            ComputerMoveAgain = "false";
                            button74.setBackgroundResource(R.drawable.red2);
                            button74.setClickable(false);
                        }
                        break;
                    case "76":
                        if (box[7][6] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[7][6] != "1") {
                            box[7][6] = "1";
                            ComputerMoveAgain = "false";
                            button76.setBackgroundResource(R.drawable.red2);
                            button76.setClickable(false);
                        }
                        break;
                    case "78":
                        if (box[7][8] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[7][8] != "1") {
                            box[7][8] = "1";
                            ComputerMoveAgain = "false";
                            button78.setBackgroundResource(R.drawable.red2);
                            button78.setClickable(false);
                        }
                        break;
                    case "710":
                        if (box[7][10] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[7][10] != "1") {
                            box[7][10] = "1";
                            ComputerMoveAgain = "false";
                            button710.setBackgroundResource(R.drawable.red2);
                            button710.setClickable(false);
                        }
                        break;
                    case "81":
                        if (box[8][1] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[8][1] != "1") {
                            box[8][1] = "1";
                            ComputerMoveAgain = "false";
                            button81.setBackgroundResource(R.drawable.red2);
                            button81.setClickable(false);
                        }
                        break;
                    case "83":
                        if (box[8][3] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[8][3] != "1") {
                            box[8][3] = "1";
                            ComputerMoveAgain = "false";
                            button83.setBackgroundResource(R.drawable.red2);
                            button83.setClickable(false);
                        }
                        break;
                    case "85":
                        if (box[8][5] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[8][5] != "1") {
                            box[8][5] = "1";
                            ComputerMoveAgain = "false";
                            button85.setBackgroundResource(R.drawable.red2);
                            button85.setClickable(false);
                        }
                        break;
                    case "87":
                        if (box[8][7] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[8][7] != "1") {
                            box[8][7] = "1";
                            ComputerMoveAgain = "false";
                            button87.setBackgroundResource(R.drawable.red2);
                            button87.setClickable(false);
                        }
                        break;
                    case "89":
                        if (box[8][9] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[8][9] != "1") {
                            box[8][9] = "1";
                            ComputerMoveAgain = "false";
                            button89.setBackgroundResource(R.drawable.red2);
                            button89.setClickable(false);
                        }
                        break;
                    case "90":
                        if (box[9][0] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[9][0] != "1") {
                            box[9][0] = "1";
                            ComputerMoveAgain = "false";
                            button90.setBackgroundResource(R.drawable.red2);
                            button90.setClickable(false);
                        }
                        break;
                    case "92":
                        if (box[9][2] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[9][2] != "1") {
                            box[9][2] = "1";
                            ComputerMoveAgain = "false";
                            button92.setBackgroundResource(R.drawable.red2);
                            button92.setClickable(false);
                        }
                        break;
                    case "94":
                        if (box[9][4] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[9][4] != "1") {
                            box[9][4] = "1";
                            ComputerMoveAgain = "false";
                            button94.setBackgroundResource(R.drawable.red2);
                            button94.setClickable(false);
                        }
                        break;
                    case "96":
                        if (box[9][6] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[9][6] != "1") {
                            box[9][6] = "1";
                            ComputerMoveAgain = "false";
                            button96.setBackgroundResource(R.drawable.red2);
                            button96.setClickable(false);
                        }
                        break;
                    case "98":
                        if (box[9][8] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[9][8] != "1") {
                            box[9][8] = "1";
                            ComputerMoveAgain = "false";
                            button98.setBackgroundResource(R.drawable.red2);
                            button98.setClickable(false);
                        }
                        break;
                    case "910":
                        if (box[9][10] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[9][10] != "1") {
                            box[9][10] = "1";
                            ComputerMoveAgain = "false";
                            button910.setBackgroundResource(R.drawable.red2);
                            button910.setClickable(false);
                        }
                        break;

                    case "101":
                        if (box[10][1] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[10][1] != "1") {
                            box[10][1] = "1";
                            ComputerMoveAgain = "false";
                            button101.setBackgroundResource(R.drawable.red2);
                            button101.setClickable(false);
                        }
                        break;
                    case "103":
                        if (box[10][3] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[10][3] != "1") {
                            box[10][3] = "1";
                            ComputerMoveAgain = "false";
                            button103.setBackgroundResource(R.drawable.red2);
                            button103.setClickable(false);
                        }
                        break;
                    case "105":
                        if (box[10][5] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[10][5] != "1") {
                            box[10][5] = "1";
                            ComputerMoveAgain = "false";
                            button105.setBackgroundResource(R.drawable.red2);
                            button105.setClickable(false);
                        }
                        break;
                    case "107":
                        if (box[10][7] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[10][7] != "1") {
                            box[10][7] = "1";
                            ComputerMoveAgain = "false";
                            button107.setBackgroundResource(R.drawable.red2);
                            button107.setClickable(false);
                        }
                        break;
                    case "109":
                        if (box[10][9] == "1") {
                            ComputerMoveAgain = "true";
                        } else if (box[10][9] != "1") {
                            box[10][9] = "1";
                            ComputerMoveAgain = "false";
                            button109.setBackgroundResource(R.drawable.red2);
                            button109.setClickable(false);
                        }
                        break;



                    default:
                        ComputerMove = "false";
                        //Toast.makeText(this, "NO MOVES", Toast.LENGTH_SHORT).show();
                }
                Log.d("skip", skip);
            }

            skip = "false";
            Log.d("ComputerMove", ComputerMove);
            computerMove.remove(new Integer(computerMoveRow));

            ComputerMove2();
        }

    }
    public void ComputerMove2(){
        if(ComputerMoveAgain=="true"){
            ComputerMove();
        }
        else if(ComputerMoveAgain =="false"){
            checkForBox();
            RotatePlayer();
            Log.d("player" , "player: " + player);
            checkWinner();
        }
    }





    public void checkForBox() {

        //check box1
        Log.d("checkForBox",""+player);
        for (int i = 1; i <= 10; i++) {
            if (i %2 != 0) {
                for (int j = 1; j <= 10; j++) {
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
                                    p2point.setText("Computer: "+p2);
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
                playerTurnTxt.setText("Computer turn");
                playerTurnTxt.setTextColor(Color.parseColor("#a83242"));
                //Toast.makeText(OfflinePVP3x3.this, "Player " + (player)+" turn!", Toast.LENGTH_SHORT).show();
            } else if (player == 2) {
                player = 1;
                playerTurnTxt = findViewById(R.id.playerturn);
                playerTurnTxt.setText("Player 1 turn");
                playerTurnTxt.setTextColor(Color.parseColor("#292fab"));

            }
        } else{
            if(player == 2){
                ComputerMove();
                scored = false;
            }
            scored = false;
        }
    }
    public void checkWinner(){
        if(p1>=13){
            winner =1;
        }
        else if(p2>=13){
            winner =2;
        }

        if(winner !=0){
            if(winner ==1){
                WinnerName = "Player 1";
            }
            else{
                WinnerName = "Computer";
            }
            popupWindow();

        }
    }


    public void popupWindow(){
        String gameMode ="PVC";
        Intent intent = new Intent(OfflineAi5x5.this, popUpActivityOffline.class);
        intent.putExtra("Winner_Name",WinnerName);
        intent.putExtra("size",size);
        intent.putExtra("GameMode",gameMode);
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
        finish();
        mActivity.mediaPlayer.start();
    }
}




