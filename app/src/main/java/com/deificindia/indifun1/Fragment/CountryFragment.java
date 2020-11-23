package com.deificindia.indifun1.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deificindia.indifun1.Adapter.ExploreCountryAdapter;
import com.deificindia.indifun1.Model.CountryNamesResult;
import com.deificindia.indifun1.Model.CountryUserResult;
import com.deificindia.indifun1.Model.CountryUsers;
import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.EqualSpacingItemDecoration;
import com.deificindia.indifun1.Utility.StartSnapHelper;
import com.deificindia.indifun1.Viewmodel.CountryViewmodel;
import com.deificindia.indifun1.Viewmodel.DataWrapper;
import com.deificindia.indifun1.ui.LoadingAnimView;
import com.deificindia.indifun1.ui.swipe.SwipeRefreshLayout;

import java.util.List;

import static com.deificindia.indifun1.Utility.ActivityUtils.openUserDetailsActivity;
import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.Logger.toGson;
import static com.deificindia.indifun1.Utility.UiUtils.setSwipeRefreshColor;

public class CountryFragment extends Fragment implements View.OnClickListener {

    RecyclerView countryList, userList;
    TextView txtcountryname;

    SwipeRefreshLayout swipeRefreshLayout;
    LoadingAnimView loadingAnimView;

    ImageView leftArrow, rightArrow;


    CountryViewmodel countryViewmodel;
    ExploreCountryAdapter exploreCountryAdapter, exploreCountryAdapterUsers;

    LinearLayoutManager linearLayoutManager;

    boolean programaticallyScrolled;
    int currentVisibleItem = 0;

    public CountryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        countryList = view.findViewById(R.id.countryRecycler);
        userList = view.findViewById(R.id.countryUserRecycler);
        txtcountryname = view.findViewById(R.id.txtCountry);
        leftArrow = view.findViewById(R.id.leftArrow);
        rightArrow = view.findViewById(R.id.rightArrow);

        rightArrow.setOnClickListener(this);

        swipeRefreshLayout =  view.findViewById(R.id.swipe_refresh);
        loadingAnimView = view.findViewById(R.id.loadinganim);
        setSwipeRefreshColor(swipeRefreshLayout);

        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager linear2 = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        //GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

        countryList.setHasFixedSize(false);
        countryList.setLayoutManager(linearLayoutManager);
        countryList.addItemDecoration(new EqualSpacingItemDecoration(5, EqualSpacingItemDecoration.HORIZONTAL));
        SnapHelper snapHelper = new StartSnapHelper();
        snapHelper.attachToRecyclerView(countryList);
        /*countryList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_DRAGGING:
                        //Indicated that user scrolled.
                        programaticallyScrolled = false;
                        break;
                    case SCROLL_STATE_IDLE:
                        if (!programaticallyScrolled) {
                            currentVisibleItem = linear.findFirstCompletelyVisibleItemPosition();
                            handleWritingViewNavigationArrows(false);
                        }
                        break;
                    default:
                        break;
                }
            }
        });*/

        userList.setHasFixedSize(false);
        userList.setLayoutManager(linear2);
        userList.addItemDecoration(new EqualSpacingItemDecoration(5));

        countryViewmodel = ViewModelProviders.of(this).get(CountryViewmodel.class);

        //loadingAnimView = view.findViewById(R.id.loadinganim);

        setupAdapterCountry();
        call_country_viewmodel();

