package com.deificindia.indifun1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.Progress_Dialogue;
import com.deificindia.indifun1.Utility.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {


    private ImageView img_back, applogo1;
    private TextView txt_header_title;
    private RelativeLayout rl_account, rl_terms,rl_general,rl_strikergallery, rl_privacy, rl_helpcenter, rl_about, rl_clearthecatch;
    private String terms,privacy,about;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        img_back = findViewById(R.id.img_back);
        txt_header_title = findViewById(R.id.txt_header_title);
        rl_account = findViewById(R.id.rl_account);
        rl_terms = findViewById(R.id.rl_terms);
        rl_privacy = findViewById(R.id.rl_privacy);
        rl_helpcenter = findViewById(R.id.rl_helpcenter);
        rl_about = findViewById(R.id.rl_about);
        rl_clearthecatch = findViewById(R.id.rl_clearthecatch);
        rl_general=findViewById(R.id.rl_general);
        rl_strikergallery=findViewById(R.id.rl_strikergallery);

        txt_header_title.setText("Settings");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        rl_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AccountActivity.class));
            }
        });
        rl_general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, GeneralActivity.class));
            }
        });

        rl_strikergallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, StickerActivity.class));
            }
        });

        rl_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, WebViewActivity.class)
                        .putExtra(AppConstants.VALUE, "Terms & Conditions")
                        .putExtra(AppConstants.HTMLCONTENT, terms));
            }
        });

        rl_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, WebViewActivity.class).putExtra(AppConstants.VALUE, "Privacy Policy")
                        .putExtra(AppConstants.HTMLCONTENT, privacy));
            }
        });
        rl_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, WebViewActivity.class).putExtra(AppConstants.VALUE, "About Indifun")
                        .putExtra(AppConstants.HTMLCONTENT, about));
            }
        });

        rl_clearthecatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File dir = getCacheDir();
                    deleteDir(dir);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        getterms();
        getabout();
        getprivacy();
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    private void getterms() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(SettingActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETTERMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                JSONObject jsonObject = obj.optJSONObject("result");
                                terms = jsonObject.optString("terms");


                            } else {
                                myDialog.dismiss();

                                // Toast.makeText(SettingActivity.this, obj.optString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(SettingActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();

                        Toast.makeText(SettingActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return null;
            }

        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }


    private void getabout() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(SettingActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETABOUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                JSONObject jsonObject = obj.optJSONObject("result");

                                about=jsonObject.optString("about");

                            } else {
                                myDialog.dismiss();

                                Toast.makeText(SettingActivity.this, obj.optString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(SettingActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();

                        Toast.makeText(SettingActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return null;
            }

        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    private void getprivacy() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(SettingActivity.this, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETPRIVACY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                JSONObject jsonObject = obj.optJSONObject("result");
                                privacy = jsonObject.optString("privacy_policy");


                            } else {
                                myDialog.dismiss();

                                // Toast.makeText(SettingActivity.this, obj.optString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(SettingActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();

                        Toast.makeText(SettingActivity.this, getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return null;
            }

        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        return super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportFragmentManager().popBackStackImmediate();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
}