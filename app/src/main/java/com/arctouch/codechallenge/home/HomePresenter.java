package com.arctouch.codechallenge.home;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import com.arctouch.codechallenge.model.MovieViewModel;

public class HomePresenter {

    private final HomeView view;
    private Activity activity;

    public HomePresenter(HomeView view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }

    public void getMovies() {
        view.showLoading();
        MovieViewModel movieViewModel = ViewModelProviders.of((FragmentActivity) activity).get(MovieViewModel.class);
        movieViewModel.moviePagedList.observe((LifecycleOwner) activity, items -> {
            view.showMovies(items);
            view.hideLoading();
        });
    }
}
