package com.tickettravel.grupo2.tickettravel.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.adapter.RvAdapterTicketInTravel;
import com.tickettravel.grupo2.tickettravel.data.RestApiTicket;
import com.tickettravel.grupo2.tickettravel.model.Ticket;

import java.util.List;

public class TicketInTravel extends AppCompatActivity {
    //region properties
    private RecyclerView rvTicket;
    private RvAdapterTicketInTravel adapter;
    private FrameLayout frameLayout;
    private TextView textNull;
    private LottieAnimationView lottieAnimationView;
    private LoadTask threads;
    private Bundle data;
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_in_travel);
        findViewsById();
        showToolbar("Tickets en Viajes",true);
        lottieAnimationView.playAnimation();
        loadRecyclerView();
    }

    private void findViewsById() {
        frameLayout=findViewById(R.id.frameloading);
        rvTicket = findViewById(R.id.rv_home_ticket);
        textNull=findViewById(R.id.textnull);
        lottieAnimationView=findViewById(R.id.animationlottiemain);
    }

    private void loadRecyclerView() {
        rvTicket.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RvAdapterTicketInTravel(this);
        rvTicket.setAdapter(adapter);
    }

    public void showToolbar(String title, boolean upButton)
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
private class LoadTask extends AsyncTask<Integer, Integer, List<Ticket>>
{
    public LoadTask() {
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Ticket> doInBackground(Integer...IdTravel) {
       return RestApiTicket.getInstance().getTicketbyTravel(IdTravel[0]);
    }

    @Override
    protected void onPostExecute(List<Ticket> ticket) {
        if(ticket.size()>0)
        {adapter.addAll(ticket);
            rvTicket.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);}
        else
        {textNull.setVisibility(View.VISIBLE);
            lottieAnimationView.cancelAnimation();
            lottieAnimationView.setVisibility(View.GONE);
        }
    }
}

    @Override
    public void onStart() {
        super.onStart();
        data = this.getIntent().getExtras();
        adapter.clear();
        threads= new LoadTask();
        threads.execute(data.getInt("id_travel"));
    }


    @Override
    public void onStop() {
        super.onStop();
        if (threads != null) {
            threads.cancel(true);
        }
    }
}
