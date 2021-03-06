package com.tickettravel.grupo2.tickettravel.data;

import android.util.Log;

import com.tickettravel.grupo2.tickettravel.auxiliar.Constants;
import com.tickettravel.grupo2.tickettravel.data.services.TypeTicketService;
import com.tickettravel.grupo2.tickettravel.model.TypeTicket;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiTypeTicket {
    private static RestApiTypeTicket restApi;

    private RestApiTypeTicket()
    {
        //Prevent initialization from the reflection api.
        if (restApi != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public static synchronized RestApiTypeTicket getInstance()
    {
        if(restApi == null)
            restApi = new RestApiTypeTicket();

        return restApi;
    }

    public ArrayList<TypeTicket> getTypeTickets()
    {
        ArrayList<TypeTicket> tickets = new ArrayList<>();
        try
        {
            Retrofit retrofit = buildRetrofit();
            TypeTicketService typeTicketService = retrofit.create(TypeTicketService.class);
            Call<ArrayList<TypeTicket>> ticketsCall = typeTicketService.getTypeTickets();
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
                baseUrl(Constants.WEB_API_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
}
