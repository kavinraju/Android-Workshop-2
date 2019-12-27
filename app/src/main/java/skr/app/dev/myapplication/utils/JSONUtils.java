package skr.app.dev.myapplication.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    private static final String TAG = JSONUtils.class.getSimpleName();

    public static ArrayList<NewsPojo> getNewsArrayList(JSONObject jsonObjectMain) {

        ArrayList<NewsPojo> newsPojos = new ArrayList<>();

        Log.d(TAG, jsonObjectMain.toString());

        try {

            JSONArray jsonArrayArticles = jsonObjectMain.getJSONArray("articles");

            Log.d(TAG, jsonArrayArticles.toString());

            for (int i = 0; i < jsonArrayArticles.length(); i++) {
                JSONObject news = jsonArrayArticles.getJSONObject(i);
                NewsPojo newsPojo = new NewsPojo();
                newsPojo.setAuthor(news.getString("author"));
                newsPojo.setContent(news.getString("content"));
                newsPojo.setDescription(news.getString("description"));
                newsPojo.setPublishedAt(news.getString("publishedAt"));
                newsPojo.setTitle(news.getString("title"));
                newsPojo.setUrl(news.getString("url"));
                newsPojo.setUrlToImage(news.getString("urlToImage"));
                newsPojos.add(newsPojo);
            }

            return newsPojos;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
