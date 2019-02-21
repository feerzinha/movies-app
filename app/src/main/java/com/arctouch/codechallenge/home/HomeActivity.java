package com.arctouch.codechallenge.home;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.detail.DetailsActivity;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.model.MovieViewModel;

import static com.arctouch.codechallenge.detail.DetailsActivity.MOVIE_EXTRA;

public class HomeActivity extends AppCompatActivity implements HomeAdapter.ItemClickListener {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        final HomeAdapter adapter = new HomeAdapter(this, this);
        movieViewModel.moviePagedList.observe(this, items -> {
            adapter.submitList(items);
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MOVIE_EXTRA, movie);
        startActivity(intent);
    }
}
