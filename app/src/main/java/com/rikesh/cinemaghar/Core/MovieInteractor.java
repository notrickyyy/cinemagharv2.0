package com.rikesh.cinemaghar.Core;

import android.content.Context;
import android.os.AsyncTask;

import com.rikesh.cinemaghar.Model.Movie;
import com.rikesh.cinemaghar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MovieInteractor implements MovieContract.Interactor {
    private MovieContract.onOperationListener mListener;
    private ArrayList<Movie> movies = new ArrayList<>();
    private String base_url;
    private Context context;
    private String query;
    private boolean search;

    public MovieInteractor(MovieContract.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performReadMovies(Context context, String base_url, int pages) {
        this.context = context;
        this.base_url = base_url;
        search = false;
        new GetData(pages).execute();
    }

    @Override
    public void performSearchMovies(Context context, String base_url, int pages, String query) {
        this.context = context;
        this.base_url = base_url;
        this.query = query;
        search = true;
        new GetData(pages).execute();
    }

    private class GetData extends AsyncTask<String, String, String> {
        int page;

        public GetData(int page) {
            this.page = page;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mListener.onStart();

        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);

            try {
                JSONObject jsonObject = new JSONObject(string);

                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i=0; i< jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    Movie movie = new Movie();
                    movie.setAverage_vote(jsonObject1.getDouble("vote_average"));
                    movie.setTitle(jsonObject1.getString("title"));
                    movie.setImage(jsonObject1.getString("poster_path"));
                    movie.setBackDrop_image(jsonObject1.getString("backdrop_path"));
                    movie.setRelease_date(jsonObject1.getString("release_date"));
                    movie.setDescription(jsonObject1.getString("overview"));

                    movies.add(movie);

                }

                mListener.onRead(movies);
                mListener.onEnd();

            } catch (JSONException e) {
                e.printStackTrace();
                mListener.onEnd();
            }

        }


        @Override
        protected String doInBackground(String... strings) {
            StringBuilder current = new StringBuilder();

            try {
                try {
                    URL url;
                    HttpURLConnection httpURLConnection = null;

                    try{
                        if (!search)
                            url = new URL(base_url + "&page=" + page + "&api_key=" + context.getString(R.string.API_KEY));
                        else
                            url = new URL(base_url + "&query=" + query + "&page=" + page + "&api_key=" + context.getString(R.string.API_KEY));
                        httpURLConnection = (HttpURLConnection) url.openConnection();
                        InputStream is = httpURLConnection.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);

                        int data = isr.read();

                        while (data != -1) {
                            current.append((char) data);
                            data = isr.read();
                        }

                        return current.toString();


                    }catch (MalformedURLException e){
                        e.printStackTrace();
                        mListener.onEnd();
                    }finally {
                        // this is done so that there are no open connections left when this task is going to complete
                        if (httpURLConnection != null)
                            httpURLConnection.disconnect();
                    }


                } catch (IOException e){
                    e.printStackTrace();
                    mListener.onEnd();
                }

            } catch (Exception e){
                e.printStackTrace();
                mListener.onEnd();
            }

            return current.toString();
        }
    }
}
