package com.rikesh.cinemaghar.View;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.rikesh.cinemaghar.R;
import com.rikesh.cinemaghar.utils.Constants;
import com.rikesh.cinemaghar.utils.Funct;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class MovieDetails extends AppCompatActivity {
    public ImageView bigImg, smallImg;
    public TextView title, description, release_date, average_vote;
    private YouTubePlayerView youTubePlayerView;
    private String videoId;

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Funct.isNetworkConnected(this)) {
            setContentView(R.layout.no_internet);
            LinearLayout try_again = findViewById(R.id.try_again);

            try_again.setOnClickListener(v -> Funct.RefreshActivity(this));
        }
        else
            setContentView(R.layout.movie_details);



        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        Bundle extras = getIntent().getExtras();
        String title = null,videoId = null, description = null, image = null, backDrop_image = null, release_date = null;
        double average_vote = -1;
        if (extras != null) {
            title = extras.getString("title");
            description = extras.getString("description");
            image = extras.getString("image");
            backDrop_image = extras.getString("backDrop_image");
            release_date = extras.getString("release_date");
            average_vote = extras.getDouble("average_vote");
            videoId = extras.getString("videoId");
        }

        HashMap<String, String> namesAndNumbers = new HashMap<>();
        namesAndNumbers.put("3GJMp1y57YA","2022-05-23");
        namesAndNumbers.put("KdvtXi1fBsA","2019-11-23");
        namesAndNumbers.put("75zgt7e1Y4g","2016-11-23");
        namesAndNumbers.put("O2L8kqUdZ58","2017-11-23");
        namesAndNumbers.put("dAJRnbZhccI","2017-02-23");
        namesAndNumbers.put("O3GRxW9IkTg","2016-05-23");
        namesAndNumbers.put("QUsgST4Zj5Y","2012-11-23");
        namesAndNumbers.put("1dgGWN8-7Fc","2018-03-23");
        namesAndNumbers.put("Q2BIRMlcZDs","2015-04-11");
        namesAndNumbers.put("7e7Bbwf4uOc","2022-11-23");
        namesAndNumbers.put("nX2fcXN_Yks","2017-02-22");
        namesAndNumbers.put("YroXZEErED8","2022-11-13");
        namesAndNumbers.put("JLV26lOElSY","2022-05-24");
        namesAndNumbers.put("Hpgp6I-4WJc","2016-05-24");
        namesAndNumbers.put("8_N1zFPvPkk","2016-11-24");
        namesAndNumbers.put("p8_YI_dNImM","2019-11-24");
        namesAndNumbers.put("4tmQgsctGp4","2018-03-24");
        namesAndNumbers.put("xHbyrAIPn3Y","2017-11-14");
        namesAndNumbers.put("WrHHGiPRTu4","2017-02-16");
        namesAndNumbers.put("oRjjJ5LkGPY","2012-11-27");
        namesAndNumbers.put("H63q3Blz3-c","2015-05-21");
        namesAndNumbers.put("wFO1lJ5qEAU","2022-10-29");
        namesAndNumbers.put("7uVU6QAvgG8","2017-02-21");
        namesAndNumbers.put("SSMUBhsxICo","2022-09-23");


        String numberFromApi = release_date;

        String FinalMovie = "";
        for (Map.Entry<String, String> entry : namesAndNumbers.entrySet()) {
            if (entry.getValue().equals(numberFromApi)) {
                FinalMovie = entry.getKey();
                break;
            }
        }

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        String finalVideoId = FinalMovie;
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);

                youTubePlayer.loadVideo(finalVideoId, 0); // play video with dynamic videoId
            }
        });
        bigImg = findViewById(R.id.activity_detail_backdrop_image);
        smallImg = findViewById(R.id.activity_detail_poster_image);
        this.title = findViewById(R.id.activity_detail_movie_title);

        this.description = findViewById(R.id.activity_detail_overview_text);
        this.release_date = findViewById(R.id.activity_detail_movie_date);
        this.average_vote = findViewById(R.id.activity_item_movie_avg_vote);

        this.title.setText(title);
        this.description.setText(description);
        this.release_date.setText(release_date);
        this.average_vote.setText(String.valueOf(average_vote));

        Funct.setImage(this, bigImg, Constants.IMAGE_BASE_URL + backDrop_image);
        Funct.setImage(this, smallImg, Constants.IMAGE_BASE_URL + image);
    }

}
