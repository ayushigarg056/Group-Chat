package com.ayushi.user.loginfirebase.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ayushi.user.loginfirebase.R;
import com.ayushi.user.loginfirebase.model.message;

import java.util.ArrayList;

/**
 * Created by user on 10-Jan-18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder>{
    SharedPreferences sp;
    Context ctx;
    ArrayList<message> messages;
    String id1,name1;
    public MessageAdapter(Context ctx, ArrayList<message> messages) {
        this.ctx=ctx;
        this.messages = messages;
        sp=ctx.getSharedPreferences("mydata", Context.MODE_PRIVATE);
        id1=sp.getString("userid",null);
        name1=sp.getString("username",null);
    }
    public int getItemViewType(int position)
    {
        message message=messages.get(position);
        String idd=message.getId();
        Log.d("my_id", "getItemViewType: " + id1);
        Log.d("msg_id", "getItemViewType: " + idd);
        if(idd.equals(id1))
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview=null;
        if (viewType==0)
        {
           itemview = li.inflate(R.layout.model2, parent, false);
        }
        else if (viewType==1)
        {
            itemview = li.inflate(R.layout.model1, parent, false);

        }
        return new MessageHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        message m=messages.get(position);
        holder.name.setText(m.getName());
        holder.msgg.setText(m.getMessagel());

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
