package com.tickettravel.grupo2.tickettravel.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.activities.List_Tickets;
import com.tickettravel.grupo2.tickettravel.activities.TicketInTravel;
import com.tickettravel.grupo2.tickettravel.adapter.RvAdapterListener;
import com.tickettravel.grupo2.tickettravel.adapter.TravelAdapter;
import com.tickettravel.grupo2.tickettravel.auxiliar.KeyExtra;
import com.tickettravel.grupo2.tickettravel.data.RestApiTravel;
import com.tickettravel.grupo2.tickettravel.model.Travel;
import java.util.ArrayList;


public class Rendir_fragment extends Fragment implements RvAdapterListener {

    //region properties
    private RecyclerView rvTicket;
    private TravelAdapter adapter;
    private FrameLayout frameLayout;
    private LoadTask threads;
    private ImageView imagenerror;
    private LottieAnimationView animationlottiemain;
    //endregion
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rendir, container, false);

        //region findViewId
        frameLayout=v.findViewById(R.id.frameloading);
        animationlottiemain=v.findViewById(R.id.animationlottiemain);
        imagenerror=v.findViewById(R.id.imagenerror);
        rvTicket = v.findViewById(R.id.rv_home_ticket);
        //endregion

        rvTicket.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter = new TravelAdapter(v.getContext(),this);
        rvTicket.setAdapter(adapter);
        threads= new LoadTask();
        threads.execute();
        return v;
    }

    @Override
    public void onItemClick(View v, int itemPosition)
    {
        if(v.getId() == R.id.action_add){
            Intent intent= new Intent(getContext(),List_Tickets.class);
            Bundle parametros = new Bundle();
            parametros.putInt(KeyExtra.KEY_EXTRA_ID_TRAVEL,adapter.getItems().get(itemPosition).getIdTravel());
            intent.putExtras(parametros);
            startActivity(intent);
        }
        if(v.getId() == R.id.action_see){
            Intent intent= new Intent(getContext(),TicketInTravel.class);
            Bundle parametros = new Bundle();
            parametros.putInt(KeyExtra.KEY_EXTRA_ID_TRAVEL,adapter.getItems().get(itemPosition).getIdTravel());
            intent.putExtras(parametros);
            startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(View v, int itemPosition) {
        return false;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);
    }


    private class LoadTask extends AsyncTask<Void, Integer, ArrayList<Travel>>
    {
        private LoadTask() {
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
