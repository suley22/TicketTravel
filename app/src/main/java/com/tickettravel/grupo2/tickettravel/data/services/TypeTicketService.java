package com.tickettravel.grupo2.tickettravel.data.services;
import com.tickettravel.grupo2.tickettravel.model.TypeTicket;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.HTTP;

public interface TypeTicketService
{
    @HTTP(method = "GET", path = "TypeTicket/GetAllType")
    Call<ArrayList<TypeTicket>> getTypeTickets();
}
