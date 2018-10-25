package com.tickettravel.grupo2.tickettravel.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.activities.List_Tickets;
import com.tickettravel.grupo2.tickettravel.activities.TicketInTravel;
import com.tickettravel.grupo2.tickettravel.model.Travel;


public class TravelAdapter extends ArrayRvAdapter<Travel,TravelAdapter.TicketViewHolder>
{

    private Activity activity;

    public static class TicketViewHolder extends RecyclerView.ViewHolder
    {
    TextView title, itemorigin,itemdestiny,datestart2,dateend2, status;
    Button action_add, action_see;


    public TicketViewHolder(View itemView)
    {
        super(itemView);
        title= itemView.findViewById(R.id.title);
        itemorigin = itemView.findViewById(R.id.itemorigin);
        itemdestiny = itemView.findViewById(R.id.itemdestiny);
        datestart2 = itemView.findViewById(R.id.datestart2);
        dateend2 = itemView.findViewById(R.id.dateend2);
        status = itemView.findViewById(R.id.idstatus);
        action_add = itemView.findViewById(R.id.action_add);
        action_see = itemView.findViewById(R.id.action_see);
    }

    }

    public TravelAdapter(Activity activity){
        this.activity=activity;
    }
    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_travel_list, parent, false);
        return new TicketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TicketViewHolder holder, final int position)
    {
        Travel t = getItems().get(position);
        setTextViewHolder(holder,t);

        //TODO poner switch para status y separar la logica a metodos con nombres descriptivos
        int status = t.getStatus();
        switch (status){
            case 2:
                holder.status.setText("Closed");
                holder.status.setTextColor(Color.parseColor("#BF123F"));
                holder.action_add.setVisibility(View.GONE);
                break;
            default:
                setClickActionAddHolder(holder,position);
                break;
        }

        holder.action_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(activity,TicketInTravel.class);
                Bundle parametros= new Bundle();
                parametros.putInt("id_travel",getItems().get(position).getIdTravel());
                intent.putExtras(parametros);
                activity.startActivity(intent);
            }
        });
    }

    private void setClickActionAddHolder(TicketViewHolder holder, final int position) {
        holder.action_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(activity,List_Tickets.class);
                Bundle parametros= new Bundle();
                parametros.putInt("id_travel",getItems().get(position).getIdTravel());//TODO mover a constante
                intent.putExtras(parametros);
                activity.startActivity(intent);
            }
        });
    }

    private void setTextViewHolder(TicketViewHolder holder, Travel t) {
        holder.title.setText("Viaje N°: "+t.getIdTravel());
        holder.itemorigin.setText(t.getOrigin());
        holder.itemdestiny.setText(t.getDestiny());
        holder.datestart2.setText(t.getDateStart().trim());
        holder.dateend2.setText(t.getReturn().trim());
    }

}
