package com.arctouch.codechallenge.home;

import android.arch.paging.PagedList;

import com.arctouch.codechallenge.model.Movie;

public interface HomeView {

    void showMovies(PagedList<Movie> items);

}
