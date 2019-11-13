package com.sanbar.gestor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
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

            String sourceString = "";

            nombre.setText("");
            sourceString = "<b>" + "Nombre: " + "</b> ";
            nombre.append(Html.fromHtml(sourceString));
            if (worker.isNull("Name")){
                nombre.append("\n" + "No definido");
            } else {
                nombre.append("\n" +worker.getString("Name"));
            }

            categoria.setText("");
            sourceString = "<b>" + "Categoria: " + "</b> ";
            categoria.append(Html.fromHtml(sourceString));
            if (worker.isNull("Categoria")){
                categoria.append("\n" + "No definido");
            } else {
                categoria.append("\n" +worker.getString("Categoria"));
            }

            status.setText("");
            sourceString = "<b>" + "Estado: " + "</b> ";
            status.append(Html.fromHtml(sourceString));
            if (worker.getBoolean("IsActivo")){
                status.append("\n" + "Activo");
            } else {
                status.append("\n" + "No activo");
            }

            JSONArray equiposArray = worker.getJSONArray("Equipos");
            JSONArray auxEquiposArray;
            JSONObject auxEquiposObj;

            capacitados.setText("");
            if (equiposArray.length()!=0){
                sourceString = "<b>" + "Capacitado para: " + "</b> ";
                capacitados.append(Html.fromHtml(sourceString));
                capacitados.append("\n");
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
            sourceString = "";

            for (int i = 0; i< infEscalable.length();i++){
                auxInfObj = infEscalable.getJSONObject(i);
                sourceString = "<b>" + auxInfObj.getString("name") + ": " + "</b> ";
                informacionEscalable.append(Html.fromHtml(sourceString));
                informacionEscalable.append("\n" +auxInfObj.getString("value") + "\n");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
