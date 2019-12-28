package skr.app.dev.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.Transition;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import skr.app.dev.myapplication.adapter.NewsAdapter;
import skr.app.dev.myapplication.utils.JSONUtils;
import skr.app.dev.myapplication.utils.NetworkUtils;
import skr.app.dev.myapplication.utils.NewsPojo;

public class NewsActivity extends AppCompatActivity implements NewsAdapter.NewsTitleClickListener {


    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<NewsPojo> newsArrayList;

    public static String SHARED_ELEMENT_TRANSITION_EXTRA = "sharedElementTransition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("World News App");
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        final ContentLoadingProgressBar progressBar = findViewById(R.id.progress_bar);

        String url = NetworkUtils.buildURL().toString();

        // Create a network request to get the news
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {

                            newsArrayList = JSONUtils.getNewsArrayList(response);
                            newsAdapter = new NewsAdapter(getApplicationContext(), newsArrayList, newsArrayList.size(),NewsActivity.this);
                            recyclerView.setAdapter(newsAdapter);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        // Add the request to the RequestQueue.
        requestQueue.add(jsonObjectRequest);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                        newsAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());

                        return true;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                            newsAdapter.notifyItemRemoved(position);
                        }
                    }

                    @Override
                    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                            boolean isCurrentlyActive) {
                        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                            viewHolder.itemView.setAlpha(0.3f);
                        }

                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }

                });

        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNewsTitleClick(View view, int position) {
        Intent intent = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra(NewsDetailsActivity.INTENT_IMAGE_URL_KEY, newsArrayList.get(position).getUrlToImage());
        intent.putExtra(NewsDetailsActivity.INTENT_DESCRIPTION_URL_KEY, newsArrayList.get(position).getDescription());
         intent.putExtra(NewsDetailsActivity.INTENT_TITLE_URL_KEY,  newsArrayList.get(position).getTitle());

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                NewsActivity.this,
                view,
                SHARED_ELEMENT_TRANSITION_EXTRA);
        ActivityCompat.startActivity(NewsActivity.this, intent, optionsCompat.toBundle());
    }
}
