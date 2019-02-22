package com.arctouch.codechallenge.home;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import com.arctouch.codechallenge.data.Cache;
import com.arctouch.codechallenge.model.MovieViewModel;
import com.arctouch.codechallenge.network.RetrofitClient;
import com.arctouch.codechallenge.network.TmdbApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter {

    private final HomeView view;
    private Activity activity;

    public HomePresenter(HomeView view, Activity activity) {
        this.view = view;
        this.activity = activity;
    }

    public void getMovies() {
        loadCacheGenres();
    }

    private void loadCacheGenres() {
        RetrofitClient.getInstance()
                .getTmdbApi().genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Cache.setGenres(response.genres);

                    requestMovies();
                });
    }

    private void requestMovies() {
        MovieViewModel movieViewModel = ViewModelProviders.of((FragmentActivity) activity).get(MovieViewModel.class);
        movieViewModel.moviePagedList.observe((LifecycleOwner) activity, items -> {
            view.showMovies(items);
            view.hideLoading();
        });
    }
}
