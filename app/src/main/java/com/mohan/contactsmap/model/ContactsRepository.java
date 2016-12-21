package com.mohan.contactsmap.model;

/**
 * Created by mohan on 20/12/16.
 */

public class ContactsRepository implements MovieDataSource {

    private static final String TAG = "ContactsRepository";

    /*@Override
    public void getMovies(@NonNull LoadMoviesCallback callback) {
        loadMoviesFromNetwork(callback);
    }

    @Override
    public void getMovie(@NonNull String taskId, @NonNull GetMovieCallback callback) {

    }

    public void loadMoviesFromNetwork(@NonNull final LoadMoviesCallback callback) {
        Retrofit retrofit = RestClient.getClient();
        String apiToken = "66731d2e5d5e953395193ec20af94cac";  // replace with yor api key of moviesdb

        final NetworkCall networkCall = retrofit.create(NetworkCall.class);

        networkCall.getMovies(apiToken).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    List<Movie> movieList = response.body().getResults();
                    if (movieList.isEmpty()) {
                        callback.onDataNotAvailable();
                    } else {
                        callback.onMoviesLoaded(movieList);
                    }
                } else {
                    try {
                        callback.onNetworkError(response.errorBody().string());
                    } catch (IOException e) {
                        callback.onNetworkError("Unknown error");
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

                callback.onNetworkError(t.getMessage());
                t.printStackTrace();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }*/
}
