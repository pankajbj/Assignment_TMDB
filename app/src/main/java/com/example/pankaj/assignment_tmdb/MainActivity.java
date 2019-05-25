package com.example.pankaj.assignment_tmdb;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static String BASE_URL="https://api.themoviedb.org/";
    public static int PAGE=1;
    public static String LANGUAGE="en-US";
    public static String CATEGORY="popular";
    public static String API_KEY="955c8a14117387021399e34318ab8b5c";

    List<MovieResults.Result>listofMovies=new ArrayList<>();
    Retrofit retrofit;
    RecyclerView recyclerView;
    MyAdapter adapter;

    MovieViewModel movieViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);

        //https://api.themoviedb.org/3/movie/popular?api_key=955c8a14117387021399e34318ab8b5c&language=en-US&page=1
        movieViewModel=ViewModelProviders.of(this).get(MovieViewModel.class);

        retrofit  =new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface myinterface=retrofit.create(ApiInterface.class);
        Call<MovieResults> call=myinterface.getMovies(CATEGORY,API_KEY,LANGUAGE,PAGE);
        try {
            call.enqueue(new Callback<MovieResults>() {
                @Override
                public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                    MovieResults results = response.body();
                    listofMovies = results.getResults();

                    adapter=new MyAdapter(MainActivity.this,listofMovies);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<MovieResults> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }catch (Exception e){}
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opt_menu, menu);
      MenuItem menuItem = menu.findItem(R.id.rating);
              menuItem=menu.findItem(R.id.date);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.rating){
            Collections.sort(listofMovies, new Comparator<MovieResults.Result>() {
                public int compare(MovieResults.Result m1, MovieResults.Result m2) {
                    return m1.getPopularity().compareTo(m2.getPopularity());
                }
            });
            adapter=new MyAdapter(MainActivity.this,listofMovies);

            recyclerView.setAdapter(adapter);
            LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(manager);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

            Toast.makeText(getApplicationContext(),"list will be sorted based on Rating",Toast.LENGTH_SHORT).show();

        }
        else if (item.getItemId()==R.id.date){
            Collections.sort(listofMovies, new Comparator<MovieResults.Result>() {
                public int compare(MovieResults.Result m1, MovieResults.Result m2) {
                    return m1.getReleaseDate().compareTo(m2.getReleaseDate());
                }
            });
            adapter=new MyAdapter(MainActivity.this,listofMovies);

            recyclerView.setAdapter(adapter);
            LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext());
            manager.setReverseLayout(true);
            manager.setStackFromEnd(true);
            recyclerView.setLayoutManager(manager);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));

            Log.i("tag","ttt"+listofMovies.toString());
           Toast.makeText(getApplicationContext(),"List will be sorted based on Date of release",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
