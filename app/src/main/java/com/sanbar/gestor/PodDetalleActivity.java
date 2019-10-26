package com.sanbar.gestor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PodDetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_detalle);

        String savedExtra = getIntent().getStringExtra("item");
        TextView myText = (TextView) findViewById(R.id.textview_pod_detalle_ito);
        myText.setText(savedExtra);

        configureButtonBack();
        configureButtonIniciar();
        configureButtonInterrupcion();

        LinearLayout ll_pod_detalle_tarea = (LinearLayout)findViewById(R.id.linearlayout_pod_detalle_tarea);

        ll_pod_detalle_tarea.setBackground(getResources().getDrawable(R.drawable.rounded_button_green));

    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_pod_detalle_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void configureButtonIniciar() {

        Button btn_fin_tarea = (Button) findViewById(R.id.button_pod_detalle_iniciar);
        btn_fin_tarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PodDetalleActivity.this, PodDetalleFinalizarTareaActivity.class);
                startActivity(myIntent);

            }
        });
    }


    private void configureButtonInterrupcion() {

        Button btn_fin_tarea = (Button) findViewById(R.id.button_pod_detalle_interrupcion);
        btn_fin_tarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PodDetalleActivity.this, MenuActivity.class);
                startActivity(myIntent);

            }
        });
    }

}
