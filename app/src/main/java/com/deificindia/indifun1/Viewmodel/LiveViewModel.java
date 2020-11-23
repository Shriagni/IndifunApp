package com.deificindia.indifun1.Viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.deificindia.indifun1.Model.CountryNamesResult;
import com.deificindia.indifun1.Model.GetFollow_Result;
import com.deificindia.indifun1.Model.retro.NewstarModal;
import com.deificindia.indifun1.Model.retro.TrendingModal;
import com.deificindia.indifun1.rest.AppConfig;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveViewModel extends ViewModel {



    /*201026 @spario*/


    private MutableLiveData<TrendingModal> mutableLiveData_trending;
    private MutableLiveData<CountryNamesResult> mutableLiveData_country;

    private LiveRepository newsRepository;

    public LiveViewModel() { }

    public void init(){
        newsRepository = LiveRepository.getInstance();
    }

    /*friends*/
    public  MutableLiveData<DataWrapper<GetFollow_Result>> get_follow_homepage(String userId) {
        MutableLiveData<DataWrapper<GetFollow_Result>> data = new MutableLiveData<>();

        Observable.fromCallable((Callable<String>) () -> {
            Call<GetFollow_Result> call = AppConfig.loadInterface().follow_homepage(userId);
            call.enqueue(new Callback<GetFollow_Result>() {
                @Override
                public void onResponse(Call<GetFollow_Result> call, Response<GetFollow_Result> response) {
                    if(response.isSuccessful()) {
                        GetFollow_Result result = response.body();
                        data.setValue(new DataWrapper<>(result, ""));

                    }else data.setValue(new DataWrapper<>(null, ""));
                }

                @Override
                public void onFailure(Call<GetFollow_Result> call, Throwable t) {
                    data.setValue(new DataWrapper<>(null, ""));
                }
            });
            return "";
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });

        return data;
    }

//--------------------------------------------------------------------------------------------------
    /*recomended setion of view model*/
    public MutableLiveData<DataWrapper<GetFollow_Result>> follow_homepage_recommended(String userId) {
        MutableLiveData<DataWrapper<GetFollow_Result>> data = new MutableLiveData<>();

        Call<GetFollow_Result> call = AppConfig.loadInterface().follow_homepage_recommended(userId);
        call.enqueue(new Callback<GetFollow_Result>() {
            @Override
            public void onResponse(Call<GetFollow_Result> call, Response<GetFollow_Result> response) {

                if(response.isSuccessful()){
                    GetFollow_Result resdata = response.body();
                    data.setValue(new DataWrapper<>(resdata, ""));
                }else {
                    data.setValue(new DataWrapper<>(null, ""));
                }
            }

            @Override
            public void onFailure(Call<GetFollow_Result> call, Throwable t) {
                //swipe_recomended.setRefreshing(false);
                data.setValue(new DataWrapper<>(null, null));
            }
        });

        return data;
    }
//===================================================================================================
    /*new star result*/
    public MutableLiveData<DataWrapper<NewstarModal>> newstar_india() {
        MutableLiveData<DataWrapper<NewstarModal>> data = new MutableLiveData<>();
        Call<NewstarModal> call = AppConfig.loadInterface().newstar_india();
        call.enqueue(new Callback<NewstarModal>() {
            @Override
            public void onResponse(Call<NewstarModal> call, Response<NewstarModal> response) {

                if(response.isSuccessful()){
                    NewstarModal resdata = response.body();
                    data.setValue(new DataWrapper<>(resdata, ""));
                }else {
                    data.setValue(new DataWrapper<>(null, "no response"));
                }
            }

            @Override
            public void onFailure(Call<NewstarModal> call, Throwable t) {
                //swipe_recomended.setRefreshing(false);
               // mutableLiveData_newstar_india;
                data.setValue(new DataWrapper<>(null, "error"));
            }
        });
        return data;
    }

    /*trending result*/

    public MutableLiveData<DataWrapper<TrendingModal>> trending() {
        MutableLiveData<DataWrapper<TrendingModal>> data = new MutableLiveData<>();
        Call<TrendingModal> call = AppConfig.loadInterface().trending();
        call.enqueue(new Callback<TrendingModal>() {
            @Override
            public void onResponse(Call<TrendingModal> call, Response<TrendingModal> response) {

                if(response.isSuccessful()){
                    TrendingModal resdata = response.body();
                    data.setValue(new DataWrapper<>(resdata, ""));
                }else {
                    data.setValue(new DataWrapper<>(null, "no response"));
                }

            }

            @Override
            public void onFailure(Call<TrendingModal> call, Throwable t) {
                //swipe_recomended.setRefreshing(false);
                data.setValue(new DataWrapper<>(null, "error response"));
            }
        });
        return data;
    }



}
