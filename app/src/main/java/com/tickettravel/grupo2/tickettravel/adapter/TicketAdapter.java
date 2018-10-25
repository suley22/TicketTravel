package com.tickettravel.grupo2.tickettravel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.auxiliar.FlipAnimator;
import com.tickettravel.grupo2.tickettravel.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends ArrayRvAdapter<Ticket,TicketAdapter.TicketViewHolder>
{
    //region properties
    private Context mContext;
    private static TicketAdapterListener listener;
    private SparseBooleanArray selectedItems;
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;
    private static int currentSelectedIndex = -1;
    //endregion

    //    //    //TODO mover clases holder a carpeta separada si se puede
    public static class TicketViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        //region properties
        public TextView from, message, timestamp;
        public ImageView imgProfile;
        public LinearLayout messageContainer;
        public RelativeLayout iconContainer, iconBack, iconFront;
        private TextView tvType,tvNro,tvAmount,tvTicketDate;
        private TextView tvObservation;
        //endregion

        public TicketViewHolder(View view) {
            super(view);
            //region findViewsById
            tvNro= itemView.findViewById(R.id.from);
            tvType = itemView.findViewById(R.id.txt_primary);
            tvAmount = itemView.findViewById(R.id.timestamp);
            tvTicketDate = itemView.findViewById(R.id.tv_item_ticketDate);
            tvObservation = itemView.findViewById(R.id.txt_secondary);
            iconBack =view.findViewById(R.id.icon_back);
            iconFront =view.findViewById(R.id.icon_front);
            imgProfile =view.findViewById(R.id.icon_profile);
            messageContainer = view.findViewById(R.id.message_container);
            iconContainer = view.findViewById(R.id.icon_container);
            //endregion
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            listener.onRowLongClicked(getAdapterPosition());
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }

    public TicketAdapter(Context mContext, TicketAdapterListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_row, parent, false);
        return new TicketViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket t = getItems().get(position);
        setTextViewsBindViewHoder(holder,t);
        setImageProfile(holder,t);

        holder.itemView.setActivated(selectedItems.get(position, false));
        applyIconAnimation(holder, position);
        applyClickEvents(holder, position);
    }

    private void setImageProfile(TicketViewHolder holder,Ticket t) {
        String descTicketType = t.getTicketTypeDescription();
        switch (descTicketType){
            case "Taxi"://TODO hacer enumerable o constantes
                holder.imgProfile.setImageResource(R.drawable.taxi);
                break;
            case "Cafeteria":
                holder.imgProfile.setImageResource(R.drawable.cooffe);
                break;
            default:
                holder.imgProfile.setImageResource(R.drawable.food);
                break;
        }
    }

    private void setTextViewsBindViewHoder(TicketViewHolder holder,Ticket t) {
        holder.tvNro.setText("Ticket N°: "+t.getId());
        holder.tvType.setText(t.getTicketTypeDescription());
        holder.tvAmount.setText("$ " +String.valueOf(t.getAmount()));
        holder.tvTicketDate.setText(t.getDate());
        holder.tvObservation.setText(t.getObservation());
    }

    private void applyClickEvents(TicketViewHolder holder, final int position) {
        holder.messageContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onRowLongClicked(position);
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
    }

    private void applyIconAnimation(TicketViewHolder holder, int position) {
        if (selectedItems.get(position, false)) {
            holder.iconFront.setVisibility(View.GONE);
            resetIconYAxis(holder.iconBack);
            holder.iconBack.setVisibility(View.VISIBLE);
            holder.iconBack.setAlpha(1);
            if (currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, true);
                resetCurrentIndex();
            }
        } else {
            holder.iconBack.setVisibility(View.GONE);
            resetIconYAxis(holder.iconFront);
            holder.iconFront.setVisibility(View.VISIBLE);
            holder.iconFront.setAlpha(1);
            if ((reverseAllAnimations && animationItemsIndex.get(position, false)) || currentSelectedIndex == position) {
                FlipAnimator.flipView(mContext, holder.iconBack, holder.iconFront, false);
                resetCurrentIndex();
            }
        }
    }

    private void resetIconYAxis(View view) {
        if (view.getRotationY() != 0) {
            view.setRotationY(0);
        }
    }

    public void resetAnimationIndex() {
        reverseAllAnimations = false;
        animationItemsIndex.clear();
    }

    @Override
    public long getItemId(int position) {
        return getItems().get(position).getId();
    }
    @Override
    public int getItemCount() {
        return getItems().size();
    }

    public void toggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
            animationItemsIndex.delete(pos);
        } else {
            selectedItems.put(pos, true);
            animationItemsIndex.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        reverseAllAnimations = true;
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        getItems().remove(position);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }

    public interface TicketAdapterListener {
        void onRowLongClicked(int position);
    }
}
