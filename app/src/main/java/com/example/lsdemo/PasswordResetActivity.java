package com.example.lsdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends AppCompatActivity implements View.OnClickListener {

    protected FirebaseAuth firebaseAuth;
    protected EditText editTextResetPassword;
    protected Button buttonResetPassword;
    protected ProgressDialog progressDialog;

    //private TextView uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);


        editTextResetPassword=(EditText) findViewById(R.id.editTextResetEmail);
        buttonResetPassword=(Button) findViewById(R.id.buttonResetPassword);
        //
       //uid=(TextView)findViewById(R.id.uid);

        buttonResetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        progressDialog.setMessage("sending....");
        progressDialog.show();

        String userEmail = editTextResetPassword.getText().toString();
        if(TextUtils.isEmpty(userEmail)){

            progressDialog.dismiss();
            Toast.makeText(this ,"Please enter Valid email:" , Toast.LENGTH_LONG).show();
            return;
        }

        else {


            firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"check inbox",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                    }
                    else
                    {
                        progressDialog.dismiss();
                        String message=task.getException().toString();
                        Toast.makeText(getApplicationContext(),"E R R O R /n"+message,Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
    }
}
