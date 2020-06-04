package com.example.lsdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextemail;
    private EditText editTextPen;
    private EditText editTextpassword;
    private EditText editTextUserName;
    private EditText editTextUserMobile;
  //  private EditText editTextUserClass;

    private ImageView userimage;
    private static int PReqCode=1;
    private static int REQUESTCODE=1;
    private Uri imageuri;


    private RadioGroup radioGroupUserGender;
    private RadioButton radioButtonUserMale;
    private RadioButton radioButtonUserFemale;
    private Button buttonReg;

    private TextView textViewerror;
    private TextView textViewlogin;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser user;


    private FirebaseFirestore firebaseFirestore;


    Spinner sp,spClass;
    ArrayList<String> dept_list,class_list;
    ArrayAdapter<String> adapter,adapterClass;
    String[] dept={"Computer","Civil","Chemical","Electrical","Mechanical"};
    String[] Class={"16","17","18","19"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Registration Page");
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true
//        );


        firebaseFirestore=FirebaseFirestore.getInstance();


        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
        //DropDownMenu

        sp=(Spinner)findViewById(R.id.spinnerUserDeptReg);
        dept_list=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,dept);
        sp.setAdapter(adapter);


        spClass=(Spinner)findViewById(R.id.editTextUserClassReg);
        class_list=new ArrayList<String>();
        adapterClass=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,Class);
        spClass.setAdapter(adapterClass);

        //DropDownEnd




        progressDialog=new ProgressDialog(this);
        buttonReg =(Button) findViewById(R.id.buttonSignup);


        editTextemail = (EditText) findViewById(R.id.editTextEmail);
        editTextPen=(EditText) findViewById(R.id.editTextPen);
        editTextUserName=(EditText)findViewById(R.id.editTextUserNameReg);
        editTextUserMobile=(EditText)findViewById(R.id.editTextUserMobileReg);
      //  editTextUserClass=(EditText)findViewById(R.id.editTextUserClassReg);

        radioGroupUserGender=(RadioGroup) findViewById(R.id.radioUserGenderReg);
        radioButtonUserFemale=(RadioButton) findViewById(R.id.userFemaleReg);
        radioButtonUserMale=(RadioButton)findViewById(R.id.userMaleReg);
        editTextpassword=(EditText) findViewById(R.id.editTextPassword);
        textViewlogin= (TextView) findViewById(R.id.sign_in);
        textViewerror=(TextView) findViewById(R.id.error_update);

        userimage=(ImageView)findViewById(R.id.userpic) ;


        userimage.setOnClickListener(this);
        buttonReg.setOnClickListener(this);
        textViewlogin.setOnClickListener(this);

    }

    private void regUser(){
        final String email=editTextemail.getText().toString().trim();
        String password=editTextpassword.getText().toString().trim();
        final String pen=editTextPen.getText().toString().trim();
        final String name=editTextUserName.getText().toString().trim();
        final String mobile= editTextUserMobile.getText().toString().trim();
       // final String batch = editTextUserClass.getText().toString().trim();
        final String batch=sp.getSelectedItem().toString()+spClass.getSelectedItem().toString();
        final String dept=sp.getSelectedItem().toString();
        final String userType="User";
        final String gender;
        if(radioGroupUserGender.getCheckedRadioButtonId()==R.id.userFemaleReg){

            gender=("Female");
        }
        else
        {
            gender=("Male");
        }

        //Validation

        if (pen.length()<12){
            editTextPen.setError("Incorrect Pen number");
            return;
        }


        if(TextUtils.isEmpty(name))
        {
            editTextUserName.setError("Please Enter Name");
        }

        if(mobile.length()<10)
        {
            editTextUserMobile.setError("check Number");
        }

//        if(TextUtils.isEmpty(batch))
//        {
//            editTextUserName.setError("Please Enter Class");
//        }




        if(TextUtils.isEmpty(email)){
            editTextemail.setError("Please Enter Email:");

            return;
        }

        if(TextUtils.isEmpty(password)) {
            editTextpassword.setError("Please Enter password:");

            return;
        }


        progressDialog.setMessage("please wait....");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //FireStoreReference
                CollectionReference collectionReference=firebaseFirestore.collection("user");
                DocumentReference documentReference=collectionReference.document(email);

                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    //  Log.w(TAG, "getInstanceId failed", task.getException());
                                    return;
                                }

                                // Get new Instance ID token
                                String token = task.getResult().getToken();
                                FirebaseFirestore.getInstance().collection("user").document(email).update("TokenId",token);

                                // Log and toast
                                updateUserpic(imageuri,email);


                                Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                            }
                        });


              //  progressDialog.dismiss();
                UserInformation userInformation=new UserInformation(name,pen,mobile,batch,dept,gender,userType);

                if(task.isSuccessful()){

                    documentReference.set(userInformation);
                  //  Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();



                }
                else {
                    String message=task.getException().getLocalizedMessage().trim();
                   textViewerror.setText(message);


                }
            }
        });
        }

        private void checkpermission(){

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale( MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE )){



            }
            else{

                ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
        {

            opengallery();
        }

        }

        private void opengallery(){

        Intent gallery=new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery,REQUESTCODE);



        }

        public void updateUserpic(Uri imageuri, final String email){

            StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("users_photos");
            final StorageReference imagepath=storageReference.child(imageuri.getLastPathSegment());
            imagepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagepath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()) {

                                String picurl = task.getResult().toString();
                                FirebaseFirestore.getInstance().collection("user").document(email).update("picurl",picurl);

                            }
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                finish();
                                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

                            }
                        }
                    });
                }
            });

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==REQUESTCODE && data!= null){

            imageuri=data.getData();
            userimage.setImageURI(imageuri);


        }
        if (data==null){
            Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

        if(v == buttonReg){
            regUser();
        }
        if(v==userimage){
            if(Build.VERSION.SDK_INT>=22){

                checkpermission();
            }
            else
            {
                opengallery();
            }
        }

        if(v == textViewlogin){
            finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));

        }
    }
}
