package com.tickettravel.grupo2.tickettravel.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.model.Travel;


public class TravelAdapter extends ArrayRvAdapter<Travel,TravelAdapter.TicketViewHolder>
{
    //region properties
    private Context context;
    private View.OnClickListener listener;
    private RvAdapterListener rvAdapterListener;
    //endregion

    public static class TicketViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener
    {
        //region properties
        private TextView title, itemorigin, itemdestiny, datestart2, dateend2, status;
        private Button action_add, action_see;
        private RvAdapterListener rvAdapterListener;
        //endregion

        private TicketViewHolder(View itemView, RvAdapterListener rvAdapterListener)
        {
            super(itemView);

            this.rvAdapterListener = rvAdapterListener;
            //region findViewById
            title= itemView.findViewById(R.id.title);
            itemorigin = itemView.findViewById(R.id.itemorigin);
            itemdestiny = itemView.findViewById(R.id.itemdestiny);
            datestart2 = itemView.findViewById(R.id.datestart2);
            dateend2 = itemView.findViewById(R.id.dateend2);
            status = itemView.findViewById(R.id.idstatus);
            action_add = itemView.findViewById(R.id.action_add);
            action_see = itemView.findViewById(R.id.action_see);
            //endregion
           action_add.setOnClickListener(this);
           action_see.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            rvAdapterListener.onItemClick(view, getLayoutPosition());
        }
    }

    public TravelAdapter(Context context, RvAdapterListener rvAdapterListener){
        this.context=context;
        this.rvAdapterListener = rvAdapterListener;
    }
    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_travel_list, parent, false);
        return new TicketViewHolder(itemView,rvAdapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final TicketViewHolder holder, final int position)
    {
        Travel t = getItems().get(position);
        setTextViewHolder(holder,t);

        int status = t.getStatus();
        switch (status){
            case 2:
                String stateClosedString = context.getResources().getString(R.string.status_travel_closed);
                holder.status.setText(stateClosedString);
                holder.status.setTextColor(ContextCompat.getColor(context,R.color.status_color_travel_closed));
                holder.action_add.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    private void setTextViewHolder(TicketViewHolder holder, Travel t) {
        String title = context.getResources().getString(R.string.title_travel_item_rendir_fragment);
        holder.title.setText(title + t.getIdTravel());
        holder.itemorigin.setText(t.getOrigin());
        holder.itemdestiny.setText(t.getDestiny());
        holder.datestart2.setText(t.getDateStart().trim());
        holder.dateend2.setText(t.getReturn().trim());
    }

}
