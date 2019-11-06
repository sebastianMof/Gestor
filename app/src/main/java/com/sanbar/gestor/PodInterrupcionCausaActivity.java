package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PodInterrupcionCausaActivity extends AppCompatActivity {

    private Sesion session;
    private String tareaId;
    private String horaInicio;
    private String causaId;

    private ArrayList<String> causasList;
    private ArrayList<String> causasIdList;

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
        configureCausasList();

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
                causasList.add(auxObj.getString("Name"));
                causasIdList.add(auxObj.getString("Id"));
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    private void configureCausasList() {

        ListView listView;
        listView = (ListView) findViewById(R.id.listview_pod_causas);

        ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,causasList);

        listView.setAdapter(adaptador);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                Toast.makeText(getApplicationContext(),"testing "+position+":"+causasIdList.get(position),Toast.LENGTH_LONG);


            }
        });


    }



/*
    private void configureSpinnerTipos() {
        Spinner spn_causas = (Spinner) findViewById(R.id.spinner_pod_interrupcion_causa);

        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(PodInterrupcionCausaActivity.this, R.layout.spinner_item, causasList){

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.colorBlue));

                return view;
            }
        };
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_causas.setAdapter(spnAdapter);

        spn_causas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                if (position!=0){

                    causaId = causasIdList.get(position-1);

                    Intent myIntent = new Intent(PodInterrupcionCausaActivity.this, PodInterrupcionTerminoActivity.class);

                    myIntent.putExtra("SESSION", session);
                    myIntent.putExtra("tareaId", tareaId);
                    myIntent.putExtra("causaId", causaId);
                    myIntent.putExtra("horaInicio", horaInicio);

                    startActivityForResult(myIntent,2);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }*/

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
