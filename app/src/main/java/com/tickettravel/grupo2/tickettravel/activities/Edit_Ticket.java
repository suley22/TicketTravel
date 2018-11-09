package com.tickettravel.grupo2.tickettravel.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.auxiliar.Constants;
import com.tickettravel.grupo2.tickettravel.auxiliar.KeyExtra;
import com.tickettravel.grupo2.tickettravel.auxiliar.RealPathUtil;
import com.tickettravel.grupo2.tickettravel.data.RestApiTypeCurrency;
import com.tickettravel.grupo2.tickettravel.data.RestApiTypeTicket;
import com.tickettravel.grupo2.tickettravel.model.Ticket;
import com.tickettravel.grupo2.tickettravel.model.TypeCurrency;
import com.tickettravel.grupo2.tickettravel.model.TypeTicket;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Calendar;

public class Edit_Ticket extends AppCompatActivity {

    //region Properties
    private Uri path;
    private  String realPath;
    private TextView amount,geolocation, dateticket, observation;
    private int itemSelectedCurrency,itemSelectedType;
    private String itemSelectedCurrencyDescription;
    private String itemSelectedTypeDescription;
    private DatePickerDialog datePickerDialog;
    private Spinner spinnerTypeCurrency,spinnerTypeTicket;
    private static final int PHOTO_SELECTED = 1;
    private ImageButton imageButton;
    private ScrollView scroll;
    private FrameLayout frameLayout;
    private ImageView imagenError;
    private LottieAnimationView animationLottieMain;
    private Bundle bundle = null;
    private Ticket parameters;
    private final String TITLE_GET_EXTRA_TICKET = "Editar Ticket";
    private LoadTaskTypeCurrency loadTaskTypeCurrency;
    private LoadTaskTypeTicket loadTaskTypeTicket;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__ticket);
        showToolbar(TITLE_GET_EXTRA_TICKET,true);
        findViewsById();
        setListenerSpinnerTypeTicket();
        dateticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showDatePickerDialog();}
        });
        setOnClickListenerImageButton();
        SetValues();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_mode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_done:
                UpdateTicket();
                break;
            default:
                break;
        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(resultCode){
            case RESULT_OK:
                setImageUri(data);
                break;
            default:
                break;
        }
    }

    private void setImageUri(Intent data) {
        path = data.getData();
        imageButton.setImageURI(path);
        realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
    }

    private void setOnClickListenerImageButton() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PHOTO_SELECTED);
            }
        });
    }

    private void setListenerSpinnerTypeTicket() {
        spinnerTypeCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypeCurrency aux= (TypeCurrency) parent.getItemAtPosition(position);
                itemSelectedCurrencyDescription= aux.toString();
                itemSelectedCurrency=aux.getCurrencyId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spinnerTypeTicket.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypeTicket aux =(TypeTicket) parent.getItemAtPosition(position);
                itemSelectedTypeDescription= aux.toString();
                itemSelectedType=aux.getTypeId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateticket.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void findViewsById() {
        scroll= findViewById(R.id.scroollprincipal);
        amount= findViewById(R.id.amountticket);
        geolocation = findViewById(R.id.georeferenciaticket);
        observation= findViewById(R.id.observation);
        spinnerTypeCurrency = findViewById(R.id.spinnerTypeCurrency);
        spinnerTypeTicket = findViewById(R.id.spinnerTypeTycket);
        dateticket =findViewById(R.id.dateticket);
        imageButton=findViewById(R.id.imagebutton);
        frameLayout= findViewById(R.id.frameloading);
        animationLottieMain=findViewById(R.id.animationlottiemain);
        imagenError =findViewById(R.id.imagenerror);
    }

    public void showToolbar(String title, boolean upButton)
    {
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void SetValues()
    {
        bundle=getIntent().getExtras();
        parameters =(Ticket)bundle.getSerializable(Constants.TICKET);
        amount.setText(String.valueOf(parameters.getAmount()));
        geolocation.setText(parameters.getGeolocation());
        observation.setText(parameters.getObservation());
        dateticket.setText(parameters.getDate());
        spinnerTypeTicket.setSelection(parameters.getTicketTypeId());
        spinnerTypeCurrency.setSelection(parameters.getTypeCurrencyId());
        imageButton.setImageURI(Uri.parse(parameters.getImageUrl()));
        realPath= parameters.getImageUrl();
    }

    private class LoadTaskTypeTicket extends AsyncTask<Void, Integer, ArrayList<TypeTicket>>
    {
        private LoadTaskTypeTicket() {
        }

        @Override
        protected ArrayList<TypeTicket> doInBackground(Void...voids) {
            return RestApiTypeTicket.getInstance().getTypeTickets();
        }

        @Override
        protected void onPostExecute(ArrayList<TypeTicket> ticket) {
            if(ticket!=null && ticket.size()>0)
            { spinnerTypeTicket.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, ticket));}
            else {
                imagenError.setVisibility(View.VISIBLE);
                animationLottieMain.setVisibility(View.GONE);}
        }
    }
    private class LoadTaskTypeCurrency extends AsyncTask<Void, Integer, ArrayList<TypeCurrency>>
    {
        private LoadTaskTypeCurrency() {
        }

        @Override
        protected ArrayList<TypeCurrency> doInBackground(Void...voids) {
            return RestApiTypeCurrency.getInstance().getTypeCurrency();
        }

        @Override
        protected void onPostExecute(ArrayList<TypeCurrency> ticket) {
            if(ticket!=null && ticket.size()>0)
            {spinnerTypeCurrency.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, ticket));
                frameLayout.setVisibility(View.GONE);
                scroll.setVisibility(View.VISIBLE);}
            else {
                imagenError.setVisibility(View.VISIBLE);
                animationLottieMain.setVisibility(View.GONE);}
        }
    }

    public void UpdateTicket() {

        if (validatorControl()) {
            try {

                Ticket ticket = Ticket.findById(Ticket.class, parameters.getId());

                ticket.setAmount(Float.parseFloat(amount.getText().toString()));
                ticket.setTicketTypeId(itemSelectedType);
                ticket.setTicketTypeDescription(itemSelectedTypeDescription);
                ticket.setDate(dateticket.getText().toString());
                ticket.setGeolocation(geolocation.getText().toString());
                ticket.setTypeCurrencyId(itemSelectedCurrency);
                ticket.setTypeCurrencyDescription(itemSelectedCurrencyDescription);
                ticket.setObservation(observation.getText().toString());
                ticket.setImageUrl(realPath);
                ticket.save();

                Toast.makeText(this, R.string.toast_text_updated_edit_ticket, Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(this, R.string.toast_text_updated_error_edit_ticket, Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, R.string.toast_text_updated_error_field_validation_edit_ticket, Toast.LENGTH_SHORT).show();
        }
    }
    private boolean validatorControl()
    {
        boolean result=false;
        if(!amount.getText().toString().matches("") &&
           !dateticket.getText().toString().matches(""))
        {
            result=true;
        }
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
    {
        super.onStop();

        if(loadTaskTypeTicket == null)
            return;
        if( loadTaskTypeTicket.getStatus().equals(AsyncTask.Status.RUNNING))
            loadTaskTypeTicket.cancel(true);
        if(loadTaskTypeCurrency == null)
            return;
        if( loadTaskTypeCurrency.getStatus().equals(AsyncTask.Status.RUNNING))
            loadTaskTypeCurrency.cancel(true);
    }
}
