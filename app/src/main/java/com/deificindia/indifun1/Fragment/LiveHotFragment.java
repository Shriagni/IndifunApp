package com.deificindia.indifun1.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deificindia.indifun1.Adapter.HotAdapter;
import com.deificindia.indifun1.Model.GetFollow_Result;
import com.deificindia.indifun1.Model.retro.LiveResult;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun1.Utility.MySharePref;
import com.deificindia.indifun1.rest.AppConfig;
import com.deificindia.indifun1.ui.LoadingAnimView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LiveHotFragment extends Fragment implements com.deificindia.indifun1.ui.swipe.SwipeRefreshLayout.OnRefreshListener {

    private boolean isattached;

    private RecyclerView hotrecycle;
    private com.deificindia.indifun1.ui.swipe.SwipeRefreshLayout swiperefresh;
    private HotAdapter hotAdapter;
    private List<LiveResult> hotmodelList = new ArrayList<>();

    LoadingAnimView loadingAnimView;

    public LiveHotFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_hot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    void initView(View view){
        hotrecycle = view.findViewById(R.id.hotrecycler);
        swiperefresh = view.findViewById(R.id.swipe_refresh);
        loadingAnimView = view.findViewById(R.id.lottieanim);

        swiperefresh.setOnRefreshListener(this);
        swiperefresh.setColorScheme(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        hotrecycle.setLayoutManager(gridLayoutManager);
        hotrecycle.addItemDecoration(new EqualSpacingItemDecoration(5));


        swiperefresh.post(this::liveHot);

    }

    @Override
    public void onRefresh() {
        hotrecycle.setAdapter(null);
        liveHot();
    }

    void liveHot(){
        swiperefresh.setRefreshing(true);
        loadingAnimView.startloading();
        loadingAnimView.setErrText("Loading...");
        String str_uid = MySharePref.getUserId();
        Call<GetFollow_Result> call = AppConfig.loadInterface().hot_homepage(str_uid);
        call.enqueue(new Callback<GetFollow_Result>() {
            @Override
            public void onResponse(Call<GetFollow_Result> call, Response<GetFollow_Result> response) {
                GetFollow_Result resdata = response.body();
                //loge("LoveHotFrag",new Gson().toJson(resdata) );
                if(resdata!=null && resdata.getStatus()==1 && resdata.getResult()!=null){
                    hotmodelList = resdata.getResult();
                    callAdapter();
                    loadingAnimView.stopAnim();
                }else{
                    loadingAnimView.showError();
                    loadingAnimView.setErrText("No data...");
                }
                swiperefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<GetFollow_Result> call, Throwable t) {
                //Toast.makeText(getContext(),"error is" + t.getMessage(), Toast.LENGTH_LONG);
                swiperefresh.setRefreshing(false);
                loadingAnimView.showError();
                loadingAnimView.setErrText("No data");
            }
        });
    }


    void callAdapter(){
        hotAdapter = new HotAdapter(hotmodelList, getActivity());
        hotrecycle.setAdapter(hotAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        isattached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isattached = false;
    }
}