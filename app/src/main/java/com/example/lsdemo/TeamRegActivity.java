package com.example.lsdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TeamRegActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSbmtTeam;
    private TextView p1;
    private EditText p2;
    private EditText p3;
    private EditText p4;
    private EditText p5;
    private EditText p6;
    private EditText p7;
    private EditText p8;
    private EditText p9;
    private EditText p10;
    private EditText p11;
    private EditText p12;
    private String name,collref,teamMember;
    private Bundle bundle;

    private FirebaseFirestore firebaseFirestore;
    private AlertDialog progressDialog;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_reg);

        bundle=getIntent().getExtras();
        collref=bundle.getString("collref");
        final String Captianmail= FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        getSupportActionBar().setTitle(collref);



        progressDialog=new ProgressDialog(TeamRegActivity.this);
        builder=new ProgressDialog.Builder(TeamRegActivity.this);
        LayoutInflater inflater=TeamRegActivity.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_dialog,null));
        builder.setCancelable(false);
        progressDialog=builder.create();
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );


        p1=(TextView) findViewById(R.id.gp1);
        CollectionReference cr=FirebaseFirestore.getInstance().collection("user");
        final DocumentReference documentReference=cr.document(Captianmail);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
           name=documentSnapshot.get("name").toString();
           teamMember=documentSnapshot.get("teamMember").toString();
           String docref=documentSnapshot.get("batch").toString();
                p1.setText(name);
                if(teamMember.equals("Done")){
                    progressDialog.show();
                    loadMember(docref);
                }
                if(teamMember.equals("Approved")){
                    disableEditText(docref);
                    btnSbmtTeam.setEnabled(false);
                }
            }
        });




        p2=(EditText) findViewById(R.id.gp2);
        p3=(EditText) findViewById(R.id.gp3);
        p4=(EditText) findViewById(R.id.gp4);
        p5=(EditText) findViewById(R.id.gp5);
        p6=(EditText) findViewById(R.id.gp6);
        p7=(EditText) findViewById(R.id.gp7);
        p8=(EditText) findViewById(R.id.gp8);
        p9=(EditText) findViewById(R.id.gp9);
        p10=(EditText) findViewById(R.id.gp10);
        p11=(EditText) findViewById(R.id.gp11);
        p12=(EditText) findViewById(R.id.gp12);



        btnSbmtTeam=(Button)findViewById(R.id.btn_submitTeam);


        firebaseFirestore=FirebaseFirestore.getInstance();

        btnSbmtTeam.setOnClickListener(this);


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

    //menuending
    public void disableEditText(String docref){
        p1.setEnabled(false);
        p2.setEnabled(false);
        p3.setEnabled(false);
        p4.setEnabled(false);
        p5.setEnabled(false);
        p6.setEnabled(false);
        p7.setEnabled(false);
        p8.setEnabled(false);
        p9.setEnabled(false);
        p10.setEnabled(false);
        p11.setEnabled(false);
        p12.setEnabled(false);
        loadMember(docref);
    }

    public void loadMember(String docref){
        FirebaseFirestore.getInstance().collection(collref).document(docref).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                String gp1,gp2,gp3,gp4,gp5,gp6,gp7,gp8,gp9,gp10,gp11,gp12,mobile,email,status;

                email=documentSnapshot.get("CaptainMail").toString();
                mobile=documentSnapshot.get("CaptainMobile").toString();
                status=documentSnapshot.get("TeamStatus").toString();
                gp1=documentSnapshot.get("pl1").toString();
                gp2=documentSnapshot.get("pl2").toString();
                gp3=documentSnapshot.get("pl3").toString();
                gp4=documentSnapshot.get("pl4").toString();
                gp5=documentSnapshot.get("pl5").toString();
                gp6=documentSnapshot.get("pl6").toString();
                gp7=documentSnapshot.get("pl7").toString();
                gp8=documentSnapshot.get("pl8").toString();
                gp9=documentSnapshot.get("pl9").toString();
                gp10=documentSnapshot.get("pl10").toString();
                gp11=documentSnapshot.get("pl11").toString();
                gp12=documentSnapshot.get("pl12").toString();



                p1.setText( gp1);
                p2.setText(gp2);
                p3.setText(gp3);
                p4.setText(gp4);
                p5.setText(gp5);
                p6.setText(gp6);
                p7.setText(gp7);
                p8.setText(gp8);
                p9.setText(gp9);
                p10.setText(gp10);
                p11.setText(gp11);
                p12.setText(gp12);
                progressDialog.dismiss();

            }
        });
    }
    public void TeamSubmit(){
        final String cp1,cp2,cp3,cp4,cp5,cp6,cp7,cp8,cp9,cp10,cp11,cp12;
        int flag=0;


        cp2=p2.getText().toString();
        cp3=p3.getText().toString();
        cp4=p4.getText().toString();
        cp5=p5.getText().toString();
        cp6=p6.getText().toString();
        cp7=p7.getText().toString();
        cp8=p8.getText().toString();
        cp9=p9.getText().toString();
        cp10=p10.getText().toString();
        cp11=p11.getText().toString();
        cp12=p12.getText().toString();
        if(cp2.isEmpty() || cp3.isEmpty() || cp4.isEmpty() || cp5.isEmpty() || cp6.isEmpty() || cp7.isEmpty()
                || cp8.isEmpty() || cp9.isEmpty() || cp10.isEmpty() || cp12.isEmpty() || cp11.isEmpty()  ){
            flag=1;
            Toast.makeText(this, "Error : Player name Empty", Toast.LENGTH_LONG).show();

        }

        final String Captianmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();


        if(flag==0) {


            CollectionReference cr = firebaseFirestore.collection("user");
            final DocumentReference documentReference = cr.document(Captianmail);
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String captianClass = documentSnapshot.get("batch").toString();
                    String mobile = documentSnapshot.get("mobile").toString();
                    String name = documentSnapshot.get("name").toString();


                   Toast.makeText(getApplicationContext(), collref+captianClass+cp2, Toast.LENGTH_SHORT).show();

                    CollectionReference referenceTeam = FirebaseFirestore.getInstance().collection(collref);
                    DocumentReference TeamClass = referenceTeam.document(captianClass);
                    Map<String, Object> value = new HashMap<>();
                    value.put("CaptainMail", Captianmail);
                    value.put("CaptainMobile", mobile);
                    value.put("TeamStatus", "Pending");
                    value.put("pl1", name);
                    value.put("pl2", cp2);
                    value.put("pl3", cp3);
                    value.put("pl4", cp4);
                    value.put("pl5", cp5);
                    value.put("pl6", cp6);
                    value.put("pl7", cp7);
                    value.put("pl8", cp8);
                    value.put("pl9", cp9);
                    value.put("pl10", cp10);
                    value.put("pl11", cp11);
                    value.put("pl12", cp12);
                    TeamClass.set(value);




//                         DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("CricketTeam/").child(captianClass);
//                         databaseReference.child("Pl1").setValue(p1);
                }
            }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isComplete()){
                        FirebaseFirestore.getInstance().collection("user").document(Captianmail).update("teamMember","Done");
                    }
                }
            });
        }












    }



    @Override
    public void onClick(View v) {
        if(v==btnSbmtTeam);
        {
            TeamSubmit();
        }

    }
}
