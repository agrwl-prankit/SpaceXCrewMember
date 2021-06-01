package com.prankit.spacexcrewmember;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.prankit.spacexcrewmember.adapter.CrewMemberAdapter;
import com.prankit.spacexcrewmember.model.CrewMemberModel;
import com.prankit.spacexcrewmember.room.CrewMemberRoomModel;
import com.prankit.spacexcrewmember.room.CrewMemberDb;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public String BASE_URL = "https://api.spacexdata.com/v4/";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private CrewMemberDb db;

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

        db = Room.databaseBuilder(getApplicationContext(), CrewMemberDb.class, "crewMemberDb").allowMainThreadQueries().build();

        convertOnlineToOffline();
    }

    private void convertOnlineToOffline() {
        GetUsersAsyncTask usersAsyncTask = new GetUsersAsyncTask();
        List<CrewMemberRoomModel> list = usersAsyncTask.doInBackground();
        if (list.size() == 4 && isNetworkConnected()){
            ((Api) new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(Api.class))
                    .crew().enqueue(new Callback<List<CrewMemberModel>>() {
                @Override
                public void onResponse(Call<List<CrewMemberModel>> call, Response<List<CrewMemberModel>> response) {
                    if (response.isSuccessful()) {
                        List<CrewMemberModel> list1 = response.body();
                        for (int pos=0; pos<list1.size(); pos++){
                            CrewMemberRoomModel member = new CrewMemberRoomModel(list1.get(pos).getName(),list1.get(pos).getAgency(),list1.get(pos).getStatus(),
                                    list1.get(pos).getWikipedia(),list1.get(pos).getImage());
                            db.crewMemberDAO().insertCrew(member);
                        }
                    } else {
                        Log.i("crewerror", response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<List<CrewMemberModel>> call, Throwable t) {
                    Log.i("crewfail", t.getMessage() + "");
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isNetworkConnected()) getCrewMemberOnline();
        else getCrewMemberOffline();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void getCrewMemberOnline() {
        ((Api) new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(Api.class))
                .crew().enqueue(new Callback<List<CrewMemberModel>>() {
            @Override
            public void onResponse(Call<List<CrewMemberModel>> call, Response<List<CrewMemberModel>> response) {
                if (response.isSuccessful()) {
                    adapter = new CrewMemberAdapter(MainActivity.this, response.body(),null, 1);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(layoutManager);
                } else {
                    Log.i("crewerror", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<CrewMemberModel>> call, Throwable t) {
                Log.i("crewfail", t.getMessage() + "");
            }
        });
    }


    private class GetUsersAsyncTask extends AsyncTask<Void, Void,List<CrewMemberRoomModel>>
    {
        @Override
        protected List<CrewMemberRoomModel> doInBackground(Void... voids) {
            return db.crewMemberDAO().getAllMember();
        }
    }

    public void getCrewMemberOffline() {
        GetUsersAsyncTask usersAsyncTask = new GetUsersAsyncTask();
        List<CrewMemberRoomModel> list = usersAsyncTask.doInBackground();
        if (list.size() == 0)
            Toast.makeText(this, "No offline member found", Toast.LENGTH_SHORT).show();
        else {
            for (int pos = 0; pos < list.size(); pos++) {
                adapter = new CrewMemberAdapter(MainActivity.this,null, list, 2);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        }
    }
}