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

import com.deificindia.indifun1.Adapter.GlobalAdapter;
import com.deificindia.indifun1.Model.CountryNamesResult;
import com.deificindia.indifun1.Model.retro.NewstarModal;
import com.deificindia.indifun1.Model.retro.TrendingModal;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.MySharePref;
import com.deificindia.indifun1.Viewmodel.DataWrapper;
import com.deificindia.indifun1.Viewmodel.LiveViewModel;
import com.deificindia.indifun1.ui.LoadingAnimView;
import com.deificindia.indifun1.ui.swipe.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import static com.deificindia.indifun1.Utility.UiUtils.setSwipeRefreshColor;

public class NewStarFragment extends Fragment {

    public static final String ARG_PARAM1 = "VIEWTYPE";
    RecyclerView recyclerView;

    private List<NewstarModal.MyResult> newstar_results = new ArrayList<>();
    private List<CountryNamesResult.MyCountry> country_results = new ArrayList<>();
    private List<TrendingModal.MyResult> trending_results = new ArrayList<>();
    LiveViewModel liveViewModel;
    GlobalAdapter globalAdapter;

    com.deificindia.indifun1.ui.swipe.SwipeRefreshLayout swipeRefreshLayout;
    LoadingAnimView loadingAnimView;


    String str_uid;

    int type_data = 0;

    public NewStarFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_star, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        type_data = getArguments().getInt(ARG_PARAM1);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        GridLayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 3);
        LinearLayoutManager llmanager = new LinearLayoutManager(getActivity());
        str_uid = MySharePref.getUserId();

        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout =  view.findViewById(R.id.swipe_refresh);


        loadingAnimView = view.findViewById(R.id.loadinganim);

        swipeRefreshLayout.setOnRefreshListener(new swipeRefresh_Listener_Recomended());
        setSwipeRefreshColor(swipeRefreshLayout);

        recyclerView.setHasFixedSize(false);
        //recyclerView.addItemDecoration(new SpacesItemDecoration(5));

        liveViewModel = ViewModelProviders.of(this).get(LiveViewModel.class);
        liveViewModel.init();

        swipeRefreshLayout.setRefreshing(true);
        loadingAnimView.startloading();

        switch (type_data){
            case 0:
                recyclerView.setLayoutManager(layoutManager);
                setupAdapterNewstar();
                swipeRefreshLayout.post(this::call_new_star_india_viewmodel);
                break;
            case 1:
                //recyclerView.setLayoutManager(layoutManager2);
                //setupAdapterCountry();
                //swipeRefreshLayout.post(this::call_country_viewmodel);
                break;
            case 2:
                recyclerView.setLayoutManager(llmanager);
                setupAdapterTrending();
                swipeRefreshLayout.post(this::call_trending_viewmodel);
                break;
        }
    }

    /*---new star----*/
    void call_new_star_india_viewmodel(){
        if(this.getActivity()==null)return;
        liveViewModel.newstar_india().observe(this.getActivity(), new Observer<DataWrapper<NewstarModal>>() {
            @Override
            public void onChanged(DataWrapper<NewstarModal> result) {
                if (result.data != null && result.data.getStatus() == 1 && result.data.getResult() != null) {
                    newstar_results.clear();
                    newstar_results.addAll(result.data.getResult());

                    globalAdapter = new GlobalAdapter(0, getActivity(), newstar_results);
                    recyclerView.setAdapter(globalAdapter);
                    loadingAnimView.stopAnim();
                }else {
                    loadingAnimView.showError();
                    loadingAnimView.setErrText("");
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    void setupAdapterNewstar(){
        if(globalAdapter ==null) {
            globalAdapter = new GlobalAdapter(0, getActivity(), newstar_results);
            recyclerView.setAdapter(globalAdapter);
        }else {
            globalAdapter.notifyDataSetChanged();
        }
    }

    /*---country----*/

   /* void setupAdapterCountry(){
        if(globalAdapter ==null) {
            globalAdapter = new GlobalAdapter(  1, country_results, getActivity());
            recyclerView.setAdapter(globalAdapter);
        }else {
            globalAdapter.notifyDataSetChanged();
        }
    }*/

    /*---trending----*/
    void call_trending_viewmodel(){
        liveViewModel.trending().observe(this.getActivity(), new Observer<DataWrapper<TrendingModal>>() {
            @Override
            public void onChanged(DataWrapper<TrendingModal> result) {
                if (result.data != null && result.data.getStatus() == 1 && result.data.getResult() != null) {
                    trending_results.clear();
                    trending_results.addAll(result.data.getResult());
                    globalAdapter.notifyDataSetChanged();
                    loadingAnimView.stopAnim();
                }else {
                    loadingAnimView.showError();
                    loadingAnimView.setErrText("");
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    void setupAdapterTrending(){
        if(globalAdapter ==null) {
            globalAdapter = new GlobalAdapter( getActivity(), 2, trending_results);
            recyclerView.setAdapter(globalAdapter);
        }else {
            globalAdapter.notifyDataSetChanged();
        }
    }

    class swipeRefresh_Listener_Recomended implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            if(liveViewModel!=null){
                switch (type_data){
                    case 0:
                        call_new_star_india_viewmodel();
                        break;
                    case 1:
                        //call_country_viewmodel();
                        break;
                    case 2:
                        globalAdapter.user_rank = 0;
                        call_trending_viewmodel();
                        break;
                }
            }

        }
    }

    private boolean isAttached1;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        isAttached1 = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached1 = false;
    }

}