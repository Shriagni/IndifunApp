package com.deificindia.indifun1.rest;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.deificindia.indifun1.Model.retro.PostModal;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.IndifunApplication;

import static com.deificindia.indifun1.rest.RetroCallListener.ONFOLLOWCLICK;

public class RestWork {

    public static void FollowApi(AppCompatButton btn, String followedBy){
        changeFolowButton(btn,2);
        RetroCalls.instance().callType(ONFOLLOWCLICK)
                .withUID()
                .stringParam(followedBy)
                .listeners((call_type, obj) -> {
                    if(call_type==ONFOLLOWCLICK) {
                        if (obj != null) {
                            PostModal ob = (PostModal) obj;
                            if (ob.getStatus() == 1) {
                                changeFolowButton(btn, 1);
                            } else {
                                changeFolowButton(btn, 0);
                            }
                        }
                    }

                }, (type, code, message) -> {
                    if(type==ONFOLLOWCLICK) {
                        changeFolowButton(btn, 0);
                    }
                });
    }

    private static void changeFolowButton(AppCompatButton btn , int n){
        if(n==1) {
            //btn.setBackgroundColor(ContextCompat.getColor(context(), R.color.gray_trans_90));
            //btn.setTextColor(ContextCompat.getColor(context(), R.color.black_trans_90));
            btn.setText("Following");
            btn.setEnabled(false);
        }else if(n==2){
            btn.setText("Follo...");
            btn.setEnabled(false);
        }else {
            //btn.setBackgroundColor(ContextCompat.getColor(context(), R.color.follow_btn_bg));
            //btn.setTextColor(ContextCompat.getColor(context(), R.color.follow_botton_color));
            btn.setText("Follow");
            btn.setEnabled(true);
        }
    }


}
