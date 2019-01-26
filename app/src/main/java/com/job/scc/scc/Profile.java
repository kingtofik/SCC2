package com.job.scc.scc;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    private ImageView profilepic;
    private TextView profilename;
    private TextView profileemail;
    private TextView profilecollege;
    private TextView profilecity;
    private Button  profileedit;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilepic= (ImageView)findViewById(R.id.pprofilepic);
        profilename=(TextView)findViewById(R.id.tvpname);
        profileemail=(TextView)findViewById(R.id.tvpmail);
        profilecollege=(TextView)findViewById(R.id.tvpcollege);
        profilecity=(TextView)findViewById(R.id.tvpcity);
        profileedit=(Button) findViewById(R.id.btnedit);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile =dataSnapshot.getValue(UserProfile.class);
                      //profilename.setText(userProfile.getName());
                      //profileemail.setText(userProfile.getEmail());
                      //profilecollege.setText(userProfile.getCollege());
                      //profilecity.setText(userProfile.getCity());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}
