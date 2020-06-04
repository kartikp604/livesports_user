package com.example.lsdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class CricketRegForm extends AppCompatActivity implements View.OnClickListener {

    private Button btnSbmtTeam;
    private Button downloadpdf;

    private TextView note;
    private EditText p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String name,teamMember;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    private String docref;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket_reg_form);
        alertDialog=new ProgressDialog(CricketRegForm.this);
        builder=new ProgressDialog.Builder(CricketRegForm.this);
        LayoutInflater inflater=CricketRegForm.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_dialog,null));
        builder.setCancelable(false);
        alertDialog=builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
        note=(TextView)findViewById(R.id.NOTE);
        downloadpdf=(Button)findViewById(R.id.btn_downloadCktTeam);

        CollectionReference cr=FirebaseFirestore.getInstance().collection("user");
        final DocumentReference documentReference=cr.document(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name=documentSnapshot.get("name").toString();
                teamMember=documentSnapshot.get("teamMember").toString();
                docref=documentSnapshot.get("batch").toString();
                p1.setText(name);
                if(teamMember.equals("Done")){
                    downloadpdf.setEnabled(false);
                    alertDialog.show();
                    loadMember(docref);
                }
                if(teamMember.equals("Approved")){
                    btnSbmtTeam.setEnabled(false);
                    disableEditText(docref);
                    note.setText("NOTE:- YOUR TEAM WAS APPROVED. YOU CAN'T EDIT TEAMMEMBER.");
                }
            }
        });


        p1=(EditText) findViewById(R.id.cp1);
        p2=(EditText) findViewById(R.id.cp2);
        p3=(EditText) findViewById(R.id.cp3);
        p4=(EditText) findViewById(R.id.cp4);
        p5=(EditText) findViewById(R.id.cp5);
        p6=(EditText) findViewById(R.id.cp6);
        p7=(EditText) findViewById(R.id.cp7);
        p8=(EditText) findViewById(R.id.cp8);
        p9=(EditText) findViewById(R.id.cp9);
        p10=(EditText) findViewById(R.id.cp10);
        p11=(EditText) findViewById(R.id.cp11);
        p12=(EditText) findViewById(R.id.cp12);
        p13=(EditText) findViewById(R.id.cp13);
        p14=(EditText) findViewById(R.id.cp14);
        p15=(EditText) findViewById(R.id.cp15);


        btnSbmtTeam=(Button)findViewById(R.id.btn_submitCktTeam);


        firebaseFirestore=FirebaseFirestore.getInstance();

        btnSbmtTeam.setOnClickListener(this);
        downloadpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==downloadpdf){
                     downloadfile();
                    Toast.makeText(CricketRegForm.this, docref, Toast.LENGTH_SHORT).show();

                }
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

    public void loadMember(String docref){
        FirebaseFirestore.getInstance().collection("CricketTeam").document(docref).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                String gp1,gp2,gp3,gp4,gp5,gp6,gp7,gp8,gp9,gp10,gp11,gp12,gp13,gp14,gp15,mobile,email,status;



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
                gp13=documentSnapshot.get("pl13").toString();
                gp14=documentSnapshot.get("pl14").toString();
                gp15=documentSnapshot.get("pl15").toString();



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
                p13.setText(gp13);
                p14.setText(gp14);
                p15.setText(gp15);
                alertDialog.dismiss();

            }
        });

    }
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
        p13.setEnabled(false);
        p14.setEnabled(false);
        p15.setEnabled(false);
        loadMember(docref);

    }




    public void TeamSubmit(){
        final String cp1,cp2,cp3,cp4,cp5,cp6,cp7,cp8,cp9,cp10,cp11,cp12,cp13,cp14,cp15;
        int flag=0;

        cp1=p1.getText().toString().trim();
        cp2=p2.getText().toString().trim();
        cp3=p3.getText().toString().trim();
        cp4=p4.getText().toString().trim();
        cp5=p5.getText().toString().trim();
        cp6=p6.getText().toString().trim();
        cp7=p7.getText().toString().trim();
        cp8=p8.getText().toString().trim();
        cp9=p9.getText().toString().trim();
        cp10=p10.getText().toString().trim();
        cp11=p11.getText().toString().trim();
        cp12=p12.getText().toString().trim();
        cp13=p13.getText().toString().trim();
        cp14=p14.getText().toString().trim();
        cp15=p15.getText().toString().trim();

        final String Captianmail= FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        if(cp2.isEmpty() || cp3.isEmpty() || cp4.isEmpty() || cp5.isEmpty() || cp6.isEmpty() || cp7.isEmpty()
                || cp8.isEmpty() || cp9.isEmpty() || cp10.isEmpty() || cp12.isEmpty() || cp11.isEmpty()
                || cp13.isEmpty() || cp14.isEmpty() || cp15.isEmpty() ){
            flag=1;
            Toast.makeText(this, "Error : Player name Empty", Toast.LENGTH_LONG).show();

        }


        if(flag==0){



        CollectionReference cr=firebaseFirestore.collection("CaptainRequestCricket");
        final DocumentReference documentReference=cr.document(Captianmail);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                     @Override
                     public void onSuccess(DocumentSnapshot documentSnapshot) {
                         String captianClass = documentSnapshot.get("batch").toString();
                         String mobile =documentSnapshot.get("mobile").toString();
                         String name=documentSnapshot.get("name").toString();


                         //Toast.makeText(CricketRegForm.this, (CharSequence) cp1, Toast.LENGTH_SHORT).show();

                         CollectionReference referenceTeam=firebaseFirestore.collection("CricketTeam");
                         DocumentReference TeamClass=referenceTeam.document(captianClass);
                         Map<String,Object> value = new HashMap<>();
                         value.put("CaptainMail",Captianmail);
                         value.put("CaptainMobile",mobile);
                         value.put("TeamStatus","Pending");
                         value.put("pl1",name);
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
                         value.put("pl13", cp13);
                         value.put("pl14", cp14);
                         value.put("pl15", cp15);


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

    public void downloadfile(){

        FirebaseFirestore.getInstance().collection("CricketTeam").document(docref).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                String url=documentSnapshot.get("Downloadlink").toString();
                String filename="CricketTeam_2020_"+docref+"_.pdf";
                downloadpdf(CricketRegForm.this,filename,".pdf",DIRECTORY_DOWNLOADS,url);

            }
        });



    }
    public void downloadpdf(Context context, String filename, String fileext, String destinationDir, String url){

        DownloadManager downloadManager=(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(url);
        DownloadManager.Request request=new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDir,filename+fileext);
        downloadManager.enqueue(request);
    }


    @Override
    public void onClick(View v) {
        if(v==btnSbmtTeam);
        {
            TeamSubmit();
        }

    }
}
