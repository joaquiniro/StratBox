package com.example.stratboxmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OnlineGame5x5 extends AppCompatActivity {



    TextView tvPlayer1, tvPlayer2, player1Point, player2Point;

    //variables from mainActivity
    String playerSession = "";
    String userName = "";
    String otherPlayer = "";
    String loginUID = "";
    String requestType = "";
    String myGameSign = "Blue";


    String p1Name, p2Name;

    //winner variable
    private String WinnerName = "";
    private int winner = 0;

    public boolean turnAgain;


    String getButtonID;
    Button button;

    //active player turn variable
    public int activePlayer;


    ArrayList<Integer> Player1 = new ArrayList<>();
    ArrayList<Integer> Player2 = new ArrayList<>();

    //player points variable
    public int P1points = 0;
    public int P2points = 0;

    int gameState = 0;


    //buttons varibales
    int tileCheck[][] = new int[7][11];
    private Button[][] buttons = new Button[7][11];


    //checking for box variable
    String checkbox[][] = new String[7][11];

    //firebase variables
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    //time variables
    private CountDownTimer count;
    private TextView timerText;
    private long time = 30000; //timer 60sec
    private boolean timeRunning = true;
    private String gameStarted = "";
    private String timeFinish = "";

    public static MediaPlayer mediaPlayer;
    MainActivity mActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game5x5);
        mediaPlayer = MediaPlayer.create(this,R.raw.ingame);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();

        resetVariables();

        //Toast.makeText(OnlineGameActivityActivity.this, winner + " " + WinnerName, Toast.LENGTH_SHORT).show();


        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 11; j++) {
                String buttonID = "btn" + i + "" + j;
                //  Toast.makeText(MainActivity.this,buttonID,Toast.LENGTH_SHORT).show();
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);

            }
        }


        //initialize variable from mainAct
        userName = getIntent().getExtras().get("user_name").toString();
        loginUID = getIntent().getExtras().get("login_uid").toString();
        otherPlayer = getIntent().getExtras().get("other_player").toString();
        requestType = getIntent().getExtras().get("request_type").toString();
        playerSession = getIntent().getExtras().get("player_session").toString();

        tvPlayer1 = (TextView) findViewById(R.id.tvPlayer1);
        tvPlayer2 = (TextView) findViewById(R.id.tvPlayer2);
        player1Point = (TextView) findViewById(R.id.player1point);
        player2Point = (TextView) findViewById(R.id.player2point);
        player1Point.setText(userName + ": ");
        player2Point.setText(otherPlayer + ": ");

        p1Name = userName;
        p2Name = otherPlayer;

        gameState = 1;

        if (requestType.equals("From")) {
            myGameSign = "Red";
            tvPlayer1.setText("Your turn");
            tvPlayer2.setText("Your turn");
            myRef.child("playing").child(playerSession).child("turn").setValue(otherPlayer);
            myRef.child("playing").child(playerSession).child("time1").setValue("30");
            myRef.child("playing").child(playerSession).child("time2").setValue("30");
            //Toast.makeText(OnlineGameActivityActivity.this,String.valueOf(userName),Toast.LENGTH_SHORT).show();
            //setEnableClick(true);
        } else {
            myGameSign = "Blue";
            tvPlayer1.setText(otherPlayer + "\'s turn");
            tvPlayer2.setText(otherPlayer + "\'s turn");
            myRef.child("playing").child(playerSession).child("turn").setValue(userName);
            myRef.child("playing").child(playerSession).child("time1").setValue("30");
            myRef.child("playing").child(playerSession).child("time2").setValue("30");
            //setEnableClick(false);
            //
        }


        myRef.child("playing").child(playerSession).child("time1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String time = (String) dataSnapshot.getValue();

//                    if (time.equals("0")) {
//
//                    } else {
//
//                    }
//                    timerText.setText(String.valueOf(time));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        myRef.child("playing").child(playerSession).child("time2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String time = (String) dataSnapshot.getValue();

//                    if (time.equals("0")) {
//
//                    } else {
//
//                    }
//                    timerText.setText(String.valueOf(time));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        myRef.child("playing").child(playerSession).child("turn").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String value = (String) dataSnapshot.getValue();

                    if (value.equals(userName)) {
                        tvPlayer1.setText("Your turn");
                        tvPlayer2.setText("Your turn");
                        setEnableClick(true);
                        activePlayer = 2;


                    } else if (value.equals(otherPlayer)) {
                        tvPlayer1.setText(otherPlayer + "\'s turn");
                        tvPlayer2.setText(otherPlayer + "\'s turn");
                        setEnableClick(false);
                        activePlayer = 1;
                        // stopTimer();


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        myRef.child("playing").child(playerSession).child("game").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    String ans = (String) dataSnapshot.getValue();
                    if (ans == null) {

                    } else {
                        timerText = findViewById(R.id.timerText);
                        changeTileColor(ans);
                        lockTile(ans);
                        checkForBox();
                        checkScore();
                        if(P1points==0 && P2points==0){
                            winner = 0;
                        }
                        Log.d("test3","winner: " +winner);
                        CheckWinner();


                        // Toast.makeText(OnlineGameActivityActivity.this, winner + " " + WinnerName, Toast.LENGTH_SHORT).show();
                        Log.d("test3", "The winner is: " + WinnerName);
                        //startTimer();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

//        myRef.child("playing").child(playerSession).child("game")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        try {
//                            Player1.clear();
//                            Player2.clear();
//                            //activePlayer = 2;
//                            HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
//                            //Toast.makeText(OnlineGameActivityActivity.this,"hello ",Toast.LENGTH_SHORT).show();
//
//
//
//                            if (map != null) {
//                                String value;
//                                String firstPlayer = userName;
//                                for (String key : map.keySet()) {
//                                    value = (String) map.get(key);
//                                    if (value.equals(userName)) {
//                                        //activePlayer = myGameSign.equals("X")?1:2;
//                                        activePlayer = 2;
//                                    } else {
//                                        //activePlayer = myGameSign.equals("X")?2:1;
//                                        activePlayer = 1;
//                                        checkForBox();
//                                    }
//                                    firstPlayer = value;
//                                    String[] splitID = key.split(":");
//                                    OtherPlayer(Integer.parseInt(splitID[1]));
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError error) {
//
//                    }
//                });


    }


//        myRef.child("playing").child(playerSession).child("game").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                String ans = (String) dataSnapshot.getValue();
//                Toast.makeText(OnlineGameActivityActivity.this,"hello " +ans,Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//
//

//    }


//    public void updateBoard(){
//
//            switch (getButtonID){
//                case "1": button.findViewById(R.id.btn01).setBackgroundResource(R.drawable.red2);
//                break;
//                case "2":button.findViewById(R.id.btn03).setBackgroundResource(R.drawable.red2);
//                    break;
//            }
//
//
//    }


//    public void StartStop(){
//        if(timeRunning){
//            stopTimer();
//        }
//        else{
//            startTimer();
////        }
//    }

//    public void startTimer(){
//        count = new CountDownTimer(time,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                time = millisUntilFinished;
//                updateTimer();
//            }
//
//            @Override
//            public void onFinish() {
//                stopTimer();
//                if(activePlayer ==2){
//                    stopTimer();
//                    time = 30000;
//                    myRef.child("playing").child(playerSession).child("time2").setValue("30");
//                }
//                else{
//                    stopTimer();
//                    time = 30000;
//                    timerText.setText("30");
//                    myRef.child("playing").child(playerSession).child("time1").setValue("30");
//                }
//
//
//            }
//        }.start();
//    }
//    public void stopTimer(){
//        count.cancel();
//        timerText.setText("30");
//      //  timeRunning = false;
//    }
//
//    public void updateTimer(){
//        if(activePlayer ==1 ){
//            int sec = (int) time %60000 / 1000;
//           // timerText.setText(String.valueOf(sec));
//            myRef.child("playing").child(playerSession).child("time1").setValue(String.valueOf(sec));
//        }
//        else if(activePlayer ==2){
//            int sec = (int) time %60000 / 1000;
//           // timerText.setText(String.valueOf(sec));
//            myRef.child("playing").child(playerSession).child("time2").setValue(String.valueOf(sec));
//        }
//
//    }
//
//    public void RotatePlayer(){
//
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.release();
        mediaPlayer = null;
        mActivity.mediaPlayer.start();
        myRef.child("playing").child(playerSession).removeValue();
        winner = 0;

        finish();

    }

    public void lockTile(String ans) {
        switch (ans) {
            case "block1":
                button = (Button) findViewById(R.id.btn01);
                button.setEnabled(false);
                tileCheck[0][1] = 1;
                break;
            case "block2":
                button = (Button) findViewById(R.id.btn03);
                button.setEnabled(false);
                tileCheck[0][3] = 1;
                break;
            case "block3":
                button = (Button) findViewById(R.id.btn05);
                button.setEnabled(false);
                tileCheck[0][5] = 1;
                break;
            case "block4":
                button = (Button) findViewById(R.id.btn07);
                button.setEnabled(false);
                tileCheck[0][7] = 1;
                break;
            case "block5":
                button = (Button) findViewById(R.id.btn09);
                button.setEnabled(false);
                tileCheck[0][9] = 1;
                break;


            case "block6":
                button = (Button) findViewById(R.id.btn10);
                button.setEnabled(false);
                tileCheck[1][0] = 1;
                break;
            case "block7":
                button = (Button) findViewById(R.id.btn12);
                button.setEnabled(false);
                tileCheck[1][2] = 1;
                break;
            case "block8":
                button = (Button) findViewById(R.id.btn14);
                button.setEnabled(false);
                tileCheck[1][4] = 1;
                break;
            case "block9":
                button = (Button) findViewById(R.id.btn16);
                button.setEnabled(false);
                tileCheck[1][6] = 1;
                break;
            case "block10":
                button = (Button) findViewById(R.id.btn18);
                button.setEnabled(false);
                tileCheck[1][8] = 1;
                break;
            case "block11":
                button = (Button) findViewById(R.id.btn110);
                button.setEnabled(false);
                tileCheck[1][10] = 1;
                break;


            case "block12":
                button = (Button) findViewById(R.id.btn21);
                button.setEnabled(false);
                tileCheck[2][1] = 1;
                break;
            case "block13":
                button = (Button) findViewById(R.id.btn23);
                button.setEnabled(false);
                tileCheck[2][3] = 1;
                break;
            case "block14":
                button = (Button) findViewById(R.id.btn25);
                button.setEnabled(false);
                tileCheck[2][5] = 1;
                break;
            case "block15":
                button = (Button) findViewById(R.id.btn27);
                button.setEnabled(false);
                tileCheck[2][7] = 1;
                break;
            case "block16":
                button = (Button) findViewById(R.id.btn29);
                button.setEnabled(false);
                tileCheck[2][9] = 1;
                break;


            case "block17":
                button = (Button) findViewById(R.id.btn30);
                button.setEnabled(false);
                tileCheck[3][0] = 1;
                break;
            case "block18":
                button = (Button) findViewById(R.id.btn32);
                button.setEnabled(false);
                tileCheck[3][2] = 1;
                break;
            case "block19":
                button = (Button) findViewById(R.id.btn34);
                button.setEnabled(false);
                tileCheck[3][4] = 1;
                break;
            case "block20":
                button = (Button) findViewById(R.id.btn36);
                button.setEnabled(false);
                tileCheck[3][6] = 1;
                break;
            case "block21":
                button = (Button) findViewById(R.id.btn38);
                button.setEnabled(false);
                tileCheck[3][8] = 1;
                break;
            case "block22":
                button = (Button) findViewById(R.id.btn310);
                button.setEnabled(false);
                tileCheck[3][10] = 1;
                break;


            case "block23":
                button = (Button) findViewById(R.id.btn41);
                button.setEnabled(false);
                tileCheck[4][1] = 1;
                break;
            case "block24":
                button = (Button) findViewById(R.id.btn43);
                button.setEnabled(false);
                tileCheck[4][3] = 1;
                break;
            case "block25":
                button = (Button) findViewById(R.id.btn45);
                button.setEnabled(false);
                tileCheck[4][5] = 1;
                break;
            case "block26":
                button = (Button) findViewById(R.id.btn47);
                button.setEnabled(false);
                tileCheck[4][7] = 1;
                break;
            case "block27":
                button = (Button) findViewById(R.id.btn49);
                button.setEnabled(false);
                tileCheck[4][9] = 1;
                break;


            case "block28":
                button = (Button) findViewById(R.id.btn50);
                button.setEnabled(false);
                tileCheck[5][0] = 1;
                break;
            case "block29":
                button = (Button) findViewById(R.id.btn52);
                button.setEnabled(false);
                tileCheck[5][2] = 1;
                break;
            case "block30":
                button = (Button) findViewById(R.id.btn54);
                button.setEnabled(false);
                tileCheck[5][4] = 1;
                break;
            case "block31":
                button = (Button) findViewById(R.id.btn56);
                button.setEnabled(false);
                tileCheck[5][6] = 1;
                break;
            case "block32":
                button = (Button) findViewById(R.id.btn58);
                button.setEnabled(false);
                tileCheck[5][8] = 1;
                break;
            case "block33":
                button = (Button) findViewById(R.id.btn510);
                button.setEnabled(false);
                tileCheck[5][10] = 1;
                break;


            case "block34":
                button = (Button) findViewById(R.id.btn61);
                button.setEnabled(false);
                tileCheck[6][1] = 1;
                break;
            case "block35":
                button = (Button) findViewById(R.id.btn63);
                button.setEnabled(false);
                tileCheck[6][3] = 1;
                break;
            case "block36":
                button = (Button) findViewById(R.id.btn65);
                button.setEnabled(false);
                tileCheck[6][5] = 1;
                break;
            case "block37":
                button = (Button) findViewById(R.id.btn67);
                button.setEnabled(false);
                tileCheck[6][7] = 1;
                break;
            case "block38":
                button = (Button) findViewById(R.id.btn69);
                button.setEnabled(false);
                tileCheck[6][9] = 1;
                break;

        }


    }

    public void checkForBox() {


        for (int i = 1; i < 7; i++) {
            if (i % 2 != 0) {
                for (int j = 1; j < 11; j++) {
                    if (j % 2 != 0) {
                        if ((tileCheck[i - 1][j] == 1) && (tileCheck[i + 1][j] == 1) && (tileCheck[i][j - 1] == 1) && (tileCheck[i][j + 1]) == 1) {
                            if (checkbox[i][j] != "x") {
                                {
                                    if (activePlayer == 2) {
                                        buttons[i][j].setBackgroundResource(R.drawable.blue);
                                        player1Point = (TextView) findViewById(R.id.player1point);
                                        P1points = P1points + 1;
                                        player1Point.setText(userName + ": " + P1points);
                                        checkbox[i][j] = "x";
                                        Log.d("test1", "box " + checkbox[i][j] + " points: " + P1points);


                                    } else if (activePlayer == 1) {
                                        buttons[i][j].setBackgroundResource(R.drawable.red);
                                        player2Point = (TextView) findViewById(R.id.player2point);
                                        P2points = P2points + 1;
                                        player2Point.setText(otherPlayer + ": " + P2points);
                                        checkbox[i][j] = "x";
                                        Log.d("test2", "box " + checkbox[i][j] + " points: " + P2points);

                                    }
                                    turnAgain = true;
                                }


                            }
                        }
                    }
                }
            }
        }
        // Toast.makeText(OnlineGameActivityActivity.this, "1. " + turnAgain, Toast.LENGTH_SHORT).show();
        // Toast.makeText(OnlineGameActivityActivity.this, "2. " + activePlayer, Toast.LENGTH_SHORT).show();
    }

    public void checkForTurnagain() {

        setEnableClick(false);
        turnAgain = false;
        myRef.child("playing").child(playerSession).child("turn").setValue(otherPlayer);

    }

    public void checkScore() {
        Log.d("test3", "p1: " + P1points + "P2: " + P2points);
    }

    public void openPopup() {
        myRef.child("playing").child(playerSession).removeValue();
        Intent intent = new Intent(this, popUpActivityOnline.class);
        intent.putExtra("Winner_Name", WinnerName);
        mediaPlayer.release();
        mediaPlayer = null;
        resetVariables();
        startActivity(intent);
    }


    void PlayGame(int selectedBlock, Button selectedButton) {


        checkForTurnagain();
        selectedButton.setEnabled(false);


    }

    void CheckWinner() {
        if (P1points == 6) {
            winner = 1;
        } else if (P2points == 6) {
            winner = 2;
        }

        if (winner != 0) {
            if (winner == 1) {
                WinnerName = userName;
            } else {
                WinnerName = otherPlayer;
            }
            openPopup();
            //gameState = 2;
            // Toast.makeText(OnlineGameActivityActivity.this,"checkwinner"+String.valueOf(activePlayer),Toast.LENGTH_SHORT).show();
        } else {

        }


        ArrayList<Integer> emptyBlocks = new ArrayList<Integer>();
        for (int i = 1; i <= 9; i++) {
            if (!(Player1.contains(i) || Player2.contains(i))) {
                emptyBlocks.add(i);
            }
        }
//        if(emptyBlocks.size() == 0) {
//            if(gameState == 1) {
//                AlertDialog.Builder b = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
//                ShowAlert("Draw");
//            }
//            gameState = 3;
//        }
        // Toast.makeText(OnlineGameActivityActivity.this,String.valueOf(activePlayer),Toast.LENGTH_SHORT).show();
    }

    public void changeTileColor(String ans) {

        if (activePlayer == 2) {
            switch (ans) {
                case "block1":
                    button = (Button) findViewById(R.id.btn01);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block2":
                    button = (Button) findViewById(R.id.btn03);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block3":
                    button = (Button) findViewById(R.id.btn05);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block4":
                    button = (Button) findViewById(R.id.btn07);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block5":
                    button = (Button) findViewById(R.id.btn09);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;


                case "block6":
                    button = (Button) findViewById(R.id.btn10);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;

                case "block7":
                    button = (Button) findViewById(R.id.btn12);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block8":
                    button = (Button) findViewById(R.id.btn14);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block9":
                    button = (Button) findViewById(R.id.btn16);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block10":
                    button = (Button) findViewById(R.id.btn18);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block11":
                    button = (Button) findViewById(R.id.btn110);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;

                case "block12":
                    button = (Button) findViewById(R.id.btn21);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block13":
                    button = (Button) findViewById(R.id.btn23);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block14":
                    button = (Button) findViewById(R.id.btn25);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block15":
                    button = (Button) findViewById(R.id.btn27);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block16":
                    button = (Button) findViewById(R.id.btn29);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;

                case "block17":
                    button = (Button) findViewById(R.id.btn30);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block18":
                    button = (Button) findViewById(R.id.btn32);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block19":
                    button = (Button) findViewById(R.id.btn34);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block20":
                    button = (Button) findViewById(R.id.btn36);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block21":
                    button = (Button) findViewById(R.id.btn38);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block22":
                    button = (Button) findViewById(R.id.btn310);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;

                case "block23":
                    button = (Button) findViewById(R.id.btn41);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block24":
                    button = (Button) findViewById(R.id.btn43);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block25":
                    button = (Button) findViewById(R.id.btn45);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block26":
                    button = (Button) findViewById(R.id.btn47);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block27":
                    button = (Button) findViewById(R.id.btn49);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;

                case "block28":
                    button = (Button) findViewById(R.id.btn50);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block29":
                    button = (Button) findViewById(R.id.btn52);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block30":
                    button = (Button) findViewById(R.id.btn54);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block31":
                    button = (Button) findViewById(R.id.btn56);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block32":
                    button = (Button) findViewById(R.id.btn58);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block33":
                    button = (Button) findViewById(R.id.btn510);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;

                case "block34":
                    button = (Button) findViewById(R.id.btn61);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block35":
                    button = (Button) findViewById(R.id.btn63);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block36":
                    button = (Button) findViewById(R.id.btn65);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block37":
                    button = (Button) findViewById(R.id.btn67);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;
                case "block38":
                    button = (Button) findViewById(R.id.btn69);
                    button.setBackgroundResource(R.drawable.blue2);
                    break;

            }
        } else if (activePlayer == 1) {

            switch (ans) {
                case "block1":
                    button = (Button) findViewById(R.id.btn01);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block2":
                    button = (Button) findViewById(R.id.btn03);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block3":
                    button = (Button) findViewById(R.id.btn05);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block4":
                    button = (Button) findViewById(R.id.btn07);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block5":
                    button = (Button) findViewById(R.id.btn09);
                    button.setBackgroundResource(R.drawable.red2);
                    break;

                case "block6":
                    button = (Button) findViewById(R.id.btn10);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block7":
                    button = (Button) findViewById(R.id.btn12);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block8":
                    button = (Button) findViewById(R.id.btn14);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block9":
                    button = (Button) findViewById(R.id.btn16);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block10":
                    button = (Button) findViewById(R.id.btn18);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block11":
                    button = (Button) findViewById(R.id.btn110);
                    button.setBackgroundResource(R.drawable.red2);
                    break;

                case "block12":
                    button = (Button) findViewById(R.id.btn21);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block13":
                    button = (Button) findViewById(R.id.btn23);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block14":
                    button = (Button) findViewById(R.id.btn25);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block15":
                    button = (Button) findViewById(R.id.btn27);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block16":
                    button = (Button) findViewById(R.id.btn29);
                    button.setBackgroundResource(R.drawable.red2);
                    break;

                case "block17":
                    button = (Button) findViewById(R.id.btn30);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block18":
                    button = (Button) findViewById(R.id.btn32);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block19":
                    button = (Button) findViewById(R.id.btn34);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block20":
                    button = (Button) findViewById(R.id.btn36);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block21":
                    button = (Button) findViewById(R.id.btn38);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block22":
                    button = (Button) findViewById(R.id.btn310);
                    button.setBackgroundResource(R.drawable.red2);
                    break;

                case "block23":
                    button = (Button) findViewById(R.id.btn41);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block24":
                    button = (Button) findViewById(R.id.btn43);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block25":
                    button = (Button) findViewById(R.id.btn45);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block26":
                    button = (Button) findViewById(R.id.btn47);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block27":
                    button = (Button) findViewById(R.id.btn49);
                    button.setBackgroundResource(R.drawable.red2);
                    break;

                case "block28":
                    button = (Button) findViewById(R.id.btn50);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block29":
                    button = (Button) findViewById(R.id.btn52);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block30":
                    button = (Button) findViewById(R.id.btn54);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block31":
                    button = (Button) findViewById(R.id.btn56);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block32":
                    button = (Button) findViewById(R.id.btn58);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block33":
                    button = (Button) findViewById(R.id.btn510);
                    button.setBackgroundResource(R.drawable.red2);
                    break;

                case "block34":
                    button = (Button) findViewById(R.id.btn61);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block35":
                    button = (Button) findViewById(R.id.btn63);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block36":
                    button = (Button) findViewById(R.id.btn65);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block37":
                    button = (Button) findViewById(R.id.btn67);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
                case "block38":
                    button = (Button) findViewById(R.id.btn69);
                    button.setBackgroundResource(R.drawable.red2);
                    break;
            }


        }
    }


    public void GameBoardClick(View view) {
        Button selectedButton = (Button) view;
        if (playerSession.length() <= 0) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            mediaPlayer.release();
            mediaPlayer = null;
            startActivity(intent);
            finish();
        } else {
            int selectedBlock = 0;
            switch ((selectedButton.getId())) {

                //row 0
                case R.id.btn01:
                    selectedBlock = 1;
                    break;
                case R.id.btn03:
                    selectedBlock = 2;
                    break;
                case R.id.btn05:
                    selectedBlock = 3;
                    break;
                case R.id.btn07:
                    selectedBlock = 4;
                    break;
                case R.id.btn09:
                    selectedBlock = 5;
                    break;

                //row 1
                case R.id.btn10:
                    selectedBlock = 6;
                    break;
                case R.id.btn12:
                    selectedBlock = 7;
                    break;
                case R.id.btn14:
                    selectedBlock = 8;
                    break;
                case R.id.btn16:
                    selectedBlock = 9;
                    break;
                case R.id.btn18:
                    selectedBlock = 10;
                    break;
                case R.id.btn110:
                    selectedBlock = 11;
                    break;

                //row 2
                case R.id.btn21:
                    selectedBlock = 12;
                    break;
                case R.id.btn23:
                    selectedBlock = 13;
                    break;
                case R.id.btn25:
                    selectedBlock = 14;
                    break;
                case R.id.btn27:
                    selectedBlock = 15;
                    break;
                case R.id.btn29:
                    selectedBlock = 16;
                    break;

                //row 3
                case R.id.btn30:
                    selectedBlock = 17;
                    break;
                case R.id.btn32:
                    selectedBlock = 18;
                    break;
                case R.id.btn34:
                    selectedBlock = 19;
                    break;
                case R.id.btn36:
                    selectedBlock = 20;
                    break;
                case R.id.btn38:
                    selectedBlock = 21;
                    break;
                case R.id.btn310:
                    selectedBlock = 22;
                    break;

                //row 4
                case R.id.btn41:
                    selectedBlock = 23;
                    break;
                case R.id.btn43:
                    selectedBlock = 24;
                    break;
                case R.id.btn45:
                    selectedBlock = 25;
                    break;
                case R.id.btn47:
                    selectedBlock = 26;
                    break;
                case R.id.btn49:
                    selectedBlock = 27;
                    break;

                //row 5
                case R.id.btn50:
                    selectedBlock = 28;
                    break;
                case R.id.btn52:
                    selectedBlock = 29;
                    break;
                case R.id.btn54:
                    selectedBlock = 30;
                    break;
                case R.id.btn56:
                    selectedBlock = 31;
                    break;
                case R.id.btn58:
                    selectedBlock = 32;
                    break;
                case R.id.btn510:
                    selectedBlock = 33;
                    break;

                //row 6
                case R.id.btn61:
                    selectedBlock = 34;
                    break;
                case R.id.btn63:
                    selectedBlock = 35;
                    break;
                case R.id.btn65:
                    selectedBlock = 36;
                    break;
                case R.id.btn67:
                    selectedBlock = 37;
                    break;
                case R.id.btn69:
                    selectedBlock = 38;
                    break;

            }


            getButtonID = String.valueOf(selectedBlock);


            //myRef.child("playing").child(playerSession).child("game").child("block" + selectedBlock).setValue(userName);
            myRef.child("playing").child(playerSession).child("game").setValue("block" + selectedBlock);

            PlayGame(selectedBlock, selectedButton);

        }

    }


    public void OtherPlayer(int selectedBlock) {
        Button button = (Button) findViewById(R.id.btn01);
        switch (selectedBlock) {
            case 1:
                button = (Button) findViewById(R.id.btn01);
                break;
            case 2:
                button = (Button) findViewById(R.id.btn03);
                break;
            case 3:
                button = (Button) findViewById(R.id.btn05);
                break;
            case 4:
                button = (Button) findViewById(R.id.btn07);
                break;
            case 5:
                button = (Button) findViewById(R.id.btn09);
                break;

            case 6:
                button = (Button) findViewById(R.id.btn10);
                break;
            case 7:
                button = (Button) findViewById(R.id.btn12);
                break;
            case 8:
                button = (Button) findViewById(R.id.btn14);
                break;
            case 9:
                button = (Button) findViewById(R.id.btn16);
                break;
            case 10:
                button = (Button) findViewById(R.id.btn18);
                break;
            case 11:
                button = (Button) findViewById(R.id.btn110);
                break;

            case 12:
                button = (Button) findViewById(R.id.btn21);
                break;
            case 13:
                button = (Button) findViewById(R.id.btn23);
                break;
            case 14:
                button = (Button) findViewById(R.id.btn25);
                break;
            case 15:
                button = (Button) findViewById(R.id.btn27);
                break;
            case 16:
                button = (Button) findViewById(R.id.btn29);
                break;

            case 17:
                button = (Button) findViewById(R.id.btn30);
                break;
            case 18:
                button = (Button) findViewById(R.id.btn32);
                break;
            case 19:
                button = (Button) findViewById(R.id.btn34);
                break;
            case 20:
                button = (Button) findViewById(R.id.btn36);
                break;
            case 21:
                button = (Button) findViewById(R.id.btn38);
                break;
            case 22:
                button = (Button) findViewById(R.id.btn310);
                break;

            case 23:
                button = (Button) findViewById(R.id.btn41);
                break;
            case 24:
                button = (Button) findViewById(R.id.btn43);
                break;
            case 25:
                button = (Button) findViewById(R.id.btn45);
                break;
            case 26:
                button = (Button) findViewById(R.id.btn47);
                break;
            case 27:
                button = (Button) findViewById(R.id.btn49);
                break;

            case 28:
                button = (Button) findViewById(R.id.btn50);
                break;
            case 29:
                button = (Button) findViewById(R.id.btn52);
                break;
            case 30:
                button = (Button) findViewById(R.id.btn54);
                break;
            case 31:
                button = (Button) findViewById(R.id.btn56);
                break;
            case 32:
                button = (Button) findViewById(R.id.btn58);
                break;
            case 33:
                button = (Button) findViewById(R.id.btn510);
                break;

            case 34:
                button = (Button) findViewById(R.id.btn61);
                break;
            case 35:
                button = (Button) findViewById(R.id.btn63);
                break;
            case 36:
                button = (Button) findViewById(R.id.btn65);
                break;
            case 37:
                button = (Button) findViewById(R.id.btn67);
                break;
            case 38:
                button = (Button) findViewById(R.id.btn69);
                break;


        }
        PlayGame(selectedBlock, button);
    }


    public void setEnableClick(Boolean tureORfalse) {
        Button btn;
        //row 0
        btn = (Button) findViewById(R.id.btn01);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn03);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn05);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn07);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn09);
        btn.setClickable(tureORfalse);

        //row 1
        btn = (Button) findViewById(R.id.btn10);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn12);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn14);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn16);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn18);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn110);
        btn.setClickable(tureORfalse);

        //row 2
        btn = (Button) findViewById(R.id.btn21);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn23);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn25);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn27);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn29);
        btn.setClickable(tureORfalse);

        //row 3
        btn = (Button) findViewById(R.id.btn30);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn12);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn34);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn36);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn38);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn310);
        btn.setClickable(tureORfalse);

        //row 4
        btn = (Button) findViewById(R.id.btn41);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn43);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn45);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn47);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn49);
        btn.setClickable(tureORfalse);

        //row 5
        btn = (Button) findViewById(R.id.btn50);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn52);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn54);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn36);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn38);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn310);
        btn.setClickable(tureORfalse);

        //row 6
        btn = (Button) findViewById(R.id.btn61);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn63);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn65);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn67);
        btn.setClickable(tureORfalse);
        btn = (Button) findViewById(R.id.btn69);
        btn.setClickable(tureORfalse);

    }

    public void resetVariables() {
        P1points = 0;
        P2points = 0;
        WinnerName = "";

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 11; j++) {
                tileCheck[i][j] = 0;

            }
        }


        for (int i = 1; i < 7; i++) {
            if (i % 2 != 0) {
                for (int j = 1; j < 11; j++) {
                    if (j % 2 != 0) {
                        checkbox[i][j] = " ";
                    }
                }
            }
        }
    }

}
