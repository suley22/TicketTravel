package com.tickettravel.grupo2.tickettravel.data;

import com.tickettravel.grupo2.tickettravel.data.services.UserService;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiUser {
    private static RestApiUser restApi;

    private RestApiUser()
    {
        //Prevent initialization from the reflection api.
        if (restApi != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public static synchronized RestApiUser getInstance()
    {
        if(restApi == null)
            restApi = new RestApiUser();

        return restApi;
    }

    public String getUser(String name, String password)
    {
        String nameUser=null;

        try
        {
            Retrofit retrofit = buildRetrofit();
            UserService userService = retrofit.create(UserService.class);
            Call<String> UserCall = userService.getUser(name,password);
            nameUser=UserCall.execute().body();
        }
        catch(Exception e)
        {
            nameUser="0";
        }
        return  nameUser;

    }

    private Retrofit buildRetrofit()
    {
        return new Retrofit.Builder().
                baseUrl("http://www.promon.net.ar/Test/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
}

