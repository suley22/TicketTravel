package com.tickettravel.grupo2.tickettravel.data.services;
import com.tickettravel.grupo2.tickettravel.model.Travel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.HTTP;

public interface TravelService
{
    @HTTP(method = "GET", path = "Travel/GetAllTravel")
    Call<ArrayList<Travel>>getTravel();
}
