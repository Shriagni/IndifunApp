package com.deificindia.indifun1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun1.Model.Hotpostmodel;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.Utility.MySharePref;
import com.deificindia.indifun1.Utility.URLs;
import com.deificindia.indifun1.dialogs.CommentBottomSheetDialog;
import com.deificindia.indifun1.interfaces.OnCommentUserClickListener;
import com.deificindia.indifun1.ui.LikeCommentPanel;
import com.deificindia.indifun1.ui.TagView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.deificindia.indifun1.Utility.ActivityUtils.openUserDetailsActivity;
import static com.deificindia.indifun1.Utility.UiUtils.setGenderTag;

public class ExplorFollowAdapter extends RecyclerView.Adapter<ExplorFollowAdapter.ViewHolder> {
    private List<Hotpostmodel.MyResult> task;
    CommentBottomSheetDialog bottomSheet;

    WeakReference<Context> _context;
    Context context(){
        return this._context.get();
    }

    OnCommentUserClickListener _listener;

    public void setOnCommentUserClickListener(OnCommentUserClickListener listener){
        this._listener = listener;
    }

    public ExplorFollowAdapter(Activity activity, List<Hotpostmodel.MyResult> task, FragmentManager fragmentManager) {
        this._context = new WeakReference<>(activity);
        this.task = task;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follow, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Hotpostmodel.MyResult data = task.get(position);

        if(data==null) return;

        if(data.getUser_name()!=null)
            holder.tv_name.setText(task.get(position).getUser_name());

        if (data.getImage() != null && !data.getImage().isEmpty()) {

            Picasso.get()
                    .load(URLs.UserImageBaseUrl+data.getImage())
                    .error(R.drawable.no_image)
                    .into(holder.iv_avtar);
        } else {
            holder.iv_avtar.setImageDrawable(context().getResources().getDrawable(R.drawable.no_image));
        }

       if(data.getIs_likes()==1){
            holder.likeCommentPanel.likeButton.setLiked(true);
            holder.likeCommentPanel.likeButton.setEnabled(false);
            holder.likeCommentPanel.likeEnable(false);
        }
        /* holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeButton.setEnabled(false);
                likepost(holder,position,task.get(position).getId());
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        });*/


        holder.likeCommentPanel.setLikeCount(String.valueOf(data.getTotal_likes()));
        holder.likeCommentPanel.setComment(String.valueOf(data.getTotal_comments()));
        holder.likeCommentPanel.setOnUserClickListener(()->{
            if(this._listener!=null){
                this._listener.onComment(position,
                        data.getId(),
                        data.getPost_by()
                );
            }
        }, ()->{

        });

        if(data.getAge()!=null) holder.genderTag.getTagText().setText(""+data.getAge());
        if(data.getGender()!=null)
            setGenderTag(holder.genderTag, data.getGender());

        if(data.getImage()!=null && !data.getImage().isEmpty()) holder.bind(data.getImage());
    }


    private void likepost(ViewHolder holder,int pos,String id) {
        //myDialog = DialogUtils.showProgressDialog(context(), "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_FOLLOWPOST,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                task.get(pos).updateTotal_Likes();

                                //holder.likeimage.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_like1));
                                /*holder.likeButton.setLiked(true);
                                holder.likeButton.setEnabled(false);
                                holder.likeButton.setClickable(false);*/
                                notifyItemChanged(pos);
                            } else {
                                Toast.makeText(context(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context(), "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context(), "Please Check your internet connection..!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", MySharePref.getUserId());
                params.put("post_id", id);
                params.put("is_like", "1");
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
        ImageView iv_avtar;
        TagView genderTag;

        TextView tv_name;
        RecyclerView recyclerView;

        LikeCommentPanel likeCommentPanel;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_avtar = itemView.findViewById(R.id.iv_avtar);
            genderTag = itemView.findViewById(R.id.genderTag);
            tv_name = itemView.findViewById(R.id.tv_name);


            recyclerView = itemView.findViewById(R.id.imageslist_recycler);

            likeCommentPanel = itemView.findViewById(R.id.like_commentPanel);

            itemView.setOnClickListener(v->{
               /* Intent int1 = new Intent(context(), ProfileActivity.class);
                int1.putExtra("UID", task.get(getAdapterPosition()).getId());
                int1.putExtra("NAME", task.get(getAdapterPosition()).getUser_name());
                //int1.putExtra("GENDER", task.get(getAdapterPosition()).getId());
                context().startActivity(int1);*/
                openUserDetailsActivity(context(), task.get(getAdapterPosition()).getPost_by(),
                        task.get(getAdapterPosition()).getUser_name());

            });

        }

        void bind(List<Hotpostmodel.MyResult.MyImages> images){
            ImageListAdapter adapter = new ImageListAdapter(context(), images,0);
            recyclerView.setLayoutManager(new GridLayoutManager(context(), 3));
            //recyclerView.addItemDecoration(new SpacesItemDecoration(5));
            recyclerView.setAdapter(adapter);

        }


    }

    public void onPostComment(int pos){
        task.get(pos).updateTotal_comments();
        notifyItemChanged(pos);
       // view.setEnabled(false);
        //view.setClickable(false);
    }

}
