package com.example.lsdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LiveScoCriActivity extends AppCompatActivity {

    private TextView txtRun,txtWicket,txtOver,txtRun2,txtWicket2,txtTeam1,txtTeam2,txtrr;
    private TextView bl1,txtwinner;
    private DatabaseReference databaseReference;
    private TextView txtbatsman1,txtbatsman2,txtbowler,runbatsman1,runbatsman2;
    private DatabaseReference databaseReference1;
    private DatabaseReference databaseReferencesp,databaseReferencetp;
    private DatabaseReference dbRefernce;
    private DatabaseReference databaseReferencern;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_sco_cri);

        getSupportActionBar().setTitle("Score");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        bl1=(TextView)findViewById(R.id.ball1);
        txtwinner=(TextView)findViewById(R.id.Winner) ;
        txtRun = (TextView) findViewById(R.id.txtRuns);
        txtOver = (TextView) findViewById(R.id.txtOver);
        txtrr=(TextView)findViewById(R.id.txtrunrate);
        txtWicket = (TextView) findViewById(R.id.txtWicket);
        txtRun2=(TextView)findViewById(R.id.txtRuns2);
        txtWicket2=(TextView)findViewById(R.id.txtWicket2);
        txtbatsman1=(TextView)findViewById(R.id.txtbat1);
        txtbatsman2=(TextView)findViewById(R.id.txtbat2);
        txtbowler=(TextView)findViewById(R.id.txtballer);
        txtTeam1=(TextView)findViewById(R.id.txtteam1);
        txtTeam2=(TextView)findViewById(R.id.txtteam2);
        runbatsman1=(TextView)findViewById(R.id.runbat1);
        runbatsman2=(TextView)findViewById(R.id.runbat2);

        databaseReference = FirebaseDatabase.getInstance().getReference("Cricket/Team1");
        databaseReferencetp= FirebaseDatabase.getInstance().getReference("Cricket/ThisOver");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String runsValue = dataSnapshot.child("Runs").getValue().toString();
                String wicketValue = dataSnapshot.child("Wickets").getValue().toString();
                String overValue = dataSnapshot.child("Overs").getValue().toString();
                String runrate=dataSnapshot.child("RunRate").getValue().toString();
                txtRun.setText(runsValue);
                txtWicket.setText(wicketValue);
                txtOver.setText(overValue);
                txtrr.setText(runrate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        databaseReferencetp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String bt1=dataSnapshot.getValue().toString();
                bl1.setText(bt1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference1 = FirebaseDatabase.getInstance().getReference("Cricket/Team2");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String RunValue = dataSnapshot.child("Runs").getValue().toString();
                String WicketValue=dataSnapshot.child("Wickets").getValue().toString();
                String OverValue=dataSnapshot.child("Overs").getValue().toString();
                String runrate=dataSnapshot.child("RunRate").getValue().toString();
                txtRun2.setText(RunValue);
                txtOver.setText(OverValue);
                txtWicket2.setText(WicketValue);
                txtrr.setText(runrate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReferencesp= FirebaseDatabase.getInstance().getReference("team");
        databaseReferencesp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String ValueTeam1=dataSnapshot.child("team1").getValue().toString();
                String ValueTeam2=dataSnapshot.child("team2").getValue().toString();
                txtTeam1.setText(ValueTeam1);
                txtTeam2.setText(ValueTeam2);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbRefernce= FirebaseDatabase.getInstance().getReference("Cricket/Player");
        dbRefernce.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Bat1Value=dataSnapshot.child("Batsman1").getValue().toString();
                String Bat2Value=dataSnapshot.child("Batsman2").getValue().toString();
                String baller1Value=dataSnapshot.child("Baller1").getValue().toString();
                txtbatsman1.setText(Bat1Value);
                txtbatsman2.setText(Bat2Value);
                txtbowler.setText(baller1Value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReferencern= FirebaseDatabase.getInstance().getReference("Cricket/Batsman");
        databaseReferencern.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String runbt1=dataSnapshot.child("runbatsman1").getValue().toString();
                String runbt2=dataSnapshot.child("runbatsman2").getValue().toString();
                String ValueWinner=dataSnapshot.child("winner").getValue().toString();
                runbatsman1.setText(runbt1);
                runbatsman2.setText(runbt2);
                txtwinner.setText(ValueWinner);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//starting of menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                break;

            case R.id.iconNotification:
                startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                break;

            case R.id.menuHome:
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//menu ending

}