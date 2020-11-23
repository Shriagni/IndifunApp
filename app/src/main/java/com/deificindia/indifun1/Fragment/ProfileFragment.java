package com.deificindia.indifun1.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun1.Activity.ActivityUserLevel;
import com.deificindia.indifun1.Activity.AristocarticityCenter;
import com.deificindia.indifun1.Activity.BroadcastsWatchActivity;
import com.deificindia.indifun1.Activity.DiscoverGroups;
import com.deificindia.indifun1.Activity.FFFGActivity;
import com.deificindia.indifun1.Activity.IncomeActivity;
import com.deificindia.indifun1.Activity.MedalsActivity;
import com.deificindia.indifun1.Activity.MessagesActivity;
import com.deificindia.indifun1.Activity.OnlineCustomerService;
import com.deificindia.indifun1.Activity.ProfileActivity;
import com.deificindia.indifun1.Activity.RechargeCoins;
import com.deificindia.indifun1.Activity.SettingActivity;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.Progress_Dialogue;
import com.deificindia.indifun1.Utility.URLs;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private RelativeLayout rl_income, rl_aristocracycenter,dicovergroupsrl,
            rechargecoinsrl,rl_medalshall, rl_broadcastswathched,
            rl_setting, onlinecustomerrl, rl_messageLayout, rl_userlevel;

    private ImageView goprofile;
    private CircleImageView profile_image;
    private String following,groups,followers,friends;
    private TextView friends1,following1,followers1,groups1, profile_name,profile_id;
    private LinearLayout profilell, layFriend, layFollowing, layFollower, layGroup;
    private int pImage;

    public ProfileFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        rl_income = v.findViewById(R.id.rl_income);
        rl_medalshall = v.findViewById(R.id.rl_medalshall);
        rl_broadcastswathched = v.findViewById(R.id.rl_broadcastswathched);
        rl_setting = v.findViewById(R.id.rl_setting);
        rl_messageLayout = v.findViewById(R.id.messageLayout);
        rl_userlevel = v.findViewById(R.id.userlevel);
        goprofile = v.findViewById(R.id.goprofile);

        friends1 = v.findViewById(R.id.friends1);
        following1 = v.findViewById(R.id.following1);
        groups1 = v.findViewById(R.id.groups1);
        followers1 = v.findViewById(R.id.followers1);

        profile_image=v.findViewById(R.id.profile_image);
        profile_name=v.findViewById(R.id.profile_name);
        profile_id=v.findViewById(R.id.profile_id);
        onlinecustomerrl=v.findViewById(R.id.onlinecustomerrl);
        profilell=v.findViewById(R.id.profilell);
        rechargecoinsrl=v.findViewById(R.id.rechargecoinsrl);
        dicovergroupsrl=v.findViewById(R.id.dicovergroupsrl);
        rl_aristocracycenter=v.findViewById(R.id.rl_aristocracycenter);


        layFriend=v.findViewById(R.id.lay_friend);
        layFollowing=v.findViewById(R.id.lay_following);
        layFollower=v.findViewById(R.id.lay_followers);
        layGroup=v.findViewById(R.id.lay_groups);
        layFriend.setOnClickListener(new OnFFGCLick());
        layFollowing.setOnClickListener(new OnFFGCLick());
        layFollower.setOnClickListener(new OnFFGCLick());
        layGroup.setOnClickListener(new OnFFGCLick());
        rl_messageLayout.setOnClickListener(new OnFFGCLick());
        rl_userlevel.setOnClickListener(new OnFFGCLick());



        rl_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), IncomeActivity.class);
                startActivity(i);
            }
        });

        onlinecustomerrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), OnlineCustomerService.class);
                startActivity(i);
            }
        });

        rl_aristocracycenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AristocarticityCenter.class);
                startActivity(i);
            }
        });

        dicovergroupsrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DiscoverGroups.class);
                startActivity(i);
            }
        });

        profilell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                i.putExtra("UID", IndifunApplication.getInstance().getCredential().getId());
                i.putExtra("NAME", IndifunApplication.getInstance().getCredential().getFullName());
                startActivity(i);
            }
        });

        rechargecoinsrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RechargeCoins.class);
                startActivity(i);
            }
        });

        rl_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SettingActivity.class);
                startActivity(i);
            }
        });

        rl_medalshall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MedalsActivity.class);
                startActivity(i);
            }
        });

        rl_broadcastswathched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BroadcastsWatchActivity.class);
                startActivity(i);
            }
        });



        profile_name.setText(IndifunApplication.getInstance().getCredential().getFullName());
        profile_id.setText(IndifunApplication.getInstance().getCredential().getUid());

        getdashboard();
        getFriends_api();

        if (IndifunApplication.getInstance().getCredential().getProfilePicture() != null && !IndifunApplication.getInstance().getCredential().getProfilePicture().isEmpty()) {
            defaultImage();
            Picasso.get()
                    .load(URLs.UserImageBaseUrl+IndifunApplication.getInstance().getCredential().getProfilePicture())
                    .error(pImage)
                    .into(profile_image);
        } else {
            defaultImage();
        }
        String gender = IndifunApplication.getInstance().getCredential().getGender();


        return v;
    }
    String gender = IndifunApplication.getInstance().getCredential().getGender();

    public void defaultImage(){

        if(gender.equals("Male")){
           pImage = R.drawable.ic_user__1_;
        }
        else{
            pImage = R.drawable.ic_user;
        }
        profile_image.setImageDrawable(ContextCompat.getDrawable(getContext(),pImage));
    }

    private void getdashboard() {
        final Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(getActivity(), "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.USERDASHBOARD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                JSONObject jsonObject = obj.optJSONObject("result");

                                following=jsonObject.optString("following");
                                followers=jsonObject.optString("followers");
                                friends=jsonObject.optString("friends");
                                groups=jsonObject.optString("groups");

                                followers1.setText(String.valueOf(followers));
                                following1.setText(String.valueOf(following));
                                friends1.setText(String.valueOf(friends));
                                groups1.setText(String.valueOf(groups));

                            } else {
                                myDialog.dismiss();

                                    Toast.makeText(getActivity(), obj.optString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                                Toast.makeText(getActivity(), getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();

                            Toast.makeText(getActivity(), getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                return params;
            }

        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    void getFriends_api(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.GETFRIENDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                //myDialog.dismiss();
                                JSONObject jsonObject = obj.optJSONObject("result");
                                if(jsonObject!=null) {
                                    Log.d("Profile", "onResponse: " + jsonObject.toString());
                                }
                                //friends1.setText("");
                            } else {
                                //myDialog.dismiss();
                                //Toast.makeText(getActivity(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            //myDialog.dismiss();
                            //Toast.makeText(getActivity(), getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //myDialog.dismiss();
                        Toast.makeText(getActivity(), getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                return params;
            }
        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }



    class OnFFGCLick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.lay_friend:
                    gotTofffgActivity(0);
                    break;
                case R.id.lay_following:
                    gotTofffgActivity(1);
                    break;
                case R.id.lay_followers:
                    gotTofffgActivity(2);
                    break;
                case R.id.lay_groups:
                    gotTofffgActivity(3);
                    break;
                case R.id.messageLayout:
                    startActivity(new Intent(getContext(), MessagesActivity.class));
                    break;
                case R.id.userlevel:
                    startActivity(new Intent(getContext(), ActivityUserLevel.class));
                    break;
            }

        }
    }

    void gotTofffgActivity(int n){
        Intent intent = new Intent(getContext(), FFFGActivity.class);
        intent.putExtra("TAB", n);
        intent.putExtra("FriendsN", friends1.getText().toString());
        intent.putExtra("FollowingN", following1.getText().toString());
        intent.putExtra("FollowersN", followers1.getText().toString());
        intent.putExtra("GroupsN", groups1.getText().toString());
        startActivity(intent);
    }
}