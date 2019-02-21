package com.arctouch.codechallenge.detail;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.util.ColorUtils;
import com.arctouch.codechallenge.util.GlideUtils;
import com.arctouch.codechallenge.util.MovieImageUrlBuilder;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class DetailsActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "movie";

    private final MovieImageUrlBuilder movieImageUrlBuilder = new MovieImageUrlBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Movie movie = (Movie) extras.getSerializable(MOVIE_EXTRA);

            if (movie != null) {
                setTitle(movie.title);
                setDetailsUI(movie);
            }
        }
    }

    private void setDetailsUI(Movie movie) {
        //Set Movie Name
        TextView movieNameTextView = findViewById(R.id.nameTextView);
        movieNameTextView.setText(movie.title);

        //Set Movie Overview
        TextView overviewTextView = findViewById(R.id.overviewTextView);
        overviewTextView.setText(movie.overview);

        //Set Movie Genres
        if (!movie.genres.isEmpty()) {
            TextView genreTextView = findViewById(R.id.genresTextView);
            genreTextView.setText(getString(R.string.genres_text, TextUtils.join(", ", movie.genres)));
            genreTextView.setVisibility(View.VISIBLE);
        }

        //Set Movie Release Date
        if (!TextUtils.isEmpty(movie.releaseDate)) {
            TextView releaseDateTextView = findViewById(R.id.releaseDateTextView);
            releaseDateTextView.setText(getString(R.string.release_date_text, movie.releaseDate));
            releaseDateTextView.setVisibility(View.VISIBLE);
        }

        //Set Poster image
        String posterPath = movie.posterPath;
        if (!TextUtils.isEmpty(posterPath)) {
            ImageView posterImageView = findViewById(R.id.posterImageView);
            GlideUtils.requestGlideWithListener(this, movieImageUrlBuilder.buildPosterUrl(posterPath), posterImageView, RequestOptions.bitmapTransform(new RoundedCorners(5)), resource -> {
                if (resource != null) {
                    setThemeColor(Palette.from(resource).generate());
                }
            });
        }

        //Set Backdrop image
        String backdropPath = movie.backdropPath;
        if (!TextUtils.isEmpty(backdropPath)) {
            ImageView backdropImageView = findViewById(R.id.backdropImageView);
            GlideUtils.requestGlide(this, movieImageUrlBuilder.buildBackdropUrl(backdropPath),
                    backdropImageView, RequestOptions.bitmapTransform(new RoundedCorners(5)));
        }
    }

    private void setThemeColor(Palette palette) {
        int backgroundLightColor = palette.getLightMutedColor(getResources().getColor(android.R.color.white));

        View containerLayout = findViewById(R.id.details_card);
        containerLayout.setBackgroundColor(ColorUtils.manipulateColor(backgroundLightColor, 0.92f));

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //set action bar color
            actionBar.setBackgroundDrawable(new ColorDrawable(ColorUtils.manipulateColor(backgroundLightColor, 0.62f)));

            //set status bar color
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ColorUtils.manipulateColor(backgroundLightColor, 0.32f));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

