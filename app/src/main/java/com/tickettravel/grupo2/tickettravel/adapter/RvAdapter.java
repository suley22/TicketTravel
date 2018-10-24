package com.tickettravel.grupo2.tickettravel.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.model.Ticket;

public class RvAdapter extends ArrayRvAdapter<Ticket,RvAdapter.TicketViewHolder> implements View.OnClickListener
{

    private View.OnClickListener listener;
    public static class TicketViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvType;
        TextView tvNro;
        TextView tvAmount;
        TextView tvTicketDate;
        LinearLayout linearColor;
        TextView tvObservation;
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
        }
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_ticket_list, parent, false);
        v.setOnClickListener(listener);
        return new TicketViewHolder(v);
    }

    public void setListener(View.OnClickListener listener)
    {
        this.listener=listener;
    }
    @Override
    public void onClick(View v) {
        if(listener!=null)
            listener.onClick(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, final int position)
    {
        Ticket t = getItems().get(position);
        holder.tvNro.setText("Ticket N°: "+t.getId());
        holder.tvType.setText(t.getTicketTypeDescription());
        holder.tvAmount.setText(String.valueOf(t.getAmount()));
        holder.tvTicketDate.setText(t.getDate());
        holder.tvObservation.setText(t.getObservation());
        if(t.getTicketTypeDescription().equals("Taxi"))
        {holder.linearColor.setBackgroundColor(Color.parseColor("#84DBFF"));}
        else if(t.getTicketTypeDescription().equals("Cafeteria"))
        {holder.linearColor.setBackgroundColor(Color.parseColor("#D01F4D"));}
    }

    public void restoreItem(Ticket item, int position) {
        add(position, item);
        notifyItemInserted(position);
    }
}
