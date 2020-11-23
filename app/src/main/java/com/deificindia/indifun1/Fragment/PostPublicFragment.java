package com.deificindia.indifun1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deificindia.indifun1.Adapter.HotPostAdapter;
import com.deificindia.indifun1.Model.Hotpostmodel;
import com.deificindia.indifun1.Model.retro.PostModal;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun1.Utility.IndifunApplication;
import com.deificindia.indifun1.rest.AppConfig;
import com.deificindia.indifun1.dialogs.CommentBottomSheetDialog;
import com.deificindia.indifun1.interfaces.OnCommentUserClickListener;
import com.deificindia.indifun1.rest.RetroCalls;
import com.deificindia.indifun1.ui.LoadingAnimView;
import com.deificindia.indifun1.ui.swipe.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.UiUtils.setSwipeRefreshColor;
import static com.deificindia.indifun1.rest.RetroCallListener.FOLLOW_POST;


public class PostPublicFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView hotlist;
    private HotPostAdapter hotPostAdapter;
    private List<Hotpostmodel.MyResult> hotpostmodelList = new ArrayList<>();
    CommentBottomSheetDialog bottomSheet;

    SwipeRefreshLayout swipeRefreshLayout;
    LoadingAnimView loadingAnimView;

    public PostPublicFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_hot, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hotlist = view.findViewById(R.id.hotlist);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        loadingAnimView = view.findViewById(R.id.lottieanim);

        swipeRefreshLayout.setOnRefreshListener(this);
        setSwipeRefreshColor(swipeRefreshLayout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        hotlist.setLayoutManager(linearLayoutManager);
        hotlist.addItemDecoration(new EqualSpacingItemDecoration(5));

        loadData();


    }

    void loadData(){
        swipeRefreshLayout.setRefreshing(true);
        loadingAnimView.startloading();
        String user_id = IndifunApplication.getInstance().getCredential().getId();
        Call<Hotpostmodel> call = AppConfig.loadInterface().get_hotpost(user_id);
        call.enqueue(new Callback<Hotpostmodel>() {
            @Override
            public void onResponse(Call<Hotpostmodel> call, retrofit2.Response<Hotpostmodel> response) {

                Hotpostmodel model = response.body();

                //loge("HOTFRAG",new Gson().toJson(model) );
                if(model!=null && model.getStatus()==1 && model.getResult()!=null){
                    hotpostmodelList = model.getResult();
                    hotPostAdapter = new HotPostAdapter(hotpostmodelList, getActivity());
                    hotPostAdapter.setOnCommentUserClickListener(new OnCommentUserClickListener() {
                        @Override
                        public boolean onComment(int pos, String id, String postby) {
                            bottomSheet = new CommentBottomSheetDialog();
                            bottomSheet.setOnMessageSent(new CommentBottomSheetDialog.OnMessageSent() {
                                @Override
                                public void onSend(String message) {
                                    on_comment(pos, postby, message);
                                }
                            });
                            bottomSheet.show(getChildFragmentManager(), "ModalBottomSheet");
                            return false;
                        }
                    });
                    hotlist.setAdapter(hotPostAdapter);
                    loadingAnimView.stopAnim();
                }else{
                    loadingAnimView.showError();
                    loadingAnimView.setErrText("No data");
                }

                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<Hotpostmodel> call, Throwable t) {
                //swipe_friend.setRefreshing(false);
                loge("HotfragErr", t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
                loadingAnimView.showError();
                loadingAnimView.setErrText("No data");
            }
        });
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
                            hotPostAdapter.onPostComment(pos);
                            //commentll.setVisibility(View.GONE);
                            if(bottomSheet!=null) bottomSheet.dismiss();
                        } else {

                        }
                    }

                }, (type, code, message) -> {

                });
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}