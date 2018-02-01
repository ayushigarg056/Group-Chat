package com.ayushi.user.loginfirebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ayushi.user.loginfirebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    EditText username,password;
    TextView t1,t2;
    ProgressDialog pg;
    FirebaseAuth fauth;
    DatabaseReference db;
    Boolean logg;
    SharedPreferences sp;
    SharedPreferences.Editor edi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logg=false;
        pg=new ProgressDialog(this);
        fauth=FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance().getReference("detail");

        sp=getSharedPreferences("mydata", Context.MODE_PRIVATE);

        username=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        t1=(TextView)findViewById(R.id.signin1);
        t2=(TextView)findViewById(R.id.forget);

        edi=sp.edit();
        edi.putString("logged", String.valueOf(logg));

       FirebaseUser id=fauth.getCurrentUser();


        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              login();

            }
        });
    }
    private  void login()
    {
        String email=username.getText().toString().trim();
        String pass=password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
//        db.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot childSnapshot: dataSnapshot.getChildren())
//                {
//                    String kkey = childSnapshot.getKey();
//                    Log.e( "keyyy: ",kkey );
//                }
//            }

        pg.setMessage("Loging u in");
        pg.show();
        fauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pg.dismiss();
                if (task.isSuccessful())
                {   logg=true;
                    final String id = fauth.getCurrentUser().getUid();
                    Log.d("login_user_id", "onComplete: " + id);
                    FirebaseDatabase.getInstance().getReference().child("detail").child(id).child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String thisUser = dataSnapshot.getValue(String.class);
                            Log.d("login_user", "onDataChange: " + thisUser);

                            edi.putString("userid", id);
                            edi.putString("username", thisUser);
                            edi.putString("logged", String.valueOf(logg));
                            edi.commit();

                            finish();
                            startActivity(new Intent(Login.this,Recyler_message.class));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                edi.putString("logged", String.valueOf(logg));
                edi.commit();
            }
        });


    }
}
