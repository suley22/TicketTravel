package com.tickettravel.grupo2.tickettravel.data.services;
import com.tickettravel.grupo2.tickettravel.model.Ticket;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface TicketService
{
    @HTTP(method = "GET", path = "Ticket/GetTicketConViajes")
    Call<ArrayList<Ticket>>getTicketbyTravel(@Query("idtravel") int idViaje);

    @HTTP(method = "POST", path = "Ticket/PostAllTicket", hasBody = true)
    Call<String>postTickets(@Body ArrayList<Ticket> tickets);
}
