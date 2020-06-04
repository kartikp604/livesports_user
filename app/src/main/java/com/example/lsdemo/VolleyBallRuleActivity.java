package com.example.lsdemo;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class VolleyBallRuleActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private Button buttonApplygame,buttonRegGame;
    private Button downloadpdf;

    private FirebaseFirestore firebaseFirestore;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    private TextView textViewVollyballRules,textViewmanagerName,textViewmanagerNumber;

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;


    private Bundle bundle;
    private String game,capref,regstatus,teamref,loadRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_ball_rule);

        bundle=getIntent().getExtras();
        game=bundle.getString("game");
        if(game.equals("VolleyBallRules")){
             capref="CaptainRequestVolleyBall";

        }
        if (game.equals("KabaddiRules")){
            capref="CaptainRequestKabaddi";

        }
        if(game.equals("VolleyBallRules")){
            regstatus="VolleyBallRegStatus";

        }
        if (game.equals("KabaddiRules")){
            regstatus="KabaddiRegStatus";

        }

        firebaseFirestore =FirebaseFirestore.getInstance();
        firebaseStorage= FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference().child("RulesPdf").child(game);

        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Gamerules");

        textViewVollyballRules=(TextView) findViewById(R.id.text_volleyBall_rule);
        textViewmanagerName=(TextView) findViewById(R.id.txtVolleyBallGameManagerName);
        textViewmanagerNumber=(TextView) findViewById(R.id.txtVolleyBallGameManagerNumber);
        //textViewCricketRules.setText((CharSequence) databaseReference);

        buttonApplygame=(Button)findViewById(R.id.btn_volley_captian);
        buttonRegGame=(Button)findViewById(R.id.btn_reg_volley);
        downloadpdf=(Button)findViewById(R.id.pdfRuelesbtnGame);

        buttonApplygame.setOnClickListener(this);
        buttonRegGame.setOnClickListener(this);
        downloadpdf.setOnClickListener(this);

        alertDialog=new ProgressDialog(VolleyBallRuleActivity.this);
        builder=new ProgressDialog.Builder(VolleyBallRuleActivity.this);
        LayoutInflater inflater=VolleyBallRuleActivity.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_dialog,null));
        builder.setCancelable(false);
        alertDialog=builder.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
        alertDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String volleyBallRules=dataSnapshot.child(game).child("Rules").getValue().toString();
                String GameManager=dataSnapshot.child(game).child("Manager").getValue().toString();
                String Number=dataSnapshot.child(game).child("Number").getValue().toString();
                textViewVollyballRules.setText(volleyBallRules);
                textViewmanagerName.setText("Coordinator:-"+GameManager);
                textViewmanagerNumber.setText("Contact:-"+Number);
                alertDialog.dismiss();

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

    public void captain(){

        final String userinfo=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        final FieldValue timestamp= FieldValue.serverTimestamp();


        final CollectionReference collectionReference=firebaseFirestore.collection("user");
        final DocumentReference documentReference=collectionReference.document(userinfo);
        final String userclass;
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String userClass = documentSnapshot.get("batch").toString();
                String mobile = documentSnapshot.get("mobile").toString();
                String gender = documentSnapshot.get("gender").toString();
                String name = documentSnapshot.get("name").toString();
                String pen = documentSnapshot.get("pen").toString();
                String status = documentSnapshot.get("userType").toString();

                if (status.equals("Captain")) {
                    Toast.makeText(getApplicationContext(), "You are already captain", Toast.LENGTH_SHORT).show();
                    //  FirebaseFirestore.getInstance().collection("user").document(userinfo).update("userType","Pending");
                } else {



                    CollectionReference referenceCaptain = firebaseFirestore.collection(capref);
                    DocumentReference referenceCaptainClass = referenceCaptain.document(userinfo);
                    Map<String, Object> value = new HashMap<>();
                    value.put("name", name);
                    value.put("email", userinfo);
                    value.put("batch", userClass);
                    value.put("gender", gender);
                    value.put("pen", pen);
                    value.put("mobile", mobile);
                    value.put("Status", "pending");
                    value.put("timestamp", timestamp);

                    referenceCaptainClass.set(value);

                    Toast.makeText(getApplicationContext(), "REQUEST SEND", Toast.LENGTH_LONG).show();


                }
            }
        });

    }

    public void GameReg(){

        final String captainMail=FirebaseAuth.getInstance().getCurrentUser().getEmail();

        //String RegStatus =FirebaseDatabase.getInstance().getReference("RegStatus").child("CricketRegStatus").va


        final CollectionReference collectionReference=firebaseFirestore.collection(capref);
        final DocumentReference documentReference=collectionReference.document(captainMail);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String captianClass=documentSnapshot.get("batch").toString();
                String status=documentSnapshot.get("Status").toString();
                if (game.equals("VolleyBallRules")){
                    teamref="VolleyBallTeam";

                }
                if (game.equals("KabaddiRules"))
                {
                    teamref="KabaddiTeam";
                }



                if(status.equals("Captain")){
                    alertDialog.dismiss();
                    Intent i =new Intent(getApplicationContext(),TeamRegActivity.class);

                    Bundle bundle=new Bundle();
                    bundle.putString("collref",teamref);

                    i.putExtras(bundle);
                    startActivity(i);
                   // Toast.makeText(getApplicationContext(),status,Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(getApplicationContext(),CricketRegForm.class));
                }
                else
                {
                    alertDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"You are not a Captain..",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void downloadfile(){


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Gamerules").child(game);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String url=dataSnapshot.child("Downloadlink").getValue().toString();


                Toast.makeText(VolleyBallRuleActivity.this, "Downloading....", Toast.LENGTH_SHORT).show();

                downloadpdf(VolleyBallRuleActivity.this,game,".pdf",DIRECTORY_DOWNLOADS,url);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        if(v==buttonApplygame){
            captain();
        }

        if(v==buttonRegGame){
            alertDialog.show();

            DatabaseReference dr =FirebaseDatabase.getInstance().getReference("RegStatus");


            dr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String StatusValue=dataSnapshot.child(regstatus).getValue().toString();
                    if (StatusValue.equals("true")){



                        GameReg();


                    }
                    else
                    {
                        alertDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "REGISTRATION CLOSED..", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        if(v==downloadpdf){
            downloadfile();
            Toast.makeText(getApplicationContext(), "Downloading....", Toast.LENGTH_SHORT).show();

        }

    }


}
