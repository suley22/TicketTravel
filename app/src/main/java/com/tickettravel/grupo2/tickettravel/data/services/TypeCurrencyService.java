package com.tickettravel.grupo2.tickettravel.data.services;
import com.tickettravel.grupo2.tickettravel.model.TypeCurrency;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.HTTP;

public interface TypeCurrencyService
{
    @HTTP(method = "GET", path = "TypeCurrency/GetAllCurrency")
    Call<ArrayList<TypeCurrency>> getTypeCurrency();
}
