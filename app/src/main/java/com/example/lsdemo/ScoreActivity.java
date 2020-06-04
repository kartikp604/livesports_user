package com.example.lsdemo;

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

public class ScoreActivity extends AppCompatActivity {

    private TextView txtRun,txtWicket,txtOver,txtRun2,txtWicket2;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        getSupportActionBar().setTitle("Score");
      //  getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        txtRun = (TextView) findViewById(R.id.txtRuns);
        txtOver = (TextView) findViewById(R.id.txtOver);
        txtWicket = (TextView) findViewById(R.id.txtWicket);
        txtRun2=(TextView)findViewById(R.id.txtRuns2);
        txtWicket2=(TextView)findViewById(R.id.txtWicket2);

        databaseReference = FirebaseDatabase.getInstance().getReference("Cricket/Team1");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String runsValue = dataSnapshot.child("Runs").getValue().toString();
                String wicketValue = dataSnapshot.child("Wickets").getValue().toString();
                String overValue = dataSnapshot.child("Overs").getValue().toString();
                txtRun.setText(runsValue);
                txtWicket.setText(wicketValue);
                txtOver.setText(overValue);
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
                txtRun2.setText(RunValue);
                txtOver.setText(OverValue);
                txtWicket2.setText(WicketValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //starting
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
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;

            case R.id.iconNotification:
                startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                break;

            case R.id.menuHome:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //ending
}
