package com.arctouch.codechallenge.data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.arctouch.codechallenge.model.Movie;

public class MovieDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Movie>> itemLiveDataSource = new MutableLiveData<>();


    @Override
    public DataSource create() {
        MovieDataSource itemDataSource = new MovieDataSource();
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Movie>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}