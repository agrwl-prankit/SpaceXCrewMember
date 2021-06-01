package com.prankit.spacexcrewmember;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import com.prankit.spacexcrewmember.model.CrewMemberModel;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public String BASE_URL = "https://api.spacexdata.com/v4/";
    private List<CrewMemberModel> crewList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SpaceX Crew Members");

        recyclerView = findViewById(R.id.crew_recycle_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isNetworkConnected()) getCrewPostOnline();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void getCrewPostOnline(){
        ((Api) new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(Api.class))
                .crew().enqueue(new Callback<List<CrewMemberModel>>() {
            @Override
            public void onResponse(Call<List<CrewMemberModel>> call, Response<List<CrewMemberModel>> response) {
                //crewList.clear();
                if (response.isSuccessful()) {
                    adapter = new CrewMemberAdapter(MainActivity.this, response.body());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(layoutManager);
                } else {
                    Log.i("crew error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<CrewMemberModel>> call, Throwable t) {
                Log.i("crew fail", t.getMessage() + "");
            }
        });
    }
}