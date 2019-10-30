package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class PodFinalizarTareaActivity extends AppCompatActivity {

    private Sesion session;

    private String tareaId;
    private String horaObtenida;
    private String cantidad;

    private boolean cantidadCompletada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_finalizar_tarea);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
            tareaId = getIntent().getStringExtra("tareaId");
            horaObtenida = getIntent().getStringExtra("horaObtenida");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        TextView avance = findViewById(R.id.textview_pod_detalle_finalizar_tarea_avance);
        try {
            JSONObject auxObj = new JSONObject(session.getTareasTareaId());
            avance.setText(" / "+auxObj.getString("CantidadPlanificada"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        configureButtonBack();
        configureSwitch();
        configureButtonFinalizarTarea();
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

        final EditText et_cantidad = findViewById(R.id.edittext_pod_detalle_finalizar_tarea_avance);
        Button btn_atras = (Button) findViewById(R.id.button_pod_detalle_finalizar_tarea_confirmar);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cantidadReal="";

                if (cantidadCompletada){
                    try {
                        JSONObject auxObj = new JSONObject(session.getTareasTareaId());
                        cantidadReal=auxObj.getString("CantidadPlanificada");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    cantidadReal= et_cantidad.getText().toString();
                }

                boolean finalizada = session.attemptFinalizarTarea(tareaId,horaObtenida,cantidadReal);

                Intent returnIntent = new Intent();
                if (finalizada){
                    setResult(Activity.RESULT_OK,returnIntent);
                } else{
                    setResult(Activity.RESULT_CANCELED,returnIntent);
                }
                finish();


            }
        });
    }

    private void configureSwitch() {
        final LinearLayout ll_finalizar_tarea = (LinearLayout)findViewById(R.id.linearlayout_pod_detalle_finalizar_tarea);

        Switch sw_complete = (Switch) findViewById(R.id.switch_pod_detalle_finalizar_tarea);
        sw_complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cantidadCompletada = isChecked;
                if (isChecked){
                    ll_finalizar_tarea.setVisibility(View.GONE);
                } else {
                    ll_finalizar_tarea.setVisibility(View.VISIBLE);
                }

            }
        });
    }
}
