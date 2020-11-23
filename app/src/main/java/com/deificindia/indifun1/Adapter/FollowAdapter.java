package com.deificindia.indifun1.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun1.Model.retro.LiveResult;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.ActivityUtils;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.Progress_Dialogue;
import com.deificindia.indifun1.Utility.URLs;
import com.deificindia.indifun1.agoraapis.maths.CallActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.ViewHolder> {
    private Activity activity;
    private List<LiveResult> task;
    private String type;
    private Dialog myDialog;
    private Progress_Dialogue DialogUtils;

    public FollowAdapter(Activity activity, List<LiveResult> task){
        this.activity = activity;
        this.task = task;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_follow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int pos) {
//        if(type.equals("bfollow")){
        LiveResult data = task.get(pos);
        holder.follow_name.setText(data.getUser_name());

        if (data.getImage() != null && !data.getImage().isEmpty()) {

            Picasso.get().load(URLs.UserImageBaseUrl+data.getImage())
                    .error(R.drawable.no_image)
                    .into(holder.follow_icon);
        } else {
            holder.follow_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.no_image));
        }
            /*Picasso.with(activity).load("https://deificindia.com/indifun/assets/images/user/" + task.get(pos).getprofile_picture())
                    .into(holder.follow_icon);*/
//        }else if(type.equals("bfollow")){
//            holder.follow_name.setText(task.get(pos).getfull_name());
//            Picasso.with(activity).load(URLs.UserImageBaseUrl + task.get(pos).getprofile_picture())
//                    .into(holder.follow_icon);
//            holder.follow_button.setVisibility(View.GONE);
//        }

    }

    private void followuser(ViewHolder holder,int pos,String id) {
        myDialog = DialogUtils.showProgressDialog(activity, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_FOLLOW,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();
                                notifyItemChanged(pos);
                                //holder.follow_button.setText("Unfollow");

                            } else {
                                myDialog.dismiss();
                                Toast.makeText(activity, obj.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();
                            Toast.makeText(activity, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(activity, "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String time,curentdate;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                curentdate=formatter.format(date);
                SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm a");
                Date date1 = new Date();
                time=formatter1.format(date1);
                params.put("user_id", IndifunApplication.getInstance().getCredential().getId());
                params.put("following_id", id);
                params.put("entry_date", curentdate);
                params.put("time", time);
                params.put("is_like", "");
                params.put("comments", "");
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

    @Override
    public int getItemCount() {
        return task.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView follow_icon;
        TextView follow_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            follow_icon = itemView.findViewById(R.id.follow_icon);
            follow_name = itemView.findViewById(R.id.follow_name);

            follow_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LiveResult itemobj = task.get(getAdapterPosition());
                    if(itemobj.getAdd_broadcast_id() !=null && itemobj.getAdd_broadcast_title() !=null)
                        CallActivity.joinSingleLiveActivity(activity, itemobj);
                    else
                        Toast.makeText(activity, "This broadcast could not be joined.", Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
