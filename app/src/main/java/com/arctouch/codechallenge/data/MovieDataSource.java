package com.arctouch.codechallenge.data;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.arctouch.codechallenge.model.Genre;
import com.arctouch.codechallenge.model.Movie;
import com.arctouch.codechallenge.network.TmdbApi;
import com.arctouch.codechallenge.network.RetrofitClient;

import java.util.ArrayList;

import io.reactivex.schedulers.Schedulers;

public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {

    public static final int PAGE_SIZE = 20;
    private static final int FIRST_PAGE = 1;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        RetrofitClient.getInstance()
                .getTmdbApi().upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    if (response != null){
                        for (Movie movie : response.results) {
                            movie.genres = new ArrayList<>();
                            for (Genre genre : Cache.getGenres()) {
                                if (movie.genreIds.contains(genre.id)) {
                                    movie.genres.add(genre);
                                }
                            }
                        }

                        callback.onResult(response.results, null, FIRST_PAGE + 1);
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        RetrofitClient.getInstance()
                .getTmdbApi().upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    if (response != null){
                        for (Movie movie : response.results) {
                            movie.genres = new ArrayList<>();
                            for (Genre genre : Cache.getGenres()) {
                                if (movie.genreIds.contains(genre.id)) {
                                    movie.genres.add(genre);
                                }
                            }
                        }

                        Integer key = (params.key > 1) ? params.key - 1 : null;
                        callback.onResult(response.results, key);
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        RetrofitClient.getInstance()
                .getTmdbApi().upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(response -> {
                    if (response != null){
                        for (Movie movie : response.results) {
                            movie.genres = new ArrayList<>();
                            for (Genre genre : Cache.getGenres()) {
                                if (movie.genreIds.contains(genre.id)) {
                                    movie.genres.add(genre);
                                }
                            }
                        }

                        //Check if has more pages to load
                        Integer key = params.key < response.totalPages ? params.key + 1 : null;
                        callback.onResult(response.results, key);
                    }

                });
    }
}
