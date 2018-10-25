package com.tickettravel.grupo2.tickettravel.data;

import android.util.Log;

import com.tickettravel.grupo2.tickettravel.data.services.TypeCurrencyService;
import com.tickettravel.grupo2.tickettravel.model.TypeCurrency;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiTypeCurrency {
    private static RestApiTypeCurrency restApi;

    private RestApiTypeCurrency()
    {
        //Prevent initialization from the reflection api.
        if (restApi != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public static synchronized RestApiTypeCurrency getInstance()
    {
        if(restApi == null)
            restApi = new RestApiTypeCurrency();

        return restApi;
    }

    public ArrayList<TypeCurrency> getTypeCurrency()
    {
        ArrayList<TypeCurrency> tickets = new ArrayList<>();
        try
        {
            Retrofit retrofit = buildRetrofit();
            TypeCurrencyService typeCurrencyService = retrofit.create(TypeCurrencyService.class);
            Call<ArrayList<TypeCurrency>> ticketsCall = typeCurrencyService.getTypeCurrency();
            tickets = ticketsCall.execute().body();
        }
        catch(Exception e)
        {
            Log.d("estoy en catch", "getTickets: ");
        }
        return tickets;
    }

    private Retrofit buildRetrofit()
    {
        return new Retrofit.Builder().
                baseUrl("http://www.promon.net.ar/Test/").//TODO mover a constante
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
}
