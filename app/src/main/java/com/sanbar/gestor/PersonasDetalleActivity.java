package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.sanbar.gestor.R.color.colorOrangeSelected;
import static com.sanbar.gestor.R.color.colorOrangeUnselected;

public class PersonasDetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personas_detalle);

        String savedExtra = getIntent().getStringExtra("item");
        TextView myText = (TextView) findViewById(R.id.textview_personas_detalle_nombre);
        myText.setText(savedExtra);

        configureButtonBack();
        configureTabs();

    }

    private void configureTabs() {
        final LinearLayout ll_informacion = (LinearLayout) findViewById(R.id.linearlayout_personas_detalle_informacion);
        final LinearLayout ll_documentos = (LinearLayout) findViewById(R.id.linearlayout_personas_detalle_documentos);

        final LinearLayout ll_informacion_view = (LinearLayout) findViewById(R.id.linearlayout_personas_detalle_informacion_view);
        final LinearLayout ll_documentos_view = (LinearLayout) findViewById(R.id.linearlayout_personas_detalle_documentos_view);

        ll_informacion.setBackgroundColor(getResources().getColor(colorOrangeSelected));
        ll_documentos.setBackgroundColor(getResources().getColor(colorOrangeUnselected));
        ll_informacion_view.setVisibility(View.VISIBLE);
        ll_documentos_view.setVisibility(View.GONE);

        ll_informacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_informacion.setBackgroundColor(getResources().getColor(colorOrangeSelected));
                ll_documentos.setBackgroundColor(getResources().getColor(colorOrangeUnselected));
                ll_informacion_view.setVisibility(View.VISIBLE);
                ll_documentos_view.setVisibility(View.GONE);
            }
        });

        ll_documentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_informacion.setBackgroundColor(getResources().getColor(colorOrangeUnselected));
                ll_documentos.setBackgroundColor(getResources().getColor(colorOrangeSelected));
                ll_informacion_view.setVisibility(View.GONE);
                ll_documentos_view.setVisibility(View.VISIBLE);
            }
        });


    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_personas_detalle_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}
