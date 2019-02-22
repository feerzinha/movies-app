package com.arctouch.codechallenge.home;

import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.arctouch.codechallenge.R;
import com.arctouch.codechallenge.details.DetailsActivity;
import com.arctouch.codechallenge.model.Movie;

import static com.arctouch.codechallenge.details.DetailsActivity.MOVIE_EXTRA;


public class HomeActivity extends AppCompatActivity implements HomeAdapter.ItemClickListener, HomeView {

    private RecyclerView recyclerView;
    private HomeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new HomeAdapter(this, this);
        recyclerView.setAdapter(adapter);

        HomePresenter presenter = new HomePresenter(this,this);
        presenter.getMovies();
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(MOVIE_EXTRA, movie);
        startActivity(intent);
    }

    @Override
    public void showMovies(PagedList<Movie> items) {

        adapter.submitList(items);
    }
}
