package com.deificindia.indifun1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.deificindia.indifun1.Model.StreamDetails;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Streamdata.ApplicationClass;
import com.deificindia.indifun1.Streamdata.PublishStream;
import com.deificindia.indifun1.Streamdata.PublishStreamListener;
import com.deificindia.indifun1.Streamdata.StreamContent;
import com.deificindia.indifun1.Streamdata.SubscribeStream;
import com.deificindia.indifun1.Utility.GetSet;
import com.deificindia.indifun1.Utility.NetworkReceiver;
import com.deificindia.indifun1.rest.ApiInterface;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

public class StreamActivity extends AppCompatActivity implements NetworkReceiver.ConnectivityReceiverListener, PublishStreamListener {

    private final int BUFFEREVT = 1000;
    IntentFilter intentFilter;
    NetworkReceiver receiver;
    ApiInterface apiInterface;
    String from = "";
    private PublishStream fragment = null;
    private AlertDialog bufferDialog;
    private boolean requiresBufferDialog = false;
    private Handler bufferHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BUFFEREVT:
                    if (!requiresBufferDialog) return;

                    bufferDialog = new AlertDialog.Builder(StreamActivity.this).create();
                    bufferDialog.setTitle("Alert");
                    bufferDialog.setMessage("Publisher Is Finishing Broadcast.\nPlease wait to start another broadcast.");
                    bufferDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener()

                            {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }

                    );
                    bufferDialog.show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);

        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        ApplicationClass.getInstance().setConnectivityListener(this);
        //apiInterface = ApiClient.createRetrofitBase().create(ApiInterface.class);
        StreamContent.LoadTests(getResources().openRawResource(R.raw.red5config));

        from = getIntent().getStringExtra("from");
        if (from.equals("publish")) {
            StreamContent.SetTestItem(0);
            Bundle arguments = new Bundle();
            arguments.putString("item_id", "0");
            PublishStream fragment = new PublishStream();
            fragment.setArguments(arguments);
            switchContent(fragment);
            HomeActivity.streamopen = true;
        } else {
            Bundle arguments = getIntent().getExtras();
            if (arguments.getString("from") != null && arguments.getString("from").equals("push")) {
                getdetails(arguments.getString("streamname"));
            } else {
                StreamContent.SetTestItem(1);
                SubscribeStream fragment = new SubscribeStream();
                fragment.setArguments(arguments);
                HomeActivity.streamopen = true;
                switchContent(fragment);
            }
        }
    }

    public void getdetails(final String StreamName) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", GetSet.getUserId());
        map.put("token", GetSet.getToken());
        map.put("stream_name", StreamName.trim());

        Log.v("getdetails:", "Params- " + map);
        Call<StreamDetails> call3 = apiInterface.streamdetail(map);
        call3.enqueue(new Callback<StreamDetails>() {
            @Override
            public void onResponse(Call<StreamDetails> call, Response<StreamDetails> response) {
                StreamDetails data = response.body();
                Log.v("getdetails:", "response- " + data);
                Log.v("streamID:", "response- " + data.streamid);
                Bundle arguments = new Bundle();
                arguments.putString("streamName", StreamName);
                arguments.putString("streamImage", data.stream_thumbnail);
                arguments.putString("title", data.title);
                arguments.putString("userName", data.Posted_by);
                arguments.putString("userImage", data.user_image);
                arguments.putString("viewCount", data.watch_count);
                StreamContent.SetTestItem(1);
                SubscribeStream fragment = new SubscribeStream();
                fragment.setArguments(arguments);
                HomeActivity.streamopen = true;
                switchContent(fragment);

            }
            @Override
            public void onFailure(Call<StreamDetails> call, Throwable t) {
                call.cancel();
            }
        });
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

    @Override
    public void onPublishFlushBufferStart() {

    }

    @Override
    public void onPublishFlushBufferComplete() {

    }

    public void switchContent(Fragment fragment) {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().addToBackStack("publish")
                    .add(R.id.content_frame, fragment, "fragment").commit();
            fragmentManager.executePendingTransactions();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

}