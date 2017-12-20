package com.example.reivin.log;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.concurrent.RejectedExecutionHandler;

public class log extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    public static final int SIGN_IN_CODE=777;

    private EditText nombre;
    private EditText contraseña;
    private TextView info;
    private Button login;
    private int cont = 3;
    public Button registrar;
    private TextView notifi;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient= new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton =(SignInButton) findViewById(R.id.btn_gml);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);

            }
        });









        nombre = (EditText)findViewById(R.id.txt_use);
        contraseña = (EditText)findViewById(R.id.txt_pass);
        info = (TextView) findViewById(R.id.inf_intent);
        login = (Button)findViewById(R.id.bt_ingres);
        registrar = (Button)findViewById((R.id.bt_regis));
        notifi = (TextView) findViewById(R.id.alert);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarse();
            }

        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 logeo(nombre.getText().toString(), contraseña.getText().toString());
            }
        });

    }
    private void logeo (String use_nom, String use_pass){
        if((use_nom.equals("admin"))  && (use_pass.equals("admin"))){
            Intent next = new Intent(this, menu.class);
            startActivity(next);
        }
        else{
            cont --;

            info.setText("intento fallido" + String.valueOf(cont));
            notifi.setText("usuario o contraseña incorrecta");
            if (cont==0){
                login.setEnabled(false);
            }
        }

    }

    private void registrarse (){
        Intent regi = new Intent(this, registros.class);
        startActivity(regi);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==SIGN_IN_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            goMainScreen();
        }else {
            Toast.makeText(this, "no se pudo iniciar secion", Toast.LENGTH_SHORT).show();
        }
    }

    private void goMainScreen() {
        Intent intent = new Intent(this,menu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
