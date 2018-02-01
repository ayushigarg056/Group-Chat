package com.ayushi.user.loginfirebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ayushi.user.loginfirebase.model.message;
import com.ayushi.user.loginfirebase.util.MessageAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Recyler_message extends AppCompatActivity {
    EditText e1;
    Button b1;
    RecyclerView r1;
    ArrayList<message> m;
    MessageAdapter ad;
    SharedPreferences sp;
    String id1,name1;
    DatabaseReference db;
    SharedPreferences.Editor edi;
    Boolean logg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler_message);
        e1=(EditText)findViewById(R.id.edd1);
        b1=(Button)findViewById(R.id.b11);
        r1=(RecyclerView)findViewById(R.id.r11);
        db= FirebaseDatabase.getInstance().getReference().child("chat");

        sp=getSharedPreferences("mydata", Context.MODE_PRIVATE);
         id1=sp.getString("userid","a");
         name1=sp.getString("username","ab");

        Log.d("message_user_id", "onComplete: " + id1);

//        Log.e( "onCreate: ",id1 );
        m=new ArrayList<>();

  //      ad=new MessageAdapter(this,m);
        r1.setLayoutManager(new LinearLayoutManager(this));

//        r1.setAdapter(ad);

//        m.add(new message("bc","scdsdx"));
//        m.add(new message("bscac","scsacxsdsdx"));
//        m.add(new message("getlost","SXDSadxas"));

        showmessage();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_messgae();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                logg=false;
                Toast.makeText(getApplicationContext(), "Log_out", Toast.LENGTH_LONG).show();
                edi=sp.edit();
                edi.putString("userid"," ");
                edi.putString("username"," ");
                edi.putString("logged", String.valueOf(logg));
                edi.commit();
                startActivity(new Intent(Recyler_message.this,MainActivity.class));
                finish();
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void save_messgae()
    {
        String msgg=e1.getText().toString().trim();
        if(!TextUtils.isEmpty(msgg))
        {
            message msg=new message(id1,name1,msgg);
            db.push().setValue(msg);
            Toast.makeText(this, "Message added", Toast.LENGTH_SHORT).show();
            e1.setText("");

            showmessage();
        }
        else
        {
            Toast.makeText(this, "please eneter the messgae", Toast.LENGTH_SHORT).show();
        }
    }

    private void showmessage() {

        m.clear();
        FirebaseDatabase.getInstance().getReference().child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String name = snap.child("name").getValue(String.class);
                    String message = snap.child("messagel").getValue(String.class);
                    String id = snap.child("id").getValue(String.class);
                    message msg = new message(id, name, message);
                    m.add(msg);
                }
                MessageAdapter messageAdapter=new MessageAdapter(Recyler_message.this,m);
                r1.setAdapter(messageAdapter);
                r1.scrollToPosition(m.size()-1);
//                ad.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d("messages_fetched", "onDataChange: " + dataSnapshot);
//                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
//                {
//                    message m1=postSnapshot.getValue(message.class);
//                    m.add(m1);
//                }
//                MessageAdapter messageAdapter=new MessageAdapter(Recyler_message.this,m);
//                r1.setAdapter(messageAdapter);
//                ad.notifyDataSetChanged();
//            }

//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
