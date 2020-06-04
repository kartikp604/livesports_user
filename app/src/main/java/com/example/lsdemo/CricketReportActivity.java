package com.example.lsdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.R.layout.simple_list_item_1;

public class CricketReportActivity extends AppCompatActivity {

    private RecyclerView report;

    private List<ClassReport>requestReportList;
    private AdapterReportReq adepterReportReq;

    public AlertDialog alertDialog;
    public AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket_report);
        getSupportActionBar().setTitle("Report");

        requestReportList=new ArrayList<>();
        adepterReportReq=new AdapterReportReq(getApplicationContext(),requestReportList);
        ActivityCompat.requestPermissions(CricketReportActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        report=(RecyclerView) findViewById(R.id.reportReView);
        report.setHasFixedSize(true);
        report.setLayoutManager(new LinearLayoutManager(this));
        report.setAdapter(adepterReportReq);


        alertDialog=new ProgressDialog(CricketReportActivity.this);
        builder=new ProgressDialog.Builder(CricketReportActivity.this);
        LayoutInflater inflater =CricketReportActivity.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_dialog,null));
        builder.setCancelable(false);
        alertDialog= builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );







        FirebaseFirestore.getInstance().collection("CricketReport").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){


                    if(doc.getType()==DocumentChange.Type.ADDED){


                        ClassReport classReport=doc.getDocument().toObject(ClassReport.class).withId(doc.getDocument().getId());
                        requestReportList.add(classReport);
                        Toast.makeText(CricketReportActivity.this, classReport.reporId, Toast.LENGTH_SHORT).show();

                        adepterReportReq.notifyDataSetChanged();

                    }
                    if(doc.getType()==DocumentChange.Type.REMOVED){
                        adepterReportReq.notifyDataSetChanged();

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
