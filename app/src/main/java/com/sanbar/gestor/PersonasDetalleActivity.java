package com.sanbar.gestor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import static com.sanbar.gestor.R.color.colorOrangeSelected;
import static com.sanbar.gestor.R.color.colorOrangeUnselected;

public class PersonasDetalleActivity extends AppCompatActivity {

    Sesion session;
    String workerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personas_detalle);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
            workerId = getIntent().getStringExtra("workerId");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureButtonBack();
        configureTabs();
        configureData();

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

    private void configureData(){


        session.attemptWorkersWorkerId(workerId);

        TextView nombre = (TextView) findViewById(R.id.textview_personas_detalle_nombre);
        TextView categoria = (TextView) findViewById(R.id.textview_personas_detalle_categoria);
        TextView status = (TextView) findViewById(R.id.textview_personas_detalle_status);
        TextView capacitados = (TextView) findViewById(R.id.textview_personas_detalle_capacitados);
        TextView informacionEscalable = (TextView) findViewById(R.id.textview_personas_detalle_informacion_escalable);


        try {
            JSONObject worker = new JSONObject(session.getWorkersWorkerId());

            nombre.setText(worker.getString("Name"));
            categoria.setText(worker.getString("Categoria"));
            if (worker.getBoolean("IsActivo")){
                status.setText("Activo");
            } else {
                status.setText("No activo");
            }

            JSONArray equiposArray = worker.getJSONArray("Equipos");
            JSONArray auxEquiposArray;
            JSONObject auxEquiposObj;

            if (equiposArray.length()!=0){
                capacitados.setText("Capacitado para: \n");
            } else{
                capacitados.setText("");
            }


            for (int i = 0; i< equiposArray.length(); i++){
                auxEquiposArray = equiposArray.getJSONArray(i);

                for (int j = 0; j < auxEquiposArray.length(); j++){
                    auxEquiposObj = auxEquiposArray.getJSONObject(j);
                    capacitados.append(auxEquiposObj.getString("value")+"\n");
                }
                capacitados.append("\n");
            }

            JSONArray infEscalable = worker.getJSONArray("InformacionEscalable");
            JSONObject auxInfObj;
            informacionEscalable.setText("");
            for (int i = 0; i< infEscalable.length();i++){
                auxInfObj = infEscalable.getJSONObject(i);
                informacionEscalable.append(auxInfObj.getString("name") + ": "+ "\n"+auxInfObj.getString("value") + "\n");
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
