package com.arctouch.codechallenge.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.arctouch.codechallenge.data.MovieDataSource;
import com.arctouch.codechallenge.data.MovieDataSourceFactory;

public class MovieViewModel extends ViewModel {
    public LiveData<PagedList<Movie>> moviePagedList;
    public LiveData<PageKeyedDataSource<Integer, Movie>> liveDataSource;

    public MovieViewModel() {

        MovieDataSourceFactory itemDataSourceFactory = new MovieDataSourceFactory();
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(MovieDataSource.PAGE_SIZE)
                        .build();

        moviePagedList = (new LivePagedListBuilder(itemDataSourceFactory, config)).build();
    }
}
