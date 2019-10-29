package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

public class PodInterrupcionResponsableActivity extends AppCompatActivity {

    private Sesion session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_interrupcion_responsable);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");

        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureSwitch();
        configureButtonBack();
        configureButtonConfirmar();

    }


    private void configureSwitch() {
        final LinearLayout ll_lista = (LinearLayout)findViewById(R.id.linearlayout_pod_interrupcion_responsable_lista);

        Switch sw_complete = (Switch) findViewById(R.id.switch_pod_interrupcion_responsable);

        sw_complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked){
                    ll_lista.setVisibility(View.GONE);
                } else {
                    ll_lista.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_pod_interrupcion_responsable_back);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void configureButtonConfirmar() {

        Button btn_atras = (Button) findViewById(R.id.button_pod_interrupcion_responsable_confirmar);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Devolver a menu",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
