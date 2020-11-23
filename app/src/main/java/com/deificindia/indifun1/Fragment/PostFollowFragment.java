package com.deificindia.indifun1.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deificindia.indifun1.Adapter.ExplorFollowAdapter;
import com.deificindia.indifun1.Model.Hotpostmodel;
import com.deificindia.indifun1.Model.retro.PostModal;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun1.rest.AppConfig;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.dialogs.CommentBottomSheetDialog;
import com.deificindia.indifun1.interfaces.OnCommentUserClickListener;
import com.deificindia.indifun1.rest.RetroCalls;
import com.deificindia.indifun1.ui.LoadingAnimView;
import com.deificindia.indifun1.ui.swipe.SwipeRefreshLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.UiUtils.setSwipeRefreshColor;
import static com.deificindia.indifun1.rest.RetroCallListener.FOLLOW_POST;


public class PostFollowFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView rv_followe;
    private String user_id;
    private List<Hotpostmodel.MyResult> explorFollow_results = new ArrayList<>();
    CommentBottomSheetDialog bottomSheet;

    SwipeRefreshLayout swipeRefreshLayout;
    LoadingAnimView loadingAnimView;

    boolean ispostingcomment = false;

    public PostFollowFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_follow, container, false);

        rv_followe = view.findViewById(R.id.rv_followe);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        loadingAnimView = view.findViewById(R.id.lottieanim);

        swipeRefreshLayout.setOnRefreshListener(this);
        setSwipeRefreshColor(swipeRefreshLayout);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rv_followe.setHasFixedSize(false);
        rv_followe.setLayoutManager(layoutManager);
        rv_followe.addItemDecoration(new EqualSpacingItemDecoration(5));
        user_id = IndifunApplication.getInstance().getCredential().getId();

       getFollow();

       return view;
    }

    private void getFollow() {
        swipeRefreshLayout.setRefreshing(true);
        loadingAnimView.startloading();
        String user_id = IndifunApplication.getInstance().getCredential().getId();
        Call<Hotpostmodel> call = AppConfig.loadInterface().get_post1(user_id);
        call.enqueue(new Callback<Hotpostmodel>() {
            @Override
            public void onResponse(Call<Hotpostmodel> call, Response<Hotpostmodel> response) {

                    Hotpostmodel result = response.body();
                    loge("Follow frag", new Gson().toJson(result));
                    if(result!=null && result.getStatus()==1 && result.getResult()!=null && isAttached){
                         initAdapter(result.getResult());
                        loadingAnimView.stopAnim();
                    }else{
                        loadingAnimView.showError();
                        loadingAnimView.setErrText("No data");
                    }

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Hotpostmodel> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                loadingAnimView.showError();
                loadingAnimView.setErrText("No data");
            }
        });
    }

    ExplorFollowAdapter followAdapter;
    void initAdapter(List<Hotpostmodel.MyResult> res){
        if(res!=null && !res.isEmpty()) {
            explorFollow_results=res;
            followAdapter = new ExplorFollowAdapter(this.getActivity(), explorFollow_results, getChildFragmentManager());
            followAdapter.setOnCommentUserClickListener(new OnCommentUserClickListener() {
                @Override
                public boolean onComment(int pos, String id, String postby) {
                    if(!ispostingcomment) {
                        bottomSheet = new CommentBottomSheetDialog();
                        bottomSheet.setOnMessageSent(new CommentBottomSheetDialog.OnMessageSent() {
                            @Override
                            public void onSend(String message) {
                                on_comment(pos, postby, message);
                            }
                        });
                        bottomSheet.show(getChildFragmentManager(), "ModalBottomSheet");
                    }else
                        Toast.makeText(getContext(), "Comment posting already in progress", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            rv_followe.setAdapter(followAdapter);
        }
    }

    void on_comment(int pos,String post_by, String comment){
        RetroCalls.instance().callType(FOLLOW_POST)
                .withUID(getContext())
                .stringParam(post_by, comment)
                .intParam(0)
                .listeners((call_type, obj) -> {
                    if(obj!=null){
                        PostModal ob = (PostModal) obj;
                        if(ob.getStatus()==1){
                            followAdapter.onPostComment(pos);
                            //commentll.setVisibility(View.GONE);

                        } else {

                        }

                        if(bottomSheet!=null) bottomSheet.dismiss();
                        ispostingcomment = false;
                    }

                }, (type, code, message) -> {
                    ispostingcomment = false;
                });
    }

    private boolean isAttached;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        isAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
    }

    @Override
    public void onRefresh() {
        getFollow();
    }
}