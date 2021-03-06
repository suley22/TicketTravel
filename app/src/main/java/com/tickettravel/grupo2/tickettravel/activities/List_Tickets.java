package com.tickettravel.grupo2.tickettravel.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.adapter.TicketAdapter;
import com.tickettravel.grupo2.tickettravel.auxiliar.CloudinarySingleton;
import com.tickettravel.grupo2.tickettravel.auxiliar.Constants;
import com.tickettravel.grupo2.tickettravel.auxiliar.KeyExtra;
import com.tickettravel.grupo2.tickettravel.data.RestApiTicket;
import com.tickettravel.grupo2.tickettravel.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class List_Tickets extends AppCompatActivity implements TicketAdapter.TicketAdapterListener {
    //region properties
    private RecyclerView recyclerView;
    private TicketAdapter mAdapter;
    private FrameLayout frameLayout;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private TextView textnull;
    private LottieAnimationView lottieAnimationView;
    private int IdTravel;
    private ImageView imagenerror;
    private LoadPost loadPost;
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__tickets);
        loadToolbar();

        Bundle data=this.getIntent().getExtras();
        IdTravel = data.getInt(KeyExtra.KEY_EXTRA_ID_TRAVEL);
        CloudinarySingleton.getInstance(this);

        findViewsById();
        loadRecyclerView();
        actionModeCallback = new ActionModeCallback();
    }

    private void findViewsById() {
        imagenerror=findViewById(R.id.imagenerror);
        recyclerView = findViewById(R.id.rv_home_ticket);
        frameLayout= findViewById(R.id.frameloading);
        textnull=findViewById(R.id.textnull);
        lottieAnimationView=findViewById(R.id.animationlottiemain);
        lottieAnimationView.playAnimation();
    }

    private void loadRecyclerView() {
        mAdapter = new TicketAdapter(this, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
    }

    private void loadToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.clear();
        new LoadTask().execute();
    }
    @Override
    public void onStop() {
        super.onStop();
        if(loadPost!=null)
        {loadPost.cancel(true);}
    }

    private class LoadTask extends AsyncTask<Void, Integer, List<Ticket>>
    {
        private LoadTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Ticket> doInBackground(Void... voids) {
            return Ticket.listAll(Ticket.class);
        }

        @Override
        protected void onPostExecute(List<Ticket> ticket) {
            if(ticket.size()>0)
            {
                mAdapter.addAll(ticket);
                recyclerView.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.GONE);
            }
            else
            {
                textnull.setVisibility(View.VISIBLE);
                textnull.setText(getResources().getString(R.string.Recyclernull));
                lottieAnimationView.cancelAnimation();
                lottieAnimationView.setVisibility(View.GONE);
            }
        }
    }

    private class LoadPost extends AsyncTask<ArrayList<Ticket>, Integer,String>
    {
        private LoadPost() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();
        }

        @Override
        protected String doInBackground(ArrayList<Ticket>... lists) {
            ArrayList<Ticket> processedTickets = RestApiTicket.getInstance().postTickets(lists[0]);

            if(processedTickets != null){
                for (int i = 0; i < lists[0].size();i++){
                    Ticket ticket = lists[0].get(i);
                    ticket.delete();
                }
            }

            return "";
        }
        @Override
        protected void onPostExecute(String ticket) {

            // delete all the selected messages
            if(ticket==null)
            {
                imagenerror.setVisibility(View.VISIBLE);
                textnull.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.GONE);
                return;
            }

            recyclerView.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
            lottieAnimationView.cancelAnimation();

            switch(ticket){
                case Constants.POST_EXECUTE_OK:
                    break;
                case Constants.POST_EXECUTE_FAIL:
                    Toast.makeText(getApplicationContext(),"Error de conexion, intente mas tarde por favor", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onRowLongClicked(int position) {
        enableActionMode(position);
    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_done:
                    postTickets();
                    deleteTickets();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        private  void postTickets()
        {
            loadPost=new LoadPost();
            loadPost.execute(getTickets());
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelections();
            actionMode = null;
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.resetAnimationIndex();
                }
            });
        }
    }

    // get list of tickets
    private ArrayList<Ticket> getTickets()
    {
        ArrayList<Ticket>tickets= new ArrayList<>();
        List<Integer> selectedItemPositions = mAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            mAdapter.get(i).setIdTravel(IdTravel);
            tickets.add(mAdapter.get(i));
        }
        return tickets;
    }

    // deleting the tickets from recycler view
    private void deleteTickets() {
        mAdapter.resetAnimationIndex();
        List<Integer> selectedItemPositions = mAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            mAdapter.removeData(selectedItemPositions.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }
}