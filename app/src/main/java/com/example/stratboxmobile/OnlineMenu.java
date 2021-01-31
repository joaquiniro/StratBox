package com.example.stratboxmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OnlineMenu extends AppCompatActivity {

    ListView lvLoginUser;
    ArrayList<String> listLoginUser = new ArrayList<String>();
    ArrayAdapter adpt;

    ListView lvRequestedUser;
    ArrayList<String> listRequestedUser = new ArrayList<String>();
    ArrayAdapter reqadpt;

    TextView txtUserID, txtSendRequest,txtAcceptRequest;
    String LoginUserID , UserName,LoginID;

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_menu);

        mediaPlayer = MediaPlayer.create(this,R.raw.stratboxbg);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.start();

        listRequestedUser.clear();
        listLoginUser.clear();




        txtSendRequest = (TextView) findViewById(R.id.TxtSendRequest);
        txtAcceptRequest = (TextView) findViewById(R.id.TxtAcceptRequest);

        txtSendRequest.setText("Please wait...");
        txtAcceptRequest.setText("Please wait...");

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();



        lvLoginUser = (ListView)findViewById(R.id.LvLoginUser);
        adpt = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listLoginUser);

        lvLoginUser.setAdapter(adpt);

        lvRequestedUser = (ListView) findViewById(R.id.LvRequestedUser);
        reqadpt = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listRequestedUser);
        lvRequestedUser.setAdapter(reqadpt);

        txtUserID = (TextView) findViewById(R.id.TxtLoginUser);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    LoginID = user.getUid();
                    Log.d("Auth", "onAuthStateChanged:sign_in:" + LoginUserID);
                    LoginUserID = user.getEmail();
                    txtUserID.setText(LoginUserID);
                    UserName = convertEmailToString(LoginUserID);
                    myRef.child("users").child(UserName).child("request").setValue(LoginID);
                    reqadpt.clear();
                    AcceptInCommingRequests();
                } else {
                    Log.d("Aut Failed", "onAuthStateChanged:sign_out or login");
                    JoinOnlineGames();
                }
            }


        };

        myRef.getRoot().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateLoginUsers(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lvLoginUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String requestToUser = ((TextView)view).getText().toString();
                confirmRequest(requestToUser, "To");
            }
        });
        lvRequestedUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String requestFromUser = ((TextView)view).getText().toString();
                confirmRequest(requestFromUser, "From");
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OnlineMenu.this, Game.class);
        startActivity(intent);
        mediaPlayer.release();
        mediaPlayer = null;
        finish();
    }

    void confirmRequest(final String OtherPlayer, final String reqType){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.connect_player_dialog, null);
        b.setView(dialogView);

        b.setTitle("Start Game?");
        b.setMessage("Connect with" + OtherPlayer);
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myRef.child("users").child(OtherPlayer).child("request").push().setValue(LoginUserID);
                if(reqType.equalsIgnoreCase("From")){
                    StartGame(OtherPlayer + ":" + UserName, OtherPlayer, "From");
                }else{
                    StartGame(UserName + ":" + OtherPlayer, OtherPlayer, "To");
                }
            }
        });
        b.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        b.show();
    }







    void StartGame(String PlayerGameID, String OtherPlayer, String requestType){
        myRef.child("playing").child(PlayerGameID).removeValue();
        Intent intent = new Intent(getApplicationContext(), OnlineGame5x5.class);
        intent.putExtra("player_session", PlayerGameID);
        intent.putExtra("user_name", UserName);
        intent.putExtra("other_player", OtherPlayer);
        intent.putExtra("login_uid", LoginID);
        intent.putExtra("request_type", requestType);
        mediaPlayer.release();
        mediaPlayer = null;


        startActivity(intent);



        adpt.clear();
        reqadpt.clear();
    }


    void updateLoginUsers(DataSnapshot dataSnapshot){
        String key = "";
        Set<String> set = new HashSet<String>();
        set.clear();
        Iterator i = dataSnapshot.getChildren().iterator();
        while(i.hasNext()){
            key = ((DataSnapshot) i.next()).getKey();
            if(!key.equalsIgnoreCase(UserName)){
                if(key != UserName){
                    set.add(key);
                }


            }
        }
        adpt.clear();
        adpt.addAll(set);
        adpt.notifyDataSetChanged();
        txtSendRequest.setText("Send Request to");
        txtAcceptRequest.setText("Accept Request from");


    }




    private String convertEmailToString(String Email){
        String value = Email.substring(0,Email.indexOf('@'));
        value = value.replace(".","");
        return value;
    }








    void AcceptInCommingRequests(){
        myRef.child("users").child(UserName).child("request")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try{

                            HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();

                            if(map != null){
                                String value = "";
                                for(String key:map.keySet()){
                                    value = (String) map.get(key);
                                    reqadpt.add(convertEmailToString(value));
                                    reqadpt.notifyDataSetChanged();
                                    myRef.child("users").child(UserName).child("request").setValue(LoginID);
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }





    void JoinOnlineGames(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.login_dialog,null);
        b.setView(dialogView);

        final EditText etEmail = (EditText) dialogView.findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) dialogView.findViewById(R.id.etPassword);

        b.setTitle("Please Register");
        b.setMessage("Enter your email and password for registration");
        b.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RegisterUser(etEmail.getText().toString(), etPassword.getText().toString());
//                Intent i = new Intent(getApplicationContext(), MenuActivity.class);
//                startActivity(i);
//                finish();
            }
        });
        b.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), Game.class);
                startActivity(i);
                finish();
            }
        });
        b.show();

    }



    void RegisterUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Auth Complete", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Auth Failed", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }




    @Override
    public void onStart(){
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        // FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }







    private void updateUI(FirebaseUser currentUser) {

    }

}
