package com.deificindia.indifun1.Viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.deificindia.indifun1.Model.CountryNamesResult;
import com.deificindia.indifun1.Model.CountryUsers;
import com.deificindia.indifun1.rest.AppConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryViewmodel extends ViewModel {

    /*Country result*/
    public LiveData<DataWrapper<CountryNamesResult>> getCountry() {
        MutableLiveData<DataWrapper<CountryNamesResult>> data = new MutableLiveData<>();
        Call<CountryNamesResult> call = AppConfig.loadInterface().getCountry();
        call.enqueue(new Callback<CountryNamesResult>() {
            @Override
            public void onResponse(Call<CountryNamesResult> call, Response<CountryNamesResult> response) {

                if(response.isSuccessful()){
                    CountryNamesResult resdata = response.body();
                    data.setValue(new DataWrapper<>(resdata, ""));
                }else {
                    data.setValue(new DataWrapper<>(null, "no response"));

                }
            }

            @Override
            public void onFailure(Call<CountryNamesResult> call, Throwable t) {
                //swipe_recomended.setRefreshing(false);
                data.setValue(new DataWrapper<>(null, "no response"));
            }
        });
        return data;
    }

    public LiveData<DataWrapper<CountryUsers>> getCountryUsers(String countryid) {
        MutableLiveData<DataWrapper<CountryUsers>> data = new MutableLiveData<>();
        Call<CountryUsers> call = AppConfig.loadInterface().getCountryUsers(countryid);
        call.enqueue(new Callback<CountryUsers>() {
            @Override
            public void onResponse(Call<CountryUsers> call, Response<CountryUsers> response) {

                if(response.isSuccessful()){
                    CountryUsers resdata = response.body();
                    data.setValue(new DataWrapper<>(resdata, ""));
                }else {
                    data.setValue(new DataWrapper<>(null, "no response"));

                }
            }

            @Override
            public void onFailure(Call<CountryUsers> call, Throwable t) {
                //swipe_recomended.setRefreshing(false);
                data.setValue(new DataWrapper<>(null, "no response"));
            }
        });
        return data;
    }

}
