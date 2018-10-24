package com.tickettravel.grupo2.tickettravel.auxiliar;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.cloudinary.android.MediaManager;


public class CloudinarySingleton extends Application {

    private static  MediaManager mediaManager;
    public static MediaManager getInstance(Context context)
    {
        if(mediaManager==null)
        {MediaManager.init(context);
        mediaManager=MediaManager.get();}
        return mediaManager;
    }
}
