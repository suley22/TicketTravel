package com.tickettravel.grupo2.tickettravel.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.activities.Edit_Ticket;
import com.tickettravel.grupo2.tickettravel.adapter.RvAdapter;
import com.tickettravel.grupo2.tickettravel.auxiliar.RecyclerItemTouchHelper;
import com.tickettravel.grupo2.tickettravel.model.Ticket;

import java.util.List;

public class Ticket_fragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView rvTicket;
    private RvAdapter adapter;
    private FrameLayout frameLayout;
    private TextView textNull;
    private LottieAnimationView lottieAnimationView;
    private LoadTask threads;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ticket_fragment, container, false);

        //References
        frameLayout = v.findViewById(R.id.frameloading);
        rvTicket = v.findViewById(R.id.rv_home_ticket);
        textNull = v.findViewById(R.id.textnull);
        lottieAnimationView = v.findViewById(R.id.animationlottiemain);
        lottieAnimationView.playAnimation();

        // Set Recycler
        SetRecycler(v);
        return v;
    }


    private void SetRecycler(View v) {
        rvTicket.setLayoutManager(new LinearLayoutManager(v.getContext()));
        rvTicket.setItemAnimator(new DefaultItemAnimator());
        rvTicket.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new RvAdapter();
        rvTicket.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvTicket);

        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Edit_Ticket.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Mi Ticket", adapter.get(rvTicket.getChildAdapterPosition(v)));
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    private class LoadTask extends AsyncTask<Void, Integer, List<Ticket>> {
        public LoadTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Ticket> doInBackground(Void... voids) {
            List<Ticket> ticket = Ticket.listAll(Ticket.class);
            return ticket;
        }

        @Override
        protected void onPostExecute(List<Ticket> ticket) {
            if (ticket.size() > 0) {
                adapter.addAll(ticket);
                rvTicket.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
            } else {
                textNull.setVisibility(View.VISIBLE);
                lottieAnimationView.cancelAnimation();
                lottieAnimationView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof RvAdapter.TicketViewHolder) {
            final long name = adapter.get(viewHolder.getAdapterPosition()).getId();
            final Ticket deletedItem = adapter.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            adapter.remove(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar
                    .make(getActivity().findViewById(R.id.coordinatormain), "Ticket N°" + name + " Borrado!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.restoreItem(deletedItem, deletedIndex);
                }
            }).setCallback(new Snackbar.Callback() {

                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                        Ticket ticket = Ticket.findById(Ticket.class, name);
                        ticket.delete();
                    }
                }
            });

            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.clear();
        threads = new LoadTask();
        threads.execute();
    }


    @Override
    public void onStop() {
        super.onStop();
        if(threads != null && threads.getStatus().equals(AsyncTask.Status.RUNNING))
            threads.cancel(true);
        }
    }


