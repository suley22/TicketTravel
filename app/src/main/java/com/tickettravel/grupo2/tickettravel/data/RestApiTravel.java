package com.tickettravel.grupo2.tickettravel.data;

import android.util.Log;

import com.tickettravel.grupo2.tickettravel.model.Travel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiTravel {
    private static RestApiTravel restApi;

    private RestApiTravel()
    {
        //Prevent initialization from the reflection api.
        if (restApi != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public static synchronized RestApiTravel getInstance()
    {
        if(restApi == null)
            restApi = new RestApiTravel();

        return restApi;
    }

    public ArrayList<Travel> getTravels()
    {
        ArrayList<Travel> travels = new ArrayList<>();
        try
        {
            Retrofit retrofit = buildRetrofit();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<ArrayList<Travel>> ticketsCall = apiService.getTravel();
            travels = ticketsCall.execute().body();
        }
        catch(Exception e)
        {
            Log.d("estoy en catch", e.toString());
        }
        return travels;
    }

    private Retrofit buildRetrofit()
    {
        return new Retrofit.Builder().
                baseUrl("http://www.promon.net.ar/Test/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
}
