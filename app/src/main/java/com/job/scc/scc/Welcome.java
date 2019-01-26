package com.job.scc.scc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.PriorityQueue;
import java.util.jar.Attributes;

public class Welcome extends AppCompatActivity {
    private EditText mail;
    private EditText password;
    private Button login;
    private Button signup;
    private TextView forgotpass;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mail = (EditText) findViewById(R.id.etmail);
        password = (EditText) findViewById(R.id.etpassword);
        login = (Button) findViewById(R.id.btnlogin);
        signup = (Button) findViewById(R.id.btnsignup);
        forgotpass = (TextView) findViewById(R.id.tvforgotpassword);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null)
        {
             finish();
             startActivity(new Intent(this,Home.class));
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               validate(mail.getText().toString(),password.getText().toString());
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Signup.class);
                startActivity(intent);
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Welcome.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

    }
    private void validate(String userName,String userPassword)
    {
        progressDialog.setMessage("Please Wait!");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task)
          {
            if(task.isSuccessful())
            {
                progressDialog.dismiss();
                 //Toast.makeText(Welcome.this,"Login Success",Toast.LENGTH_SHORT).show();
                 //startActivity(new Intent(Welcome.this,Home.class));
                 checkEmailVerification();
            }
            else
                {
                    Toast.makeText(Welcome.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
          }
      });
    }
    private void  checkEmailVerification()
    {
        FirebaseUser firebaseUser=firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag=firebaseUser.isEmailVerified();
        if(emailflag)
        {
            finish();
            startActivity(new Intent(Welcome.this,Home.class));
        }
        else
        {
         Toast.makeText(this,"Verify Your Email",Toast.LENGTH_SHORT).show();
         firebaseAuth.signOut();
        }
        }
}