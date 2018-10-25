package com.tickettravel.grupo2.tickettravel.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.model.Ticket;

public class RvAdapterTicketInTravel extends ArrayRvAdapter<Ticket,RvAdapterTicketInTravel.TicketViewHolder>{

    //region properties
    private Context context;
    //endregion

    public RvAdapterTicketInTravel(Context context)
    {
        this.context=context;
    }
    //TODO mover clases holder a carpeta separada si se puede
    public static class TicketViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvType;
        private TextView tvNro;
        private TextView tvAmount;
        private TextView tvTicketDate;
        private LinearLayout linearColor;
        private TextView tvObservation;
        private ImageView imageP;
        public RelativeLayout viewBackground, viewForeground;

        public TicketViewHolder(View itemView)
        {
            super(itemView);
            tvNro= itemView.findViewById(R.id.tv_item_nro);
            tvType = itemView.findViewById(R.id.tv_item_type);
            tvAmount = itemView.findViewById(R.id.tv_item_amount);
            tvTicketDate = itemView.findViewById(R.id.tv_item_ticketDate);
            linearColor = itemView.findViewById(R.id.linearColor);
            tvObservation = itemView.findViewById(R.id.tv_item_Observation);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            imageP=itemView.findViewById(R.id.getimagen);
        }
    }

    @NonNull
    @Override
    public RvAdapterTicketInTravel.TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticketintravel, parent, false);
        return new RvAdapterTicketInTravel.TicketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapterTicketInTravel.TicketViewHolder holder, final int position)
    {
        Ticket t = getItems().get(position);
        setTextViewsBindViewHoder(holder,t);
        setColorTicketByType(holder,t);

        Glide.with(context)
             .load(t.getImageUrl())
             .apply(new RequestOptions().placeholder(R.drawable.progress_animation).error(R.drawable.image_ticket).centerCrop())
             .into(holder.imageP);
    }

    private void setColorTicketByType(TicketViewHolder holder, Ticket t) {
        String descTypeTicket = t.getTicketTypeDescription();

        switch(descTypeTicket){
            case "Taxi"://TODO mover a constante
                holder.linearColor.setBackgroundColor(Color.parseColor("#84DBFF"));//TODO mover a constantes res
                break;
            case "Cafeteria":
                holder.linearColor.setBackgroundColor(Color.parseColor("#D01F4D"));//TODO mover a constantes res
                break;
            default:
                break;
        }
    }

    private void setTextViewsBindViewHoder(TicketViewHolder holder, Ticket t) {
        holder.tvNro.setText("Ticket N°: "+t.getId());
        holder.tvType.setText(t.getTicketTypeDescription());
        holder.tvAmount.setText(String.valueOf(t.getAmount()));
        holder.tvTicketDate.setText(t.getDate());
        holder.tvObservation.setText(t.getObservation());
    }
}

