package com.example.sanoyan.googlemaps.request;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.sanoyan.googlemaps.bdd.DBAdapter;
import com.example.sanoyan.googlemaps.bdd.DatabaseHelper;
import com.example.sanoyan.googlemaps.bdd.Position;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Lionel on 05/03/2016.
 */
public class RetrofitAdapter {

    DBAdapter db;

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://localhost:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private TwwService service = retrofit.create(TwwService.class);

    public RetrofitAdapter(){

    }

    public void listDesPositions(final Context context){
        final Call<List<Position>> Positions = service.listPosition();
        Positions.enqueue(new Callback<List<Position>>() {
            @Override
            public void onResponse(Response<List<Position>> response, Retrofit retrofit) {
                List<Position> allPosition = response.body();
                db = new DBAdapter(context);
                for(int i = 0; i<allPosition.size(); i++) {
                    Position position = new Position(allPosition.get(i).getLatitude(), allPosition.get(i).getLongitude());
                    db.creerUnePosition(position);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(context, String.format("KO"), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
