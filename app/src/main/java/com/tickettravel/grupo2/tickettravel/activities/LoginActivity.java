package com.tickettravel.grupo2.tickettravel.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.tickettravel.grupo2.tickettravel.R;
import com.tickettravel.grupo2.tickettravel.data.RestApiUser;

public class LoginActivity extends AppCompatActivity {

    //region properties
    private EditText userText, passText;
    private Button buttonText;
    private LottieAnimationView lottieAnimationView;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViewsById();
        buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void findViewsById(){
        userText = findViewById(R.id.login_userText);
        passText = findViewById(R.id.login_passText);
        buttonText=findViewById(R.id.login_buttonLogin);
        lottieAnimationView = findViewById(R.id.animationlottielogin);
    }

    private void login(String name) {
        Intent toHome=new Intent(LoginActivity.this,MainActivity.class );
        Bundle parametros = new Bundle();
        parametros.putString("name",name);
        toHome.putExtras(parametros);
        startActivity(toHome);
        finish();
    }

    private void SavePreference()
    {
        SharedPreferences preferences= getSharedPreferences("userSesion",Context.MODE_PRIVATE);//TODO crear constante
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("user",userText.getText().toString());//TODO crear constante
        editor.commit();
    }

    private void validate() {
        new LoadTask().execute(userText.getText().toString(),passText.getText().toString());
    }
    private class LoadTask extends AsyncTask<String, Void, String>
    {
        public LoadTask() {
        }

        protected void onPreExecute() {
            buttonText.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();
        }
        @Override
        protected String doInBackground(String...valores) {
            return RestApiUser.getInstance().getUser(valores[0],valores[1]);
        }

        @Override
        protected void onPostExecute(String user) {
           if(user==null)
           {buttonText.setVisibility(View.VISIBLE);
               lottieAnimationView.setVisibility(View.GONE);
               lottieAnimationView.cancelAnimation();
               Toast.makeText(LoginActivity.this,"Error de conexion, intente mas tarde por favor", Toast.LENGTH_LONG).show();}
           else  if(user.equals("1"))
           {
               SavePreference();
               login(userText.getText().toString());
           }
           else
           {
               buttonText.setVisibility(View.VISIBLE);
               lottieAnimationView.setVisibility(View.GONE);
               lottieAnimationView.cancelAnimation();
               Toast.makeText(LoginActivity.this,"Usuario o Contraseña incorrectas", Toast.LENGTH_LONG).show();

           }
        }
    }
}
