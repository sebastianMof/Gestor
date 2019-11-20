package com.sanbar.gestor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PodInterrupcionCausaActivity extends AppCompatActivity {

    private Sesion session;
    private String tareaId;
    private String horaInicio;

    private ArrayList<String> causasList;
    private ArrayList<String> causasIdList;

    private ArrayList<String> causasHijoList;
    private ArrayList<String> causasHijoIdList;

    private String causaSeleccionada="Causa Seleccionada";
    private String causaSeleccionadaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_interrupcion_causa);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
            tareaId = getIntent().getStringExtra("tareaId");
            horaInicio = getIntent().getStringExtra("horaInicio");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        getCausasList();
        configureCausasListview();

        configureButtonConfirmar();
        configureTextviewCausa();
    }

    private void getCausasList() {
        causasList = new ArrayList<>();
        causasIdList = new ArrayList<>();

        session.attemptCausasInmediatas();
        JSONArray CausasInmediatas = null;

        try {
            CausasInmediatas = new JSONArray(session.getCausasInmediatas());

            JSONObject auxObj;
            for (int i = 0; i < CausasInmediatas.length(); i++) {

                auxObj = CausasInmediatas.getJSONObject(i);
                causasList.add(auxObj.getString("Nombre"));
                causasIdList.add(auxObj.getString("Id"));
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    private void configureCausasListview() {

        ListView listView;
        listView = (ListView) findViewById(R.id.listview_pod_causas);

        ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,causasList);

        listView.setAdapter(adaptador);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                causaSeleccionadaId=causasIdList.get(position);
                causaSeleccionada=causasList.get(position);
                configureTextviewCausa();

                getCausasHijoList(causasIdList.get(position));
                configureCausasHijoListview();

            }
        });


    }

    private void getCausasHijoList(String causaId) {
        causasHijoList = new ArrayList<>();
        causasHijoIdList = new ArrayList<>();

        session.attemptCausasInmediatasCausaId(causaId);

        try {
            JSONObject CausasInmediatasCausaId = new JSONObject(session.getCausasInmediatasCausaId());
            JSONArray auxArray = CausasInmediatasCausaId.getJSONArray("CausasHijo");
            JSONObject auxObj;

            for (int i = 0; i < auxArray.length(); i++) {
                auxObj=auxArray.getJSONObject(i);
                causasHijoList.add(auxObj.getString("Name"));
                causasHijoIdList.add(auxObj.getString("Id"));
            }

            Log.e("TEST",causasHijoList.toString());


        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    private void configureCausasHijoListview() {

        ListView listView;
        listView = (ListView) findViewById(R.id.listview_pod_causas);

        ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,causasHijoList);

        listView.setAdapter(adaptador);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                causaSeleccionadaId=causasHijoIdList.get(position);
                causaSeleccionada=causasHijoList.get(position);
                configureTextviewCausa();

                getCausasHijoList(causasHijoIdList.get(position));
                configureCausasHijoListview();


            }
        });


    }

    private void configureTextviewCausa() {
        TextView tv_causa = (TextView) findViewById(R.id.textview_pod_causa_seleccionada);
        tv_causa.setText(causaSeleccionada);
    }

    private void configureButtonConfirmar() {
        Button btn_confirmar = (Button) findViewById(R.id.button_pod_causas_confirmar);
        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(PodInterrupcionCausaActivity.this, PodInterrupcionTerminoActivity.class);

                myIntent.putExtra("SESSION", session);
                myIntent.putExtra("tareaId", tareaId);
                myIntent.putExtra("causaId", causaSeleccionadaId);
                myIntent.putExtra("horaInicio", horaInicio);

                startActivityForResult(myIntent,2);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2) {//INTERRUMPIR tarea
            if(resultCode == Activity.RESULT_OK){
                setResult(Activity.RESULT_OK);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        }
        if (requestCode == 1) {//TERMINAR tarea
            if(resultCode == Activity.RESULT_OK){
                setResult(Activity.RESULT_OK);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),"Acción no concretada",Toast.LENGTH_SHORT).show();
            }
        }



    }//onActivityResult

}