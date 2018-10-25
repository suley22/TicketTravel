package com.tickettravel.grupo2.tickettravel.data;
import android.util.Log;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tickettravel.grupo2.tickettravel.auxiliar.Constants;
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

    public ArrayList<Ticket> postTickets(ArrayList<Ticket> tickets)
    {
        //TODO despues de usar este metodo borrar los ticket que vienen por parametro desde el activity
        ArrayList<Ticket> processedTickets = new ArrayList<Ticket>();
        if(tickets != null)
        {
            try
            {
                Map resul;
                for (int i = 0; i < tickets.size() ; i++) {
                    Cloudinary cloudinary = new Cloudinary(Constants.CLOUDINARY_API_URL);
                    Ticket ticket = tickets.get(i);
                    resul = cloudinary.uploader().upload(ticket.getImageUrl(), ObjectUtils.asMap("public_id",ticket.getId().toString()));
                    ticket.setImageUrl(resul.get("url").toString());
                    processedTickets.add(ticket);
                }

                Retrofit retrofit = buildRetrofit();
                TicketService ticketService = retrofit.create(TicketService.class);
                Call<String> ticketsCall = ticketService.postTickets(tickets);
                String result = ticketsCall.execute().body();

                if(result.equalsIgnoreCase("fail")){
                    processedTickets = null;
                }
            }
            catch(Exception e)
            {
                Log.d("estoy en catch", e.toString());
                processedTickets = null;
            }
        }
        else
        {
            processedTickets = null;
        }
        return processedTickets;
    }

    private Retrofit buildRetrofit()
    {
        return new Retrofit.Builder().
                baseUrl(Constants.WEB_API_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
}
