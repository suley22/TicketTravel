package com.tickettravel.grupo2.tickettravel.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.auxiliar.KeyExtra;
import com.tickettravel.grupo2.tickettravel.fragments.Rendir_fragment;
import com.tickettravel.grupo2.tickettravel.fragments.Ticket_fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //region properties
    private String NameUser;
    private FragmentManager fragmentManager;
    private Fragment fragment = null;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Bundle parametros;
    private Toolbar toolbar;
    private FloatingActionButton floatingBtn;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parametros = this.getIntent().getExtras();
        NameUser = parametros.getString(KeyExtra.KEY_EXTRA_USER_NAME);

        findViewsById();
        initFragment();
        setClickListenerFloatingButton();
        loadActionBarToolbar();
    }

    private void setClickListenerFloatingButton() {
        if(floatingBtn == null) return;

        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newTicket = new Intent(getApplicationContext(),NewTicket.class);
                startActivity(newTicket);
            }
        });
    }

    private void initFragment() {
        fragment = new Ticket_fragment();
        fragmentManager= getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentarea,fragment).commit();
    }

    private void findViewsById() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView =findViewById(R.id.nav_view);
        floatingBtn =findViewById(R.id.fab);
    }

    private void loadActionBarToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_actionBar_mainActivity));
        toolbar.setTitleTextColor(0xFFFFFFFF);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
        toggle.syncState();

        View hview = navigationView.getHeaderView(0);
        TextView nameuser = hview.findViewById(R.id.nameUserMenu);
        nameuser.setText(getResources().getString(R.string.title_name_user_nav_bar) + NameUser);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
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
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        switch(id){
            case R.id.nav_tickets:
                fragment= new Ticket_fragment();
                break;
            case R.id.nav_render:
                fragment= new Rendir_fragment();
                break;
            case R.id.nav_logout:
                LogoutConfirm();
                break;
        }

        if(fragment!=null)
        {
            fragmentManager= getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.fragmentarea,fragment);
            ft.commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void LogoutConfirm() {
        new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(getResources().getString(R.string.dialog_logout_title))
            .setMessage(getResources().getString(R.string.dialog_logout_message))
            .setPositiveButton(getResources().getString(R.string.dialog_logout_text_btn_ok), new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Logout();
                }
            })
            .setNegativeButton(getResources().getString(R.string.dialog_logout_text_btn_cancel), null)
            .show();
    }

    public void Logout(){
        SharedPreferences preferences = getSharedPreferences(KeyExtra.KEY_USER_SESION,0);
        preferences.edit().clear().apply();
        startActivity (new Intent(getApplicationContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
    }
}
