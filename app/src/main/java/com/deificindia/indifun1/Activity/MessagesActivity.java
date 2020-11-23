package com.deificindia.indifun1.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deificindia.indifun1.Adapter.ChatAdapter;
import com.deificindia.indifun1.Model.retro.ChatModel_Response;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.MySharePref;
import com.deificindia.indifun1.rest.AppConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun1.Utility.MySharePref.USERID;

public class MessagesActivity extends FragmentActivity {

    private RecyclerView chatlist;
    private ChatAdapter likeAdapter;
    private SwipeRefreshLayout Chatswipe;
    private List<ChatModel_Response> chatmodelList = new ArrayList<>();
    private static final  String URL = "https://deificindia.com/indifun/api/user_chatbox";
    private ImageView searchimage;
    private TextView txt_header_title, notitext, momenttext, recenttext;
    private LinearLayout nLayout, mLayout, rLayout, mrLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        searchimage=findViewById(R.id.searchimage);
        txt_header_title=findViewById(R.id.txt_header_title);
        nLayout = findViewById(R.id.nLayout);
        mLayout = findViewById(R.id.mLayout);
        notitext = findViewById(R.id.notitext);
        recenttext = findViewById(R.id.recenttext);
        momenttext = findViewById(R.id.momenttext);
        rLayout = findViewById(R.id.rLayout);
        mrLayout = findViewById(R.id.mrLayout);
        txt_header_title.setText("Messages");
        Chatswipe = findViewById(R.id.swiprefresh);
        chatlist = findViewById(R.id.recylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chatlist.setLayoutManager(linearLayoutManager);
        getdata();

        Chatswipe.setOnRefreshListener(() -> {
            getdata();
            Chatswipe.setRefreshing(false);
        });

        nLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mrLayout.getVisibility() != View.VISIBLE  ){
                    mrLayout.setVisibility(View.VISIBLE);
                }
                else{
                    mrLayout.setVisibility(View.GONE);
                }
            }
        });

        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagesActivity.this, MomentNotification.class));

            }
        });

        rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////
            }
        });

        notitext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mrLayout.getVisibility() != View.VISIBLE  ){
                    mrLayout.setVisibility(View.VISIBLE);
                }
                else{
                    mrLayout.setVisibility(View.GONE);
                }

            }
        });

        momenttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagesActivity.this, MomentNotification.class));

            }
        });

        searchimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MessagesActivity.this, SearchUserActivity.class));
            }
        });

    }

    private void getdata() {
        String uid = MySharePref.getUserId();
        Call<ChatModel_Response> chatmodelList = AppConfig.loadInterface().getChats(uid);
        chatmodelList.enqueue(new Callback<ChatModel_Response>() {
            @Override
            public void onResponse(Call<ChatModel_Response> call, Response<ChatModel_Response> response) {
                ChatModel_Response list = response.body();
                if(list!=null) {
                    chatlist.setAdapter(new ChatAdapter(list.getResult(), MessagesActivity.this));
                    //Toast.makeText(MessagesActivity.this, "success", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ChatModel_Response> call, Throwable t) {
                Toast.makeText(MessagesActivity.this, "error occcured", Toast.LENGTH_LONG).show();

            }
        });
    }
}