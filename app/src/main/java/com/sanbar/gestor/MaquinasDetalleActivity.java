package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class MaquinasDetalleActivity extends AppCompatActivity {

    Sesion session;
    String equipoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maquinas_detalle);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
            equipoId = getIntent().getStringExtra("equipoId");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureButtonBack();
        configureTabs();
        configureData();

    }


    private void configureData(){

        session.attemptEquiposEquipoId(equipoId);

        TextView nombre = (TextView) findViewById(R.id.textview_maquinas_detalle_nombre);
        TextView codigoInterno = (TextView) findViewById(R.id.textview_maquinas_detalle_codigo_interno);
        TextView marca = (TextView) findViewById(R.id.textview_maquinas_detalle_marca);
        TextView modelo = (TextView) findViewById(R.id.textview_maquinas_detalle_modelo);
        TextView patente = (TextView) findViewById(R.id.textview_maquinas_detalle_patente);
        TextView status = (TextView) findViewById(R.id.textview_maquinas_detalle_status);
        TextView ubicacion = (TextView) findViewById(R.id.textview_maquinas_detalle_ubicacion);

        TextView workers = (TextView) findViewById(R.id.textview_maquinas_detalle_workers);

        TextView informacionEscalable = (TextView) findViewById(R.id.textview_maquinas_detalle_informacion_escalable);

        PieChartView pieChartView = findViewById(R.id.piechart_maquinas_detalle);

        try {
            JSONObject equipo = new JSONObject(session.getEquiposEquipoId());

            nombre.setText(equipo.getString("Name"));
            codigoInterno.setText(equipo.getString("Code"));
            marca.setText(equipo.getString("Marca"));
            modelo.setText(equipo.getString("Modelo"));
            patente.setText(equipo.getString("Patente"));
            if (equipo.getBoolean("IsActivo")){
                status.setText("Activo");
            }else {
                status.setText("No activo");
            }
            //ubicacion.setText(equipo.getString("Ubicacion"));
            ubicacion.setText("Última ubicación");


            JSONArray workersArray = equipo.getJSONArray("Workers");
            JSONArray auxWorkerArray;
            JSONObject auxWorkerObj;
            workers.setText("Trabajadores capacitados: \n");

            for (int i = 0; i< workersArray.length(); i++){

                auxWorkerArray = workersArray.getJSONArray(i);
                for (int j = 0; j < auxWorkerArray.length(); j++){
                    auxWorkerObj = auxWorkerArray.getJSONObject(j);
                    workers.append(auxWorkerObj.getString("value")+"\n");
                }
            }

            JSONArray infEscalable = equipo.getJSONArray("InformacionEscalable");
            JSONObject auxInfObj;
            informacionEscalable.setText("");
            for (int i = 0; i< infEscalable.length();i++){
                auxInfObj = infEscalable.getJSONObject(i);
                informacionEscalable.append(auxInfObj.getString("name") + ": "+ "\n"+auxInfObj.getString("value") + "\n");
            }

            int combustible = equipo.getInt("Combustible");
            List<SliceValue> pieData = new ArrayList<>();
            pieData.add(new SliceValue((100-combustible), Color.rgb(235,169,119)));
            pieData.add(new SliceValue(combustible, Color.rgb(244,106,0)));
            PieChartData pieChartData = new PieChartData(pieData);
            pieChartData.setHasCenterCircle(true).setCenterText1(String.valueOf(combustible)).setCenterText1FontSize(20).setCenterText1Color(Color.rgb(244,106,0));
            pieChartView.setPieChartData(pieChartData);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void configureTabs() {
        final LinearLayout ll_informacion = (LinearLayout) findViewById(R.id.linearlayout_maquinas_detalle_informacion);
        final LinearLayout ll_documentos = (LinearLayout) findViewById(R.id.linearlayout_maquinas_detalle_documentos);

        final LinearLayout ll_informacion_view = (LinearLayout) findViewById(R.id.linearlayout_maquinas_detalle_informacion_view);
        final LinearLayout ll_documentos_view = (LinearLayout) findViewById(R.id.linearlayout_maquinas_detalle_documentos_view);

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

        Button btn_atras = (Button) findViewById(R.id.button_maquinas_detalle_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}
