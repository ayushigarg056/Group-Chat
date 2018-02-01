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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayushi.user.loginfirebase.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Registration extends AppCompatActivity  {
    EditText username,password,mail;
    TextView t1,t2;
    ProgressDialog pg;
    FirebaseAuth fauth;
    DatabaseReference db;
    List<User> user11;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);
        username=(EditText)findViewById(R.id.username1);
        password=(EditText)findViewById(R.id.password1);
        mail=(EditText)findViewById(R.id.mail);
        t1=(TextView)findViewById(R.id.signin2);
        sp=getSharedPreferences("mydata", Context.MODE_PRIVATE);

        fauth=FirebaseAuth.getInstance();
        pg=new ProgressDialog(this);
        db= FirebaseDatabase.getInstance().getReference("detail");
        user11=new ArrayList<>();
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i1=new Intent(Registration.this,Login.class);
//                startActivity(i1);
            registeruser();

            }
        });
    }

    private void registeruser()
    {
        final String email = mail.getText().toString().trim();
        final String password1  = password.getText().toString().trim();
        final String user  = username.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
        Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
        return;
    }

        if(TextUtils.isEmpty(password1)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(user)){
            Toast.makeText(this,"Please enter username",Toast.LENGTH_LONG).show();
            return;
        }

//        if (!TextUtils.isEmpty(user))
//        {
//
//            String id=db.push().getKey();
//            SharedPreferences.Editor edi=sp.edit();
//            edi.putString("userid",id);
//            edi.putString("username",user);
//            edi.commit();
//            User user2=new User(user,email,password1);
//            db.child(id).setValue(user2);
//            Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show();
//
//        }
//        else {
//            Toast.makeText(this, "not added", Toast.LENGTH_SHORT).show();
//        }
        pg.setMessage("Registering Please Wait...");
        pg.show();

        fauth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
//                        Toast.makeText(Registration.this, "registration successful now u can login", Toast.LENGTH_SHORT).show();
                        mail.setText(" ");
                        password.setText(" ");
                        username.setText(" ");
//                        String id=db.push().getKey();
                        String id = fauth.getCurrentUser().getUid();
                        Log.d("user_id", "onComplete: ");
//                        SharedPreferences.Editor edi=sp.edit();
//                        edi.putString("userid",id);
//                        edi.putString("username",user);
//                        edi.commit();
                        User user2=new User(user,email,password1);
                        db.child(id).setValue(user2);
                        Toast.makeText(Registration.this, "User added", Toast.LENGTH_SHORT).show();
                        Intent i1=new Intent(Registration.this,MainActivity.class);
                        startActivity(i1);
                        finish();
                    }
                else
                    {
                        Toast.makeText(Registration.this, "Registration not succesfull", Toast.LENGTH_SHORT).show();
                    }
                pg.dismiss();
            }
        });

    }


}
