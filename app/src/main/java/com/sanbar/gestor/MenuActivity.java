package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sanbar.gestor.ui.login.LoginActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            String loginModelData1 = extras.getString("loginModelData1");//model.getDisplayName()
        }

        configureButtonPersonas();
        configureButtonMaquinas();
        configureButtonPod();
        configureButtonContrato();
        configureButtonAtras();

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
                Intent myIntent = new Intent(MenuActivity.this, PersonasActivity.class);
                myIntent.putExtra("loginModelData1", "dato"); //Optional parameters
                startActivity(myIntent);

            }
        });
    }

    private void configureButtonMaquinas() {

        Button btn_maquinas = (Button) findViewById(R.id.button_menu_maquinas);
        btn_maquinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuActivity.this, MaquinasActivity.class);
                myIntent.putExtra("loginModelData1", "dato"); //Optional parameters
                startActivity(myIntent);

            }
        });
    }

    private void configureButtonPod() {

        Button btn_pod = (Button) findViewById(R.id.button_menu_plan_de_obra_diaria);
        btn_pod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuActivity.this, PodActivity.class);
                myIntent.putExtra("loginModelData1", "dato"); //Optional parameters
                startActivity(myIntent);

            }
        });
    }

    private void configureButtonContrato() {

        Button btn_pod = (Button) findViewById(R.id.button_menu_cambiar_contrato);
        btn_pod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MenuActivity.this, ContratoActivity.class);
                myIntent.putExtra("loginModelData1", "dato"); //Optional parameters
                startActivity(myIntent);

            }
        });
    }



}
