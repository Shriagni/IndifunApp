package com.deificindia.indifun1.Utility;

import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.deificindia.indifun1.R;
import com.deificindia.indifun1.ui.TagView;
import com.pixplicity.sharp.Sharp;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UiUtils {

    public static void setSwipeRefreshColor(SwipeRefreshLayout swipeRefreshColor){
        swipeRefreshColor.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
    }

    public static void setSwipeRefreshColor(com.deificindia.indifun1.ui.swipe.SwipeRefreshLayout swipeRefreshColor){
        /*swipeRefreshColor.setColorScheme(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);*/
    }

    public static void svgGlide(){



        /*requestBuilder = Glide.with(mActivity)
                .using(Glide.buildStreamModelLoader(Uri.class, mActivity), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.drawable.ic_facebook)
                .error(R.drawable.ic_web)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());*/

        /*Uri uri = Uri.parse("http://upload.wikimedia.org/wikipedia/commons/e/e8/Svg_example3.svg");
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                // SVG cannot be serialized so it's not worth to cache it
                .load(uri)
                .into(mImageView);*/
    }

    public static int randomedrawable(Context cnx){
        final TypedArray imgs = cnx.getResources().obtainTypedArray(R.array.imagesbg);
        final Random rand = new Random();
        final int rndInt = rand.nextInt(imgs.length());
        final int resID = imgs.getResourceId(rndInt, 0);
        return resID;
    }

    public static int randBw(int min, int max){
        final Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

//    /*
//     UiUtils.fetchSvg(context, URLs.CountryFlagImages + data.getFlag(), imgFlag, new UiUtils.onLoadData() {
//                        @Override
//                        public void onSuccess() {
//                            placeholder.setVisibility(View.GONE);
//                        }
//
//                        @Override
//                        public void onFail() {
//                            placeholder.setText(data.getCountry());
//                        }
//                    });
//
//    */

    private static OkHttpClient httpClient;

    public static void fetchSvg(Context context, String url, final ImageView target, onLoadData listener) {
        if (httpClient == null) {
            // Use cache for performance and basic offline capability
            httpClient = new OkHttpClient.Builder()
                    .cache(new Cache(context.getCacheDir(), 5 * 1024 * 1014))
                    .build();
        }

        Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                target.setImageResource(R.drawable.no_image);
                if(listener!=null) listener.onFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream stream = response.body().byteStream();
                Sharp.loadInputStream(stream).into(target);
                stream.close();
                if(listener!=null) listener.onSuccess();
            }
        });
    }

    public interface onLoadData{
        void onSuccess();
        void onFail();
    }

    public static String generateRandomString(boolean numeric, boolean lower,boolean upper, int length) {
        // You can customize the characters that you want to add into
        // the random strings
        String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
        String CHAR_UPPER = CHAR_LOWER.toUpperCase();
        String NUMBER = "0123456789";

        StringBuilder sb2 = new StringBuilder();
        //String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
        if(numeric) sb2.append(NUMBER);
        if(lower) sb2.append(CHAR_LOWER);
        if(upper) sb2.append(CHAR_UPPER);

        String DATA_FOR_RANDOM_STRING = sb2.toString();

        SecureRandom random = new SecureRandom();

        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            sb.append(rndChar);
        }

        return sb.toString();
    }

    public static void setGenderTag(TagView tag, String gender){
        if(gender.equalsIgnoreCase("Male")){
            tag.setTagImage(R.drawable.ic_male_gender);
            tag.changeBg(R.drawable.bg_male);
        }else {
            tag.setTagImage(R.drawable.ic_female_sign);
            tag.changeBg(R.drawable.bg_female);
        }
    }

    public static void setHtmlText(TextView tv, CharSequence str){
        Spanned text;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text = Html.fromHtml(str.toString(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            text = Html.fromHtml(str.toString());
        }

        tv.setText(text);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static Boolean isNetworkAvailable(Application application) {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network nw = connectivityManager.getActiveNetwork();
            if (nw == null) return false;
            NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
            return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
        } else {
            NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
            return nwInfo != null && nwInfo.isConnected();
        }
    }
}
