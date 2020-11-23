package com.deificindia.indifun1.agorlive.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.deificindia.indifun1.R;
import com.deificindia.indifun1.agoraapis.api.mod.GiftInfo2;
import com.deificindia.indifun1.agorlive.ui.BaseActivity;

public class DisclaimerActivity extends BaseActivity {
    @Override
    protected void onSendGift(int position, GiftInfo2 info) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);
        hideStatusBar(true);

       /* findViewById(R.id.disclaimer_activity_close)
                .setOnClickListener(view -> finish());*/
    }

    @Override
    protected void onGlobalLayoutCompleted() {
        View topLayout = findViewById(R.id.activity_disclaimer_title_layout);
        if (topLayout != null) {
            RelativeLayout.LayoutParams params =
                    (RelativeLayout.LayoutParams)
                            topLayout.getLayoutParams();
            params.topMargin += systemBarHeight;
            topLayout.setLayoutParams(params);
        }
    }
}
