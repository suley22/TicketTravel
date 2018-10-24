package com.tickettravel.grupo2.tickettravel.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.cloudinary.android.MediaManager;
import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.auxiliar.CloudinarySingleton;
import com.tickettravel.grupo2.tickettravel.auxiliar.RealPathUtil;
import com.tickettravel.grupo2.tickettravel.data.RestApiTypeCurrency;
import com.tickettravel.grupo2.tickettravel.data.RestApiTypeTicket;
import com.tickettravel.grupo2.tickettravel.model.Ticket;
import com.tickettravel.grupo2.tickettravel.model.TypeCurrency;
import com.tickettravel.grupo2.tickettravel.model.TypeTicket;

import java.util.ArrayList;
import java.util.Calendar;

public class NewTicket extends AppCompatActivity {

    private static final int PHOTO_SELECTED = 1;
    private Uri path;
    private String realPath;
    private TextView Amount, geolocation, dateTicket, observation;
    private int itemSelectedCurrency, itemSelectedType;
    private String itemSelectedCurrencyDescription, itemSelectedTypeDescription;
    private DatePickerDialog datePickerDialog;
    private Spinner spinnerTypeCurrency, spinnerTypeTicket;
    private ImageButton imageButton;
    private NestedScrollView scroll;
    private FrameLayout frameLayout;
    private ImageView imageError;
    private LottieAnimationView animationLottieMain;
    private LoadTaskTypeTicket loadTaskTypeTicket;
    private LoadTaskTypeCurrency loadTaskTypeCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__ticket);
        showToolbar("New Ticket", true);

        //References
        scroll = findViewById(R.id.scroollprincipal);
        Amount = findViewById(R.id.amountticket);
        geolocation = findViewById(R.id.georeferenciaticket);
        observation = findViewById(R.id.observation);
        spinnerTypeCurrency = findViewById(R.id.spinnerTypeCurrency);
        spinnerTypeTicket = findViewById(R.id.spinnerTypeTycket);
        imageError = findViewById(R.id.imagenerror);
        dateTicket = findViewById(R.id.dateticket);
        imageButton = findViewById(R.id.imagebutton);
        frameLayout = findViewById(R.id.frameloading);
        animationLottieMain = findViewById(R.id.animationlottiemain);

        //Events Clicks
        dateTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker();
            }
        });
        spinnerTypeCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypeCurrency aux = (TypeCurrency) parent.getItemAtPosition(position);
                itemSelectedCurrencyDescription = aux.toString();
                itemSelectedCurrency = aux.getCurrencyId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spinnerTypeTicket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypeTicket aux = (TypeTicket) parent.getItemAtPosition(position);
                itemSelectedTypeDescription = aux.toString();
                itemSelectedType = aux.getTypeId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickButtonImage();
            }
        });
    }

    private void ClickButtonImage() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PHOTO_SELECTED);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PHOTO_SELECTED);
        }
    }

    private void showToolbar(String title, boolean upButton) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void DatePicker() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(NewTicket.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateTicket.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private class LoadTaskTypeTicket extends AsyncTask<Void, Integer, ArrayList<TypeTicket>> {
        public LoadTaskTypeTicket() {
        }

        @Override
        protected ArrayList<TypeTicket> doInBackground(Void... voids) {
            return RestApiTypeTicket.getInstance().getTypeTickets();
        }

        @Override
        protected void onPostExecute(ArrayList<TypeTicket> ticket) {
            if (ticket != null && ticket.size() > 0)
                spinnerTypeTicket.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, ticket));
            else {
                imageError.setVisibility(View.VISIBLE);
                animationLottieMain.setVisibility(View.GONE);
            }
        }
    }

    private class LoadTaskTypeCurrency extends AsyncTask<Void, Integer, ArrayList<TypeCurrency>> {
        public LoadTaskTypeCurrency() {
        }

        @Override
        protected ArrayList<TypeCurrency> doInBackground(Void... voids) {
            return RestApiTypeCurrency.getInstance().getTypeCurrency();
        }

        @Override
        protected void onPostExecute(ArrayList<TypeCurrency> ticket) {
            if (ticket != null && ticket.size() > 0) {
                spinnerTypeCurrency.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, ticket));
                frameLayout.setVisibility(View.GONE);
                scroll.setVisibility(View.VISIBLE);
            } else {
                imageError.setVisibility(View.VISIBLE);
                animationLottieMain.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            path = data.getData();
            imageButton.setImageURI(path);
            realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
        }
    }

    public void SaveTicket(View v) {
        if (validatorControl() == true) {
            try {
                Ticket ticket = new Ticket(Float.parseFloat(Amount.getText().toString()), itemSelectedType, itemSelectedTypeDescription, dateTicket.getText().toString(), geolocation.getText().toString(), itemSelectedCurrency, itemSelectedCurrencyDescription, observation.getText().toString(), realPath.toString());
                ticket.save();
                Toast.makeText(this, "Ticket Guardado", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Ocurrio Problema en el Guardado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Complete el ticket con todos los campos y foto de ticket", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validatorControl() {
        boolean result = false;
        if (Amount.getText().toString() != null && itemSelectedType != 0
                && itemSelectedTypeDescription != null && dateTicket.getText().toString() != null
                && geolocation.getText().toString() != null && itemSelectedCurrency != 0
                && itemSelectedCurrencyDescription != null && observation.getText().toString() != null
                && realPath != null)
        {result = true;}
        return result;
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        //Threads
        loadTaskTypeTicket =new LoadTaskTypeTicket();
        loadTaskTypeTicket.execute();
        loadTaskTypeCurrency = new LoadTaskTypeCurrency();
        loadTaskTypeCurrency.execute();
    }
    @Override
    protected void onStop()
    { super.onStop();
        if(loadTaskTypeTicket != null && loadTaskTypeTicket.getStatus().equals(AsyncTask.Status.RUNNING))
            loadTaskTypeTicket.cancel(true);
        if(loadTaskTypeCurrency != null && loadTaskTypeCurrency.getStatus().equals(AsyncTask.Status.RUNNING))
            loadTaskTypeCurrency.cancel(true);
    }

}
