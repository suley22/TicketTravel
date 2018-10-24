package com.tickettravel.grupo2.tickettravel.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.fragments.Rendir_fragment;
import com.tickettravel.grupo2.tickettravel.fragments.Ticket_fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    String NameUser;
    FragmentManager fragmentManager;
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle parametros = this.getIntent().getExtras();
        NameUser= parametros.getString("name");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragment = new Ticket_fragment();
        fragmentManager= getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentarea,fragment).commit();

        getSupportActionBar().setTitle("Ticket Travel");
        toolbar.setTitleTextColor(0xFFFFFFFF);
       FloatingActionButton fab =findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newTicket = new Intent(getApplicationContext(),NewTicket.class);
                startActivity(newTicket);
            }
        });

        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        toggle.syncState();

        NavigationView navigationView =findViewById(R.id.nav_view);
        View hview= navigationView.getHeaderView(0);
        TextView nameuser= hview.findViewById(R.id.nameUserMenu);
        nameuser.setText("Bienvenido "+NameUser);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;
       // }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_tickets) {
            fragment= new Ticket_fragment();

        } else if (id == R.id.nav_render) {

           fragment= new Rendir_fragment();
           // Intent intent =new Intent(this,List_Tickets.class);
           // startActivity(intent);
        } else if (id == R.id.nav_logout) {
            SharedPreferences preferences =getSharedPreferences("userSesion",0);
            preferences.edit().clear().commit();
            this.startActivity (new Intent(getApplicationContext(), LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();

        }

        if(fragment!=null)
        {

            fragmentManager= getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.fragmentarea,fragment);
            ft.commit();
        }

        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
