package com.deificindia.indifun1.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deificindia.indifun1.Adapter.FollowAdapter;
import com.deificindia.indifun1.Adapter.FollowAdapterRecomended;
import com.deificindia.indifun1.Model.GetFollow_Result;
import com.deificindia.indifun1.Model.retro.LiveResult;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun1.Utility.MySharePref;
import com.deificindia.indifun1.Viewmodel.DataWrapper;
import com.deificindia.indifun1.Viewmodel.LiveViewModel;
import com.deificindia.indifun1.ui.LoadingAnimView;

import java.util.List;

import static com.deificindia.indifun1.Utility.Logger.loge;


public class LiveFollowFragment extends Fragment implements com.deificindia.indifun1.ui.swipe.SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView_friend, recyclerView_recomended;
    private com.deificindia.indifun1.ui.swipe.SwipeRefreshLayout /*wipe_friend,*/ swipe_recomended;

    String str_uid;

    LiveViewModel liveViewModel;

    FollowAdapter followAdapter;
    FollowAdapterRecomended followAdapterRecomended;
    LoadingAnimView loadingAnimView;

    public LiveFollowFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_follow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linear = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        str_uid = MySharePref.getUserId();

        //swipe_friend = view.findViewById(R.id.swipe_follow_homepage);
        recyclerView_friend = (RecyclerView) view.findViewById(R.id.follow_homepage);

        recyclerView_friend.setHasFixedSize(false);
        recyclerView_friend.setLayoutManager(linear);
        recyclerView_friend.addItemDecoration(new EqualSpacingItemDecoration(5));

        loadingAnimView = view.findViewById(R.id.loadinganim);

        /*recomended*/
        recyclerView_recomended = (RecyclerView) view.findViewById(R.id.recycler_follow_homepage_recommended);
        swipe_recomended =  view.findViewById(R.id.swipe_follow_homepage_recommended);
        /*swipe_recomended.setOnRefreshListener(new swipeRefresh_Listener_Recomended());
        swipe_recomended.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);*/
        swipe_recomended.setOnRefreshListener(this);
        swipe_recomended.setColorScheme(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        recyclerView_recomended.setHasFixedSize(false);
        recyclerView_recomended.setLayoutManager(layoutManager);
        recyclerView_recomended.addItemDecoration(new EqualSpacingItemDecoration(5));

        loge("LiveFrag", str_uid);

        /**setting up things**/
        liveViewModel = ViewModelProviders.of(this).get(LiveViewModel.class);
        liveViewModel.init();

        get_follow_homepage();

        swipe_recomended.setRefreshing(true);
        swipe_recomended.post(this::get_follow_homepage_recommended);

    }

    /*friends section*/
    void get_follow_homepage(){

        liveViewModel.get_follow_homepage(str_uid).observe(this.getViewLifecycleOwner(), result -> {
            if (result.data != null && result.data.getStatus() == 1 && result.data.getResult() != null) {
                /*getFollow_results.clear();
                getFollow_results.addAll(result.data.getResult());
                followAdapter.notifyDataSetChanged();*/
                callFriendAdapter(result.data.getResult());
            }
        });
    }

    void callFriendAdapter(List<LiveResult> it){
        followAdapter = new FollowAdapter(getActivity(), it);
        recyclerView_friend.setAdapter(followAdapter);

    }

    /* recomended Sections*/
    void get_follow_homepage_recommended(){
        swipe_recomended.setRefreshing(true);
        loadingAnimView.startloading();
        liveViewModel.follow_homepage_recommended(str_uid).observe(this.getActivity(), new Observer<DataWrapper<GetFollow_Result>>() {
            @Override
            public void onChanged(DataWrapper<GetFollow_Result> result) {
                if(result.data!=null && result.data.getStatus() == 1 && result.data.getResult() != null){
                    //getFollow_recom_results.clear();
                    //getFollow_recom_results.addAll(result.data.getResult());
                    //followAdapterRecomended.notifyDataSetChanged();
                    callRecomendedAdapter(result.data.getResult());
                    loadingAnimView.stopAnim();
                }else {
                    loadingAnimView.showError();
                    loadingAnimView.setErrText("No live user..");
                }
                swipe_recomended.setRefreshing(false);
            }
        });

    }

    void callRecomendedAdapter(List<LiveResult> data){
        followAdapterRecomended = new FollowAdapterRecomended(getActivity(), data);
        recyclerView_recomended.setAdapter(followAdapterRecomended);

    }

    @Override
    public void onRefresh() {
        if(liveViewModel!=null) {
            recyclerView_recomended.setAdapter(null);
            get_follow_homepage_recommended();
            get_follow_homepage();
        }
    }

    @Override
    public void onDestroy() {
        liveViewModel = null;
        super.onDestroy();
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
}