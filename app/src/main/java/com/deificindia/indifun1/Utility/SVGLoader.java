package com.deificindia.indifun1.Utility;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.deificindia.indifun1.ui.svgandroid.SVG;
import com.deificindia.indifun1.ui.svgandroid.SVGParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.deificindia.indifun1.Utility.Logger.loge;
import static com.deificindia.indifun1.Utility.URLs.CountryFlagImages;

public class SVGLoader extends AsyncTask<Void, Void, Drawable> {

    private String endpoint;
    private OnResultListener _listener;

    public SVGLoader(String endpoint, OnResultListener listener) {
        this.endpoint = endpoint;
        this._listener = listener;
    }

    public interface OnResultListener{
        void onsuccessResult(Drawable drawable);
        void onFaileListener(String msg);
    }

    @Override
    protected Drawable doInBackground(Void... params) {
        try {

            final URL url = new URL(CountryFlagImages+endpoint);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            SVG svg = SVGParser. getSVGFromInputStream(inputStream);
            return svg.createPictureDrawable();
        } catch (Exception e) {
            loge("MainActivity", e.getMessage());
            if (_listener!=null) _listener.onFaileListener(e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Drawable drawable) {
        if (_listener!=null) _listener.onsuccessResult(drawable);
    }
}
