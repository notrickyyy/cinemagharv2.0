package com.rikesh.cinemaghar.View;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.rikesh.cinemaghar.R;
import com.rikesh.cinemaghar.utils.Constants;
import com.rikesh.cinemaghar.utils.Funct;

import java.util.Objects;

public class MovieDetails extends AppCompatActivity {
    public ImageView bigImg, smallImg;
    public TextView title, description, release_date, average_vote;

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
        String title = null, description = null, image = null, backDrop_image = null, release_date = null;
        double average_vote = -1;
        if (extras != null) {
            title = extras.getString("title");
            description = extras.getString("description");
            image = extras.getString("image");
            backDrop_image = extras.getString("backDrop_image");
            release_date = extras.getString("release_date");
            average_vote = extras.getDouble("average_vote");
        }

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
