package com.sanbar.gestor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sanbar.gestor.ui.login.LoginActivity;

public class MenuActivity extends AppCompatActivity {

    private Sesion session;
    private TextView nombreContratista;
    private TextView nombreContrato;
    private TextView codigoContrato;
    private TextView codigoApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureHeader(session);

        configureButtonPersonas();
        configureButtonMaquinas();
        configureButtonPod();
        configureButtonContrato();
        configureButtonAtras();

    }

    @Override
    public void onResume(){
        super.onResume();

        final LinearLayout ll_progressBar = (LinearLayout) findViewById(R.id.linearlayout_menu_progressbar);
        final LinearLayout ll_activity = (LinearLayout) findViewById(R.id.linearlayout_menu_activity);
        ll_progressBar.setVisibility(View.GONE);
        ll_activity.setVisibility(View.VISIBLE);
    }


    private void configureHeader(Sesion session){
        nombreContratista = (TextView) findViewById(R.id.textview_menu_nombre_contratista);
        nombreContrato = (TextView) findViewById(R.id.textview_menu_nombre_contrato);
        codigoContrato = (TextView) findViewById(R.id.textview_menu_codigo_contrato);
        codigoApi = (TextView) findViewById(R.id.textview_menu_codigo_api);

        nombreContratista.setText(session.getFullNameComputed());
        nombreContrato.setText(session.getSelectedContractName());
        codigoContrato.setText(session.getContractCode());
        codigoApi.setText(session.getApiCode());

    }

    private void configureButtonAtras() {

        Button btn_atras = (Button) findViewById(R.id.button_menu_cerrar_sesion);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(myIntent);
                finish();

            }
        });
    }

    private void configureButtonPersonas() {

        Button btn_personas = (Button) findViewById(R.id.button_menu_personas);
        btn_personas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLinearLayout();
                Intent myIntent = new Intent(MenuActivity.this, PersonasActivity.class);
                myIntent.putExtra("SESSION", session); //Optional parameters
                startActivity(myIntent);
            }
        });
    }

    private void configureButtonMaquinas() {

        Button btn_maquinas = (Button) findViewById(R.id.button_menu_maquinas);
        btn_maquinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLinearLayout();
                Intent myIntent = new Intent(MenuActivity.this, MaquinasActivity.class);
                myIntent.putExtra("SESSION", session); //Optional parameters
                startActivity(myIntent);
            }
        });
    }

    private void configureButtonPod() {

        Button btn_pod = (Button) findViewById(R.id.button_menu_plan_de_obra_diaria);
        btn_pod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLinearLayout();
                Intent myIntent = new Intent(MenuActivity.this, PodActivity.class);
                myIntent.putExtra("SESSION", session); //Optional parameters
                startActivity(myIntent);

            }
        });
    }

    private void configureButtonContrato() {

        Button btn_pod = (Button) findViewById(R.id.button_menu_cambiar_contrato);
        btn_pod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLinearLayout();
                Intent myIntent = new Intent(MenuActivity.this, ContratoActivity.class);
                myIntent.putExtra("SESSION", session); //Optional parameters
                startActivityForResult(myIntent,1); // 1 is code for contracto

            }
        });
    }

    public void changeLinearLayout(){

        final LinearLayout ll_progressBar = (LinearLayout) findViewById(R.id.linearlayout_menu_progressbar);
        final LinearLayout ll_activity = (LinearLayout) findViewById(R.id.linearlayout_menu_activity);

        if (ll_progressBar.getVisibility()==View.GONE){
            ll_activity.setVisibility(View.GONE);
            ll_progressBar.setVisibility(View.VISIBLE);
        } else if (ll_progressBar.getVisibility()==View.VISIBLE){
            ll_progressBar.setVisibility(View.GONE);
            ll_activity.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {

            case (1) : {//1 is for contratos
                if (resultCode == Activity.RESULT_OK) {
                    Intent intent = getIntent();
                    session = intent.getParcelableExtra("SESSION");
                    session.attemptContratistas();
                    configureHeader(session);
                }
                break;
            }
        }
    }
}