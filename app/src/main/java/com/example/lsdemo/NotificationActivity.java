package com.example.lsdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.sql.Timestamp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firestore;
    private String userMail;

    private RecyclerView notiRecView;
    private List<ClassNotification>notificationList;
    private AdepterNotification adepterNotification;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getSupportActionBar().setTitle("Notification");


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        if(firebaseAuth.getCurrentUser()== null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        userMail=firebaseUser.getEmail();

        firestore=FirebaseFirestore.getInstance();

        notificationList=new ArrayList<>();
        adepterNotification=new AdepterNotification(getApplicationContext(),notificationList,notiRecView);

        notiRecView=(RecyclerView)findViewById(R.id.viewNotification);
        notiRecView.setLayoutManager(new LinearLayoutManager(this));

        notiRecView.setAdapter(adepterNotification);


        firestore.collection("user/"+userMail+"/Notification").orderBy("timestamp", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){


                    if(doc.getType()==DocumentChange.Type.ADDED){

                    ClassNotification classNotification=doc.getDocument().toObject(ClassNotification.class).withId(doc.getDocument().getId());
                    notificationList.add(classNotification);
                    adepterNotification.notifyDataSetChanged();


                    }
                    if(doc.getType()==DocumentChange.Type.REMOVED){

                        adepterNotification.notifyDataSetChanged();


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
