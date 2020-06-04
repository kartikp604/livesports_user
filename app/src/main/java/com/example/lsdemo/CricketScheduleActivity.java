package com.example.lsdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CricketScheduleActivity extends AppCompatActivity {

    private static final String Tag="FireLog";

    private ListView cricketSchedulelist;

    private FirebaseFirestore firebaseFirestore;
    private AdepterScheduleC adepterScheduleC;

    private List<Classcricketschedule> scheduleList;

    private Bundle bundle;
    private String collref;

    public AlertDialog alertDialog;
    public AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket_schedule);
        bundle=getIntent().getExtras();
        collref=bundle.getString("collref");
        //final String Captianmail= FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        getSupportActionBar().setTitle(collref);
        getSupportActionBar().setTitle(collref);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true
//         );

        alertDialog=new ProgressDialog(CricketScheduleActivity.this);
        builder=new ProgressDialog.Builder(CricketScheduleActivity.this);
        LayoutInflater inflater =CricketScheduleActivity.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_dialog,null));
        builder.setCancelable(false);
        alertDialog= builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
        alertDialog.show();

        scheduleList=new ArrayList<>();
        adepterScheduleC=new AdepterScheduleC(scheduleList);

        cricketSchedulelist=(ListView)findViewById(R.id.listView_Cricket_Schedule);
        cricketSchedulelist.setHasTransientState(true);
        cricketSchedulelist.setAdapter(adepterScheduleC);


        firebaseFirestore=FirebaseFirestore.getInstance();

        firebaseFirestore.collection(collref).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


                if(e != null){

                    Log.d(Tag,"Error"+e.getMessage());
                }

                for(DocumentChange doc :queryDocumentSnapshots.getDocumentChanges()){

                    if(doc.getType()==DocumentChange.Type.ADDED)
                    {

                        Classcricketschedule classcricketschedule=doc.getDocument().toObject(Classcricketschedule.class);

                        scheduleList.add(classcricketschedule);
                        adepterScheduleC.notifyDataSetChanged();
                        alertDialog.dismiss();

                    }


                }

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
