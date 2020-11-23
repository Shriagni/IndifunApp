package com.deificindia.indifun1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.google.firebase.auth.FirebaseAuth;

import static com.deificindia.indifun1.Utility.MySharePref.ISOTPVARIFIED;
import static com.deificindia.indifun1.Utility.MySharePref.getBoolData;

public class FirstLoginActivity extends AppCompatActivity {

    private CardView first_login;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_login);

        first_login = findViewById(R.id.first_login);
        img = findViewById(R.id.splashimage);

        first_login.setOnClickListener(v -> {
            Intent i = new Intent(FirstLoginActivity.this, LoginActivity.class);
            startActivity(i);
        });

        setBackgroundImage();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(IndifunApplication.getInstance().getCredential()!=null && getBoolData(FirstLoginActivity.this, ISOTPVARIFIED, false)){

            Intent intent = new Intent(FirstLoginActivity.this, SingupActivity.class);
            startActivity(intent);
            finish();
        }
    }

    void setBackgroundImage(){
        int viewWidth = img.getWidth();
        int viewHeight = img.getHeight();

        //img.setImageBitmap(decodeBitmapWithGiveSizeFromResource(getResources(), R.drawable.splashbg, viewWidth, viewHeight));
        img.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.splashbg));
    }
}