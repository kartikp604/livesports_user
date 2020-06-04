package com.example.lsdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    private TextView textViewforget;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
        buttonLogIn=(Button) findViewById(R.id.buttonlogin);
        editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        textViewSignup=(TextView) findViewById(R.id.sign_up);
        textViewforget=(TextView) findViewById(R.id.forget);

        progressDialog=new ProgressDialog(this);

        buttonLogIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
        textViewforget.setOnClickListener(this);

    }

    private void login(){

        final String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            editTextEmail.setError("Please Enter Email:");
          //  Toast.makeText(this ,"Please enter email:" , Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please Enter Password:");
            //Toast.makeText(this, "Please enter Password:", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("please wait....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {//

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


                                        Toast.makeText(getApplicationContext(), token, Toast.LENGTH_SHORT).show();
                                    }
                                });

                        //String TokenId= FirebaseInstanceId.getInstance().getToken();
                       // FirebaseFirestore.getInstance().collection("user").document().update("TokenId",TokenId);

                        ///
                       // Toast.makeText(LoginActivity.this, TokenId, Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                           // Toast.makeText(LoginActivity.this,"login done",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            progressDialog.dismiss();
                            String message=task.getException().toString();
                            Toast.makeText(getApplicationContext(),"E R R O R.. "+message,Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }



    @Override
    public void onClick(View v) {
        if(v==buttonLogIn){
            login();
        }

        if(v==textViewSignup){
            finish();
            startActivity(new Intent(this,MainActivity.class) );
        }

        if(v==textViewforget){

            startActivity(new Intent(getApplicationContext(),PasswordResetActivity.class));
        }
    }
}
