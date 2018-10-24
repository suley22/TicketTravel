package com.tickettravel.grupo2.tickettravel.data;
import com.tickettravel.grupo2.tickettravel.model.Ticket;
import com.tickettravel.grupo2.tickettravel.model.Travel;
import com.tickettravel.grupo2.tickettravel.model.TypeCurrency;
import com.tickettravel.grupo2.tickettravel.model.TypeTicket;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface ApiService
{
    @HTTP(method = "GET", path = "TypeTicket/GetAllType")
    Call<ArrayList<TypeTicket>> getTypeTickets();

    @HTTP(method = "GET", path = "TypeCurrency/GetAllCurrency")
    Call<ArrayList<TypeCurrency>> getTypeCurrency();

    @HTTP(method = "POST", path = "User/validar",hasBody = true)
    Call<String>getUser(@Query("Usuario")String name, @Query("Contrasenas")String password);

    @HTTP(method = "GET", path = "Travel/GetAllTravel")
    Call<ArrayList<Travel>>getTravel();

    @HTTP(method = "GET", path = "Ticket/GetTicketConViajes")
    Call<ArrayList<Ticket>>getTicketbyTravel(@Query("idtravel")int idViaje);

    @HTTP(method = "POST", path = "Ticket/PostAllTicket", hasBody = true)
    Call<String>postTickets(@Body ArrayList<Ticket> tickets);

}
