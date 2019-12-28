package skr.app.dev.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

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


    // Notification
    private static final int NOTIFICATION_ID_0 = 0;
    private static final String PRIMARY_CHANNEL_ID_0 = "primary_notification_channel_zero";
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createNotificationChannel();
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

                sendNotification();

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

    //Helper Methods
    public void createNotificationChannel(){
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Channel 0
            NotificationChannel channel_0 = new NotificationChannel(
                    PRIMARY_CHANNEL_ID_0,
                    "Channel 0",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel_0.enableLights(true);
            channel_0.setLightColor(Color.GREEN);
            channel_0.enableVibration(true);
            channel_0.setDescription("Hello! This is the description of Notification Channel 0");
            mNotificationManager.createNotificationChannel(channel_0);
        }

    }

    public void sendNotification(){
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_0,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID_0)
                .setContentTitle("Today's news!")
                .setContentText(title)
                .setColor(Color.GREEN)
                .setSmallIcon(R.drawable.ic_speaker_notes_black_24dp)
                .setContentIntent(pendingIntent)
                .setNumber(5)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);
        mNotificationManager.notify(NOTIFICATION_ID_0, builder.build());
    }



}
