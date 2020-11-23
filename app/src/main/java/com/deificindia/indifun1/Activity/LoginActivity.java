package com.deificindia.indifun1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun1.Model.login.LoginResult;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.KeyClass;
import com.deificindia.indifun1.Utility.MySharePref;
import com.deificindia.indifun1.Utility.Progress_Dialogue;
import com.deificindia.indifun1.Utility.URLs;
import com.deificindia.indifun1.bindingmodals.login.Login;
import com.deificindia.indifun1.bindingmodals.otheruserprofile.OtherUserProfile;
import com.deificindia.indifun1.pojo.userpojo.Result;
import com.deificindia.indifun1.rest.AppConfig;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.Logger.toGson;
import static com.deificindia.indifun1.Utility.MySharePref.PHONE1;
import static com.deificindia.indifun1.Utility.MySharePref.USERID;
import static com.deificindia.indifun1.Utility.MySharePref.saveData;
import static com.deificindia.indifun1.rest.ApiClient.getBaseApi;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private EditText edt_phone_number;
    private RelativeLayout btn_continue;
    private TextView register_now;
    private Spinner country_spinner;
    private String number;
    private FirebaseAuth firebaseAuth;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;
    private CountryCodePicker flaglayout;
    View parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindView();
        viewSetup();
    }

    private void bindView() {
        edt_phone_number =  findViewById(R.id.edt_phone_number);
        btn_continue =  findViewById(R.id.btn_continue);
        flaglayout =  findViewById(R.id.ccp1);
        parent =  findViewById(R.id.parent);
    }

    private void viewSetup() {
        btn_continue.setOnClickListener(this);
        flaglayout.registerCarrierNumberEditText(edt_phone_number);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_continue:
                loginWithPhone();
                break;

        }
    }

    public void loginWithPhone(){
        String mobile = flaglayout.getFullNumberWithPlus();
        String mobileraw = edt_phone_number.getText().toString().trim();
        if (mobileraw != null && !mobileraw.isEmpty()&&mobileraw.length()<11) {
            loginperform(mobile);
        } else {
            edt_phone_number.setError("Enter a valid mobile number");
            edt_phone_number.setText("");
            edt_phone_number.requestFocus();
        }
    }

    private void loginperform(final String mobile) {

        myDialog = DialogUtils.showProgressDialog(LoginActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_lOGIN,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                               myDialog.dismiss();
                                JSONObject jsonObject = obj.optJSONObject("result");
                                Result result = new Result();
                                result.setId(jsonObject.getString("id"));
                                result.setUid(jsonObject.getString("uid"));
                                result.setFullName(jsonObject.getString("full_name"));
                                result.setEmail(jsonObject.getString("email"));
                                result.setContact(jsonObject.getString("contact"));
                                result.setLatitude(jsonObject.getString("latitude"));
                                result.setLongitude(jsonObject.getString("longitude"));
                                result.setProfilePicture(jsonObject.getString("profile_picture"));
                                result.setAge(jsonObject.getString("age"));
                                result.setDob(jsonObject.getString("dob"));
                               // result.setBio(jsonObject.getString("bio"));
                                result.setGender(jsonObject.getString("gender"));
                                //result.setLanguage(jsonObject.getString("language"));
                                result.setRelationship(jsonObject.getString("relationship"));
                                result.setCountry(jsonObject.getString("country"));
                                result.setState(jsonObject.getString("state"));
                                result.setCity(jsonObject.getString("city"));
                                result.setIsVerified(jsonObject.getString("is_verified"));

                                new Thread(()->{
                                    IndifunApplication.getInstance().saveCredential(result);

                                    try {
                                        MySharePref.saveData(getApplicationContext(), USERID, jsonObject.getString("id"));
                                        MySharePref.saveData(getApplicationContext(), PHONE1, jsonObject.getString("contact"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }).start();


                                Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                                intent.putExtra(KeyClass.PHONE_NUM, mobile);
                                //intent.putExtra(KeyClass.PUT_DATA, result);
                                startActivity(intent);
                                finish();
                            } else {
                                myDialog.dismiss();
                                Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();
                            //Toast.makeText(LoginActivity.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            showError(e);
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        showError(error);
                        //Toast.makeText(LoginActivity.this, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
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

    private void loginClick(String mobile){
        loge("UserDetails", "profile", mobile);
        AppConfig.loadInterface().login(mobile)
                .enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        loge("UserDetails", "profile", toGson(response));
                        if(response.isSuccessful()){
                            Login userProfile = response.body();
                            if(userProfile!=null && userProfile.getStatus()==1 && userProfile.getResult()!=null){

                                loge("LoginAct", "login", toGson(userProfile.getResult()));
                                new Thread(()->{
                                    IndifunApplication.getInstance().saveCredential(userProfile.getResult());

                                    MySharePref.saveData(getApplicationContext(), USERID, userProfile.getResult().getId());
                                    MySharePref.saveData(getApplicationContext(), PHONE1, userProfile.getResult().getContact());
                                }).start();


                                Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                                intent.putExtra(KeyClass.PHONE_NUM, mobile);
                                //intent.putExtra(KeyClass.PUT_DATA, userProfile.getResult());
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        loge("UserDetails", "profile", t.getMessage());
                    }
                });
    }

    private void startLogin(String mobile){
        getBaseApi().login(mobile)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::showError);
    }

    private void handleResults(LoginResult responseBody) {
            loge(TAG, "startLogin",responseBody.getResult().getUid());
    }

    private void showError(Throwable e) {
        loge(TAG, "Error", e.getMessage());
        String message = "";
        try {
            if (e instanceof IOException) {
                message = "No internet connection!";
            } else if (e instanceof HttpException) {
                HttpException error = (HttpException) e;
                String errorBody = error.response().errorBody().string();
                JSONObject jObj = new JSONObject(errorBody);

                message = jObj.getString("error");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (TextUtils.isEmpty(message)) {
            message = "Unknown error occurred!";
        }

        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

}