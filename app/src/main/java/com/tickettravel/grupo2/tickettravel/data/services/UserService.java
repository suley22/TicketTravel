package com.tickettravel.grupo2.tickettravel.data.services;
import retrofit2.Call;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface UserService
{
    @HTTP(method = "POST", path = "User/validar",hasBody = true)
    Call<String>getUser(@Query("Usuario") String name, @Query("Contrasenas") String password);
}
