package com.example.sanoyan.googlemaps.request;

import com.example.sanoyan.googlemaps.bdd.Position;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Lionel on 05/03/2016.
 */
public interface TwwService {
    @GET("/tww_service/position/list")
    Call<List<Position>> listPosition();
}