        swipeRefreshLayout.setRefreshing(true);
        loadingAnimView.startloading();

    }

    private void handleWritingViewNavigationArrows(boolean scroll) {
        if (currentVisibleItem == (countryList.getAdapter().getItemCount() - 1)) {
            rightArrow.setVisibility(View.GONE);
            leftArrow.setVisibility(View.VISIBLE);
        } else if (currentVisibleItem != 0) {
            rightArrow.setVisibility(View.VISIBLE);
            leftArrow.setVisibility(View.VISIBLE);
        } else if (currentVisibleItem == 0) {
            leftArrow.setVisibility(View.GONE);
            rightArrow.setVisibility(View.VISIBLE);
        }
        if (scroll) {
            countryList.smoothScrollToPosition(currentVisibleItem);
        }
    }

    void setupAdapterCountry(){
        if(exploreCountryAdapter ==null) {
            exploreCountryAdapter = new ExploreCountryAdapter(getContext(), 1);
            countryList.setAdapter(exploreCountryAdapter);
            exploreCountryAdapter.setOnCountryClickListener(new ExploreCountryAdapter.OnCountryClickListener() {
                @Override
                public void onCountryClick(int pos, CountryNamesResult.MyCountry country) {
                    loadById(country);
                }

            });
        }else {
            exploreCountryAdapter.notifyDataSetChanged();
        }
    }

    void call_country_viewmodel(){
        countryViewmodel.getCountry().observe(this.getActivity(), new Observer<DataWrapper<CountryNamesResult>>() {
            @Override
            public void onChanged(DataWrapper<CountryNamesResult> result) {
                if (result.data != null && result.data.getStatus() == 1 && result.data.getResult() != null) {
                    List<CountryNamesResult.MyCountry> res = result.data.getResult();
                    exploreCountryAdapter.updateCountry(res);
                    if(res.get(0)!=null)
                        loadById(res.get(0));

                }else {
                    //loadingAnimView.showError();
                    //loadingAnimView.setErrText("");
                    txtcountryname.setVisibility(View.GONE);
                }

                //swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    void loadById(CountryNamesResult.MyCountry country){
        txtcountryname.setVisibility(View.VISIBLE);
        txtcountryname.setText(country.getCountry());
        call_country_users_viewmodel(country.getId());
    }

    void call_country_users_viewmodel(String countryid){
        userList.setAdapter(null);
        loadingAnimView.startloading();
        swipeRefreshLayout.setRefreshing(true);
        countryViewmodel.getCountryUsers(countryid).observe(getActivity(), new Observer<DataWrapper<CountryUsers>>() {
            @Override
            public void onChanged(DataWrapper<CountryUsers> result) {
                if (result.data != null && result.data.getStatus() == 1 && result.data.getResult() != null) {
                    List<CountryUserResult> res = result.data.getResult();
                    loge("Countryfra", "users", toGson(res));
                    //exploreCountryAdapterUsers.updateUsers(res);
                    setUsers(res);
                    loadingAnimView.stopAnim();
                }else {
                    loadingAnimView.showError();
                    loadingAnimView.setErrText("");
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    void setUsers(List<CountryUserResult> list){
        exploreCountryAdapterUsers = new ExploreCountryAdapter( list, getContext(), 2);
        userList.setAdapter(exploreCountryAdapterUsers);
        exploreCountryAdapterUsers.setOnCountryUserClickListener(usr -> {
            openUserDetailsActivity(getContext(), usr.getId(), usr.getFullName());
        });
    }

    int p;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rightArrow:
                //p = linearLayoutManager.findFirstVisibleItemPosition() - 1;
                //countryList.smoothScrollToPosition(p);
                //checkVisibility();
                countryList.smoothScrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);
                break;
            case R.id.leftArrow:
                //p = linearLayoutManager.findLastVisibleItemPosition() + 1;
                //countryList.smoothScrollToPosition(p);
                //checkVisibility();
                if (linearLayoutManager.findFirstVisibleItemPosition() > 0) {
                    countryList.smoothScrollToPosition(linearLayoutManager.findFirstVisibleItemPosition() - 1);
                } else {
                    countryList.smoothScrollToPosition(0);
                }
                break;
        }
    }

    public void checkVisibility() {
        if (p < 1) {
            rightArrow.setVisibility(View.GONE);
            leftArrow.setVisibility(View.VISIBLE);
        } else if (p >= (countryList.getAdapter().getItemCount() - 1)) {
            rightArrow.setVisibility(View.VISIBLE);
            leftArrow.setVisibility(View.GONE);
        } else {
            rightArrow.setVisibility(View.VISIBLE);
            leftArrow.setVisibility(View.VISIBLE);
        }
    }

}