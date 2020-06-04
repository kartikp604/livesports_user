package com.example.lsdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OtherGameRegActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSubmit,btnsbmt2;


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;



    Spinner sp,sp2;
    ArrayList<String> dept_list;
    ArrayAdapter<String> adapter;
    String[] dept={"Chess","Badminton","Tabletennis","Carrom",};

    ArrayList<String> game_list;
    ArrayAdapter<String> adapter2;
    String[] game={"null","Chess","Badminton","Tabletennis","Carrom",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_game_reg);

        sp=(Spinner)findViewById(R.id.spinnerGame);
        dept_list=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,dept);
        sp.setAdapter(adapter);

//        sp2=(Spinner)findViewById(R.id.spinnerGame1);
//        game_list=new ArrayList<String>();
//        adapter2=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,game);
//        sp2.setAdapter(adapter2);


        firebaseAuth=FirebaseAuth.getInstance();



        buttonSubmit=(Button)findViewById(R.id.buttonsubmit);
     //   btnsbmt2=(Button)findViewById(R.id.buttonsubmit1);
        buttonSubmit.setOnClickListener(this);
        buttonSubmit.setOnClickListener(this);






    }
    //user uid to email

public void submit(final String gameref){
        final String firebaseUser=firebaseAuth.getCurrentUser().getEmail();


//        final String game=sp.getSelectedItem().toString();
//        final String game1=sp2.getSelectedItem().toString();

       // FirebaseFirestore.getInstance().collection("user").document(firebaseUser).update("game1",game);
       // FirebaseFirestore.getInstance().collection("user").document(firebaseUser).update("game2",game1);

        FirebaseFirestore.getInstance().collection("user").document(firebaseUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String name=documentSnapshot.get("name").toString();
                String pen=documentSnapshot.get("pen").toString();
                String contact=documentSnapshot.get("mobile").toString();
                String  batch=documentSnapshot.get("batch").toString();

                Map<String,Object> value =new HashMap<>();
                value.put("name",name);
                value.put("pen",pen);
                value.put("mobile",contact);
                value.put("batch",batch);
                FirebaseFirestore.getInstance().collection(gameref).add(value);


            }
        });

//    FirebaseFirestore.getInstance().collection("user").document(firebaseUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//        @Override
//        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//            String name=documentSnapshot.get("name").toString();
//            String pen=documentSnapshot.get("pen").toString();
//            String contact=documentSnapshot.get("mobile").toString();
//            String  batch=documentSnapshot.get("batch").toString();
//
//            Map<String,Object> value =new HashMap<>();
//            value.put("name",name);
//            value.put("pen",pen);
//            value.put("mobile",contact);
//            value.put("batch",batch);
//            FirebaseFirestore.getInstance().collection(game1).add(value);
//
//
//        }
//    });





}



    @Override
    public void onClick(View v) {
        if(v==buttonSubmit){
            String game=(sp.getSelectedItem().toString());
            submit(game);
        }
//        if(v==btnsbmt2){
//            String game=sp2.getSelectedItem().toString();
//            submit(game);
//        }
    }
}
