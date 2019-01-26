package com.job.scc.scc;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.widget.Toast.LENGTH_SHORT;

public class Signup extends AppCompatActivity {
    private LinearLayout linearLayout;
    private ImageView userimg;
    private EditText rname;
    private EditText remail;
    private EditText rpassword;
    private EditText rcity;
    private Button register;
    private TextView alreadyuser;
    String name, password, email, college, city;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE=123;
    private StorageReference storageReference;
    Uri imagePath;

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData()!=null)
        {
            imagePath=data.getData();
            try {
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                userimg.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

    private AutoCompleteTextView rcollege;
    String[] inputs = new String[]{"Pacific", "SCET", "Gandhi", "FETR", "Mahavir", "Pithawala",
            "BhagwanMahavir", "Sasit"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        rname = (EditText) findViewById(R.id.etname);
        remail = (EditText) findViewById(R.id.etemail);
        rpassword = (EditText) findViewById(R.id.etrpassword);
        rcollege = (AutoCompleteTextView) findViewById(R.id.selectclg);
        rcity = (EditText) findViewById(R.id.etcity);
        register = (Button) findViewById(R.id.btnregister);
        userimg=(ImageView)findViewById(R.id.ivprofile);
        alreadyuser = (TextView) findViewById(R.id.tvalreadyuser);
        {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, inputs);
            rcollege.setAdapter(adapter);
        }
        firebaseAuth = FirebaseAuth.getInstance();
        /*firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageReference=firebaseStorage.getReference();

        userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
            }
        });*/

        alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Welcome.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String user_email = remail.getText().toString().trim();
                    String user_password = rpassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                 //sendUserData();
                                 //Toast.makeText(Signup.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                 //startActivity(new Intent(Signup.this, Home.class));
                                 sendEmailVerification();
                            } else {
                                Toast.makeText(Signup.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    private Boolean validate() {
        Boolean result = false;
        name = rname.getText().toString();
        password = rpassword.getText().toString();
        email = remail.getText().toString();
        college = rcollege.getText().toString();
        city = rcity.getText().toString();

        if (name.isEmpty() || password.isEmpty() || email.isEmpty() || college.isEmpty() || city.isEmpty()) {
            Toast.makeText(Signup.this, "Please Enter All Detailes", LENGTH_SHORT).show();
            } else {
            result = true;
            }
            return result;
            }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        Toast.makeText(Signup.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(Signup.this, Welcome.class));
                    } else {
                        Toast.makeText(Signup.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef =firebaseDatabase.getReference(firebaseAuth.getUid());
         /* StorageReference imageReference=storageReference.child(firebaseAuth.getUid()).child("Images");
          UploadTask uploadTask=imageReference.putFile(imagePath);
          uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Signup.this,"Upload Fail",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });*/
        UserProfile userProfile =new UserProfile(name,email,college,city);
        myRef.setValue(userProfile);
    }
  }