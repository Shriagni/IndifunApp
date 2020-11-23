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

import com.deificindia.indifun1.Adapter.RecommendadAdapter;
import com.deificindia.indifun1.Model.Recommendadmodel;
import com.deificindia.indifun1.Model.Recommended;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun1.interfaces.OnFilterUpdate;
import com.deificindia.indifun1.rest.AppConfig;
import com.deificindia.indifun1.ui.LoadingAnimView;
import com.deificindia.indifun1.ui.swipe.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.deificindia.indifun1.Utility.KeyClass.SAVE_AGE1;
import static com.deificindia.indifun1.Utility.KeyClass.SAVE_GENDER;
import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.MySharePref.getData;
import static com.deificindia.indifun1.Utility.MySharePref.getIntData;
import static com.deificindia.indifun1.Utility.UiUtils.setSwipeRefreshColor;
import static com.deificindia.indifun1.dialogs.FilterBottomSheetDialog.dataset;
import static com.deificindia.indifun1.interfaces.Event.setOnFilterUpdate;


public class PostRecommendedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnFilterUpdate {

    private RecyclerView recview;
    private RecommendadAdapter recommendadAdapter;
    private List<Recommendadmodel> recommendadmodelList = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;
    LoadingAnimView loadingAnimView;

    public PostRecommendedFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_recommended, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recview = view.findViewById(R.id.recomrecycler);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        loadingAnimView = view.findViewById(R.id.lottieanim);

        swipeRefreshLayout.setOnRefreshListener(this);
        setSwipeRefreshColor(swipeRefreshLayout);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recview.setLayoutManager(linearLayoutManager);
        recview.addItemDecoration(new EqualSpacingItemDecoration(5));
        setOnFilterUpdate(this);
        loadData();
    }

    void loadData(){
        swipeRefreshLayout.setRefreshing(true);
        loadingAnimView.startloading();

        String gender_selected = getData(getContext(), SAVE_GENDER,"All");
        int age_selected = getIntData(getContext(), SAVE_AGE1,0);

        String startAge="", endAge="";

        if(gender_selected.equalsIgnoreCase("All")){
            gender_selected = "";
        }

        String age_selected_str = "";
        if(dataset.get(age_selected).equalsIgnoreCase("All")){
            age_selected_str = "All";
        }else age_selected_str = dataset.get(age_selected);


        if(age_selected_str.contains("-")){
            String[] str = age_selected_str.split("-");
            if(str.length>1){
                startAge = str[0];
                endAge = str[1];
            }
        }

        Call<Recommended> call = AppConfig.loadInterface().getRecommendedPoast(gender_selected, startAge, endAge);
        call.enqueue(new Callback<Recommended>() {
            @Override
            public void onResponse(Call<Recommended> call, retrofit2.Response<Recommended> response) {

                Recommended model = response.body();

                //loge("HOTFRAG",new Gson().toJson(model) );
                if(model!=null && model.getStatus()==1 && model.getResult()!=null){
                    recommendadmodelList = model.getResult();
                    recommendadAdapter = new RecommendadAdapter(recommendadmodelList, getActivity());
                    recview.setAdapter(recommendadAdapter);

                    loadingAnimView.stopAnim();
                }else{
                    loadingAnimView.showError();
                    loadingAnimView.setErrText("No data");
                }

                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<Recommended> call, Throwable t) {
                //swipe_friend.setRefreshing(false);
                loge("HotfragErr", t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
                loadingAnimView.showError();
                loadingAnimView.setErrText("No data");
            }
        });

    }

    @Override
    public void onRefresh() {
        recview.setAdapter(null);
        loadData();
    }

    @Override
    public void onFileter() {
        recview.setAdapter(null);
        loadData();
    }
}