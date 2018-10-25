package com.tickettravel.grupo2.tickettravel.data;
import android.util.Log;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tickettravel.grupo2.tickettravel.data.services.TicketService;
import com.tickettravel.grupo2.tickettravel.model.Ticket;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiTicket
{
    private static RestApiTicket restApi;

    private RestApiTicket()
    {
        //Prevent initialization from the reflection api.
        if (restApi != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public static synchronized RestApiTicket getInstance()
    {
        if(restApi == null)
            restApi = new RestApiTicket();

        return restApi;
    }

    public ArrayList<Ticket> getTicketbyTravel(int IdViaje)
    {
        ArrayList<Ticket> tickets = new ArrayList<>();
        try
        {
            Retrofit retrofit = buildRetrofit();
            TicketService ticketService = retrofit.create(TicketService.class);
            Call<ArrayList<Ticket>> ticketsCall = ticketService.getTicketbyTravel(IdViaje);
            tickets = ticketsCall.execute().body();
        }
        catch(Exception e)
        {
            Log.d("estoy en catch", e.toString());
        }
        return tickets;
    }

    public String postTickets(ArrayList<Ticket> tickets)
    {
        String result;
        if(tickets!=null)
        {try
        {
            Map resul;
            for (int i = 0; i <tickets.size() ; i++) {
                Cloudinary cloudinary = new Cloudinary("cloudinary://761652156746733:n14HmdjezX2mUy-0cJjGWlmoyBs@dtcmp4y9n");
                String url=tickets.get(i).getImageUrl();
                String id=tickets.get(i).getId().toString();
                resul=cloudinary.uploader().upload(url, ObjectUtils.asMap("public_id",id));
                 tickets.get(i).setImageUrl(resul.get("url").toString());
            }
            Retrofit retrofit = buildRetrofit();
            TicketService ticketService = retrofit.create(TicketService.class);
            Call<String> ticketsCall = ticketService.postTickets(tickets);
            result = ticketsCall.execute().body();
        }
        catch(Exception e)
        {
            Log.d("estoy en catch", e.toString());
            result="false";
        }}
        else
        {result = null;}
        return result;
    }

    private Retrofit buildRetrofit()
    {
        return new Retrofit.Builder().
                baseUrl("http://www.promon.net.ar/Test/").//TODO mover a constante
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
}
