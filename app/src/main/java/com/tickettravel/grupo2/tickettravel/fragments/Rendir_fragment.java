package com.tickettravel.grupo2.tickettravel.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.adapter.TravelAdapter;
import com.tickettravel.grupo2.tickettravel.data.RestApiTravel;
import com.tickettravel.grupo2.tickettravel.model.Travel;
import java.util.ArrayList;


public class Rendir_fragment extends Fragment {

    private RecyclerView rvTicket;
    private TravelAdapter adapter;
    private FrameLayout frameLayout;
    private LoadTask threads;
    private ImageView imagenerror;
    private LottieAnimationView animationlottiemain;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       View v =inflater.inflate(R.layout.fragment_rendir, container, false);
        frameLayout=v.findViewById(R.id.frameloading);
        animationlottiemain=v.findViewById(R.id.animationlottiemain);
        imagenerror=v.findViewById(R.id.imagenerror);
        rvTicket = v.findViewById(R.id.rv_home_ticket);
        rvTicket.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter = new TravelAdapter(getActivity());
        rvTicket.setAdapter(adapter);
        threads= new LoadTask();
        threads.execute();
        return v;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
    }


    private class LoadTask extends AsyncTask<Void, Integer, ArrayList<Travel>>
    {
        public LoadTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Travel> doInBackground(Void... voids) {
            return RestApiTravel.getInstance().getTravels();
        }

        @Override
        protected void onPostExecute(ArrayList<Travel> travel) {
            if(travel!=null && travel.size()>0)
            {adapter.addAll(travel);
                frameLayout.setVisibility(View.GONE);
                rvTicket.setVisibility(View.VISIBLE);}
                 else {
                imagenerror.setVisibility(View.VISIBLE);
                animationlottiemain.setVisibility(View.GONE);}
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(threads!=null)
        {threads.cancel(true);}
    }
}
