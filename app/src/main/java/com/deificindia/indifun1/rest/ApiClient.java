package com.deificindia.indifun1.rest;

import android.util.Log;

import androidx.databinding.library.baseAdapters.BuildConfig;

import com.deificindia.indifun1.R;
import com.deificindia.indifun1.Utility.api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.deificindia.indifun1.Utility.URLs.BaseUrl;

public class ApiClient {

    private static Retrofit retrofit = null;

    private static OkHttpClient okHttpClient = null;
    private static GsonConverterFactory gsonConverterFactory = null;

    public static ApiInterface getBaseApi() {
        return createRetrofitBase().create(ApiInterface.class);
    }

    public static Retrofit createRetrofitBase() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    //.client(okclient())
                    .build();
        }
        return retrofit;
    }


    public static OkHttpClient getOkHttpClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        if (BuildConfig.DEBUG)
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // add logging as last interceptor
        httpClient.addInterceptor(logging);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();
                okhttp3.Response response = chain.proceed (request);
                // todo deal with the issues the way you need to
                if (response.code() == 403 || response.code() == 401) {

                    return response;
                }
                Log.e("response of app", response.toString());
                return response;

            }
        });
        okHttpClient = httpClient.build();

        return okHttpClient;
    }

    private static final int DEFAULT_TIMEOUT_IN_SECONDS = 30;

    public static OkHttpClient okclient(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return okHttpClient;

    }

    private static GsonConverterFactory getGsonConverter() {

        if (gsonConverterFactory == null) {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .setLenient()
                    .create();
            gsonConverterFactory = GsonConverterFactory.create(gson);
        }
        return gsonConverterFactory;
    }
}
