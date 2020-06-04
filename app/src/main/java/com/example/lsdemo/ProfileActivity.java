package com.example.lsdemo;

import android.app.FragmentManager;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.lsdemo.ui.home.HomeFragment;
import com.example.lsdemo.ui.slideshow.SlideshowFragment;
import com.google.android.gms.actions.ItemListIntents;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private String databaseReference;

    private FirebaseFirestore firebaseFirestore;




    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //edit code


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        firebaseFirestore=FirebaseFirestore.getInstance();
//        firebaseDatabase=FirebaseDatabase.getInstance();
//        databaseReference=firebaseDatabase.getReference().child("User").child(firebaseAuth.getUid()).toString();


        if(firebaseAuth.getCurrentUser()== null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }




        //end



//        final FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//          @Override
//         public void onClick(View view) {
//           if(view==fab){
//            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
//            finish();
//        }
//
//
//               // finish();
//               Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                       .setAction("Action", null).show();
//            }
//        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_Schedule, R.id.nav_Rules,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        update_nav_hader();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.profile, menu);
        return true;


    }
    //notification menu

    //


   //trile
        @Override
        public void onBackPressed(){
            FragmentManager fm = getFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                Log.i("MainActivity", "popping backstack");
                fm.popBackStack();
            } else {
                Log.i("MainActivity", "nothing on backstack, calling super");
                super.onBackPressed();
            }
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
                finish();
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void update_nav_hader(){
        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
        View viewheder = navigationView.getHeaderView(0);
        TextView textViewUserMail=viewheder.findViewById(R.id.textViewUserMail);
        final ImageView navUserPhoto=viewheder.findViewById(R.id.navimageuser);
        final TextView textViewUserName=viewheder.findViewById(R.id.textViewUserName);
        textViewUserMail.setText(firebaseUser.getEmail());

//firestore
       // DocumentReference documentReference=firebaseFirestore.document(firebaseUser.getEmail());
        CollectionReference collectionReference=firebaseFirestore.collection("user");
        DocumentReference documentReference=collectionReference.document(firebaseUser.getEmail());

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
           String pen=documentSnapshot.get("pen").toString();
           textViewUserName.setText(pen);
                Glide.with(ProfileActivity.this).load(documentSnapshot.get("picurl")).into(navUserPhoto);

            }
        });

        //end


        FirebaseAuth firebaseAuth;
        final String firebaseUser;
        final DatabaseReference databaseReference;

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser().getUid();

        databaseReference=FirebaseDatabase.getInstance().getReference("User");

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String na=dataSnapshot.child(firebaseUser).child("pen").getValue().toString();
//                textViewUserName.setText(na);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                return;
//
//            }
//        });

    }

}

