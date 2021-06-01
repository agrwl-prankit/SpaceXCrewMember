package com.prankit.spacexcrewmember;

import com.prankit.spacexcrewmember.model.CrewMemberModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("crew")
    Call<List<CrewMemberModel>> crew();
}
