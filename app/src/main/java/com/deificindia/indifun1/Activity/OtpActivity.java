package com.deificindia.indifun1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.MySharePref;
import com.deificindia.indifun1.pojo.userpojo.Result;
import com.deificindia.indifun1.rest.AppConfig;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.KeyClass;
import com.deificindia.indifun1.Utility.LoadingDialog;
import com.deificindia.indifun1.Utility.Progress_Dialogue;
import com.deificindia.indifun1.Utility.URLs;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.Logger.toGson;
import static com.deificindia.indifun1.Utility.MySharePref.ISOTPVARIFIED;
import static com.deificindia.indifun1.Utility.MySharePref.USERID;
import static com.deificindia.indifun1.Utility.MySharePref.getData;
import static com.deificindia.indifun1.Utility.MySharePref.saveBoolData;
import static com.deificindia.indifun1.Utility.MySharePref.saveData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText otp1_et, otp2_et, otp3_et, otp4_et, otp5_et, otp6_et;
    private TextView txt_show_otp, phone_number;
    private String verificationId, otpid;
    private FirebaseAuth firebaseAuth;
    private RelativeLayout btn_countinue;
    String PhoneNum, str_uid, str_mob;
    String otp1, otp2, otp3, otp4, otp5, otp6;
    String verify_otp = "";
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    private TextView otptime,txt_resend;
    private CountDownTimer countDownTimer;
    private long totalTimeCountInMilliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        LoadingDialog.cancelLoading();
        bindView();
        viewSetup();


        str_uid = MySharePref.getUserId();
        str_mob = getData(getApplicationContext(), "mob", null);

    }

    private void bindView() {
        txt_show_otp = findViewById(R.id.txt_show_otp);
        phone_number = findViewById(R.id.phone_number);
        btn_countinue=findViewById(R.id.btn_countinue);
        otptime=findViewById(R.id.otptime);
        txt_resend=findViewById(R.id.txt_resend);
        otp1_et = findViewById(R.id.otp1_et);
        otp2_et = findViewById(R.id.otp2_et);
        otp3_et = findViewById(R.id.otp3_et);
        otp4_et = findViewById(R.id.otp4_et);
        otp5_et = findViewById(R.id.otp5_et);
        otp6_et = findViewById(R.id.otp6_et);
        txt_resend.setVisibility(View.GONE);
        otptime.setVisibility(View.VISIBLE);
        setTimer();
        startTimer();

        txt_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode(str_mob);
                txt_resend.setVisibility(View.GONE);
                otptime.setVisibility(View.VISIBLE);
                setTimer();
                startTimer();
            }
        });

        findViewById(R.id.img_back).setOnClickListener(v->{
            finish();
        });

    }


    private void viewSetup() {
        Intent intent = getIntent();
        PhoneNum = intent.getExtras().getString(KeyClass.PHONE_NUM);
        phone_number.setText(PhoneNum);

        otp1_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp1_et.getText().toString().length() == 1)
                    otp2_et.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                otp1 = otp1_et.getText().toString();
                System.out.println("first=====otp==================" + otp1);
            }
        });
        otp2_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp2_et.getText().toString().length() == 1)
                    otp3_et.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                otp2 = otp2_et.getText().toString();
                System.out.println("first=====otp==================" + otp2);
            }
        });
        otp3_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp3_et.getText().toString().length() == 1)
                    otp4_et.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                otp3 = otp3_et.getText().toString();
                System.out.println("first=====otp==================" + otp3);
            }
        });
        otp4_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp4_et.getText().toString().length() == 1)
                    otp5_et.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                otp4 = otp4_et.getText().toString();
                System.out.println("first=====otp==================" + otp4);
            }
        });
        otp5_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp5_et.getText().toString().length() == 1)
                    otp6_et.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                otp5 = otp5_et.getText().toString();
                System.out.println("first=====otp==================" + otp5);
            }
        });
        otp6_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (otp6_et.getText().toString().length() == 1)
                    otp6_et.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {
                otp6 = otp6_et.getText().toString();
                System.out.println("first=====otp==================" + otp6);
            }
        });
        btn_countinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_countinue:
                verify_otp = otp1 + otp2 + otp3 + otp4 + otp5 + otp6;
                System.out.println("sb wali=================" + verify_otp);
                if (verify_otp.equalsIgnoreCase("") || verify_otp == null) {
                    Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    OTPVERIFY(str_uid, verify_otp);
                }
                break;
        }
    }



    private void setTimer() {
        int time = 1;
        totalTimeCountInMilliseconds = 30 * time * 1000;

    }

    private void startTimer() {
        countDownTimer = new OtpCounter(totalTimeCountInMilliseconds, 500).start();
    }

    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    public void resumeTimer() {
        otptime.setVisibility(View.VISIBLE);
        //textViewShowTime.setVisibility(View.GONE);
        countDownTimer = new OtpCounter(totalTimeCountInMilliseconds, 500).start();
    }


    public class OtpCounter extends CountDownTimer {

        OtpCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long leftTimeInMilliseconds) {
            long seconds = leftTimeInMilliseconds / 1000;
            otptime.setText(String.format(Locale.getDefault(), "%02d", seconds / 60) + ":" + String.format(Locale.getDefault(), "%02d", seconds % 60));
        }

        @Override
        public void onFinish() {
            otptime.setVisibility(View.GONE);
            txt_resend.setVisibility(View.VISIBLE);
        }
    }

    public void clearFocus() {
        otp1_et.clearFocus();
        otp2_et.clearFocus();
        otp3_et.clearFocus();
        otp4_et.clearFocus();
        otp5_et.clearFocus();
        otp6_et.clearFocus();
    }

    /*/////////////////////////////////////////*/

    //resend verification code
    private void sendVerificationCode(final String mobile) {

        myDialog = DialogUtils.showProgressDialog(OtpActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_RESENDOTP,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                JSONObject jsonObject = obj.optJSONObject("result");
                                //  String str_result = object.getString("result");
                                //  JSONObject jsonObject = new JSONObject(str_result);
                                String str_uid = jsonObject.getString("id");
                                String str_mobile = jsonObject.getString("contact");
                                saveData(getApplicationContext(), "userId", str_uid);
                                saveData(getApplicationContext(), "mob", str_mobile);
                            } else {
                                myDialog.dismiss();
                                Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();
                            Toast.makeText(OtpActivity.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(OtpActivity.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("contact", mobile);
                return params;
            }

            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
               *//* Map<String, String> params = new HashMap<String, String>();
                params.put(AppConstants.TastyTiffinKEY, AppConstants.TastyTiffinKEYVALUE);
                return params;*//*
               return null;
            }*/
        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }
    //verify otp
    private void OTPVERIFY(String str_uid, String verify_otp) {

        myDialog = DialogUtils.showProgressDialog(OtpActivity.this, "Loading Please Wait...");

        Call<ResponseBody> call = AppConfig.loadInterface().verify_otp(str_uid, verify_otp);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                myDialog.dismiss();
                try {
                    String resdata = response.body().string();
                    JSONObject object = new JSONObject(resdata);
                    String error = object.getString("status");
                    System.out.println("resdata:::"+resdata);
                    if (error.equals("1")){
                        String str_result = object.getString("result");
                        JSONObject jsonObject = new JSONObject(str_result);

                        String str_full_name = jsonObject.getString("full_name");
                        String str_email = jsonObject.getString("email");
                        String str_contact = jsonObject.getString("contact");
                        saveBoolData(OtpActivity.this, ISOTPVARIFIED, true);

                        //loge("OTP", "verify otp", toGson(result));
                        //IndifunApplication.getInstance().saveCredential(result);

                        if(str_full_name.equals("null") && str_email.equals("null")){
                            Intent intent = new Intent(OtpActivity.this, SingupActivity.class);
                            intent.putExtra(KeyClass.PHONE_NUM, PhoneNum);
                            startActivity(intent);
                            finish();
                        }else {
                            saveData(getApplicationContext(), "ldata", str_result+"");
                            Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
                            intent.putExtra(KeyClass.PHONE_NUM, PhoneNum);
                            startActivity(intent);
                            finish();
                        }
                    }
                } catch (IOException | JSONException e) {
                    myDialog.dismiss();
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                myDialog.dismiss();
            }
        });
    }



}