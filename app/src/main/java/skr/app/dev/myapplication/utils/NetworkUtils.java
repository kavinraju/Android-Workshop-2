package skr.app.dev.myapplication.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {

    private static final String BASE_URL ="https://newsapi.org/v2/top-headlines";
    private static final String COUNTRY ="country";
    private static final String API_KEY  ="apiKey";
    private static final String countryValue ="us";
    private static final String api_keyValue ="2331f4053d7647e8a3c595f6c0a49db0";

    public static URL buildURL(){

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(COUNTRY, countryValue)
                .appendQueryParameter(API_KEY, api_keyValue)
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
            Log.d("NetworkUtils: URL: " , url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }

    public static String getResponceFromURl(URL url) throws IOException{

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();

        assert response.body() != null;
        return response.body().string();
    }
}
