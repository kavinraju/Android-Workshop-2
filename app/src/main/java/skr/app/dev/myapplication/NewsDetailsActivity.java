package skr.app.dev.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsDetailsActivity extends AppCompatActivity {

    public static String INTENT_IMAGE_URL_KEY = "image_url";
    public static String INTENT_DESCRIPTION_URL_KEY = "description";
    public static String INTENT_TITLE_URL_KEY = "title";

    private String image_url = "", description = "", title = "";

    private ImageView imageViewNewsBk;
    private TextView textViewDescription, textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getDataFromIntent();

        // Get reference from UI
        imageViewNewsBk = findViewById(R.id.iv_tool_bar__news_bk);
        textViewDescription = findViewById(R.id.tv_desp);  // you can use same Id across various activities
        textViewTitle = findViewById(R.id.tv_title);

        setUI();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setUI() {
        Glide.with(this)
                .load(image_url)
                .into(imageViewNewsBk);

        textViewDescription.setText(description);

        if (title.equals(""))
            textViewTitle.setText("No Title available");
        else
            textViewTitle.setText(title);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_IMAGE_URL_KEY))
            image_url = intent.getStringExtra(INTENT_IMAGE_URL_KEY);

        if (intent.hasExtra(INTENT_DESCRIPTION_URL_KEY))
            description = intent.getStringExtra(INTENT_DESCRIPTION_URL_KEY);

        if (intent.hasExtra(INTENT_TITLE_URL_KEY))
            title = intent.getStringExtra(INTENT_TITLE_URL_KEY);
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }


}
