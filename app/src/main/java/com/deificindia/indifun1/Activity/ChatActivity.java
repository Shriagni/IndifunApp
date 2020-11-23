package com.deificindia.indifun1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.Visibility;
import android.os.Bundle;
import android.sax.RootElement;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.deificindia.indifun.R;
import com.deificindia.indifun1.R;
import com.google.android.material.textfield.TextInputEditText;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import retrofit2.http.GET;

public class ChatActivity extends AppCompatActivity {
    TextView chatUsername, senderDistance, senderTime;
    ImageView chatBackarrow, chatFollow, chatSetting, sendButton, micIcon,gifIcon, imageIcon, giftIcon, smileIcon, optionIcon, videoIcon, locationIcon;
    LinearLayout optionLayout;
    TextInputEditText msgType;
    EmojiconEditText emojiconEditText, emojiconEditText2;
    EmojIconActions emojIcon;
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatUsername = findViewById(R.id.chatUsername);
        senderDistance = findViewById(R.id.senderDistance);
        senderTime = findViewById(R.id.senderTime);
        chatBackarrow = findViewById(R.id.chatBackarrow);
        chatFollow = findViewById(R.id.chatFollow);
        chatSetting =findViewById(R.id.chatSetting);
        sendButton = findViewById(R.id.sendButton);
        micIcon = findViewById(R.id.micIcon);
        gifIcon = findViewById(R.id.gifIcon);
        imageIcon = findViewById(R.id.imageIcon);
        giftIcon = findViewById(R.id.giftIcon);
        smileIcon = findViewById(R.id.smileIcon);
        optionIcon = findViewById(R.id.optionIcon);
        videoIcon = findViewById(R.id.videoIcon);
        locationIcon = findViewById(R.id.locationIcon);
        emojiconEditText = findViewById(R.id.emojicon_edit_text2);
        rootView = findViewById(R.id.parent_view);
        emojIcon = new EmojIconActions(this, rootView , emojiconEditText,  smileIcon );
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard","open");
            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard","close");
            }
        });

        optionLayout.setVisibility(View.GONE);


    }
    public void optionClick(){
        if ((optionLayout.getVisibility() == View.GONE)) {
            optionLayout.setVisibility(View.VISIBLE);
        } else {
            optionLayout.setVisibility(View.GONE);
        }
    }

    public void emojiClick(){

    }

    public void optionClick(View view) {
    }
}