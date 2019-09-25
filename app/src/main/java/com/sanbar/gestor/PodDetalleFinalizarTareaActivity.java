package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

public class PodDetalleFinalizarTareaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_detalle_finalizar_tarea);

        configureButtonBack();
        configureButtonFinalizarTarea();
        configureSwitch();

    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_pod_detalle_finalizar_tarea_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void configureButtonFinalizarTarea() {

        Button btn_atras = (Button) findViewById(R.id.button_pod_detalle_finalizar_tarea_confirmar);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void configureSwitch() {
        final LinearLayout ll_finalizar_tarea = (LinearLayout)findViewById(R.id.linearlayout_pod_detalle_finalizar_tarea);

        Switch sw_complete = (Switch) findViewById(R.id.switch_pod_detalle_finalizar_tarea);
        sw_complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked){
                    ll_finalizar_tarea.setVisibility(View.GONE);
                } else {
                    ll_finalizar_tarea.setVisibility(View.VISIBLE);
                }

            }
        });
    }
}
