package com.sanbar.gestor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.sanbar.gestor.R.color.colorOrangeSelected;
import static com.sanbar.gestor.R.color.colorOrangeUnselected;

public class PersonasDetalleActivity extends AppCompatActivity {

    Sesion session;
    String posSelected;
    JSONObject workerSelected;
    String workerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personas_detalle);
        try {
            Intent intent = getIntent();

            posSelected = getIntent().getStringExtra("itemPosition");
            session = intent.getParcelableExtra("SESSION");

            JSONArray workers = new JSONArray(session.getWorkers());

            workerSelected = workers.getJSONObject(Integer.valueOf(posSelected));
            workerId = workerSelected.getString("Id");


            TextView tv_nombre = (TextView) findViewById(R.id.textview_personas_detalle_nombre);
            TextView tv_cargo = (TextView) findViewById(R.id.textview_personas_detalle_cargo);
            TextView tv_tipo = (TextView) findViewById(R.id.textview_personas_detalle_status);

            tv_nombre.setText(workerSelected.getString("Name"));
            tv_cargo.setText(workerSelected.getString("Categoria"));

            if (workerSelected.getBoolean("IsActivo")){
                tv_tipo.setText("Activo");
            }else {
                tv_tipo.setText("No activo");
            }

            session.attemptWorkersWorkerId(workerId);
            String workersWorkerIdRequested = session.getWorkersWorkerId();
            JSONObject auxObj = new JSONObject(workersWorkerIdRequested);

            TextView tv_informacion_escalable = (TextView) findViewById(R.id.textview_personas_detalle_informacion_escalable);
            tv_informacion_escalable.setText("");

            tv_informacion_escalable.append("Ciudad de origen"+"\n");
            tv_informacion_escalable.append(auxObj.getString("CiudadOrigen")+"\n\n");

            tv_informacion_escalable.append("Región"+"\n");
            tv_informacion_escalable.append(auxObj.getString("Region")+"\n\n");

            tv_informacion_escalable.append("Código de contrato"+"\n");
            tv_informacion_escalable.append(auxObj.getString("CodigoContrato")+"\n\n");

            tv_informacion_escalable.append("Nombre de contrato"+"\n");
            tv_informacion_escalable.append(auxObj.getString("NombreContrato")+"\n\n");

            tv_informacion_escalable.append("Turno de contrado"+"\n");
            tv_informacion_escalable.append(auxObj.getString("TurnoContratoName")+"\n\n");

            tv_informacion_escalable.append("Especialidad"+"\n");
            tv_informacion_escalable.append(auxObj.getString("Especialidad")+"\n\n");

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
