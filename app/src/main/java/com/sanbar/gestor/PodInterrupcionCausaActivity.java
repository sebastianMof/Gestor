package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PodInterrupcionCausaActivity extends AppCompatActivity {

    private Sesion session;

    private ArrayList<String> causasList;
    private ArrayList<String> causasIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_interrupcion_causa);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureCausasList();
        configureSpinnerTipos();

    }

    private void configureCausasList() {
        causasList = new ArrayList<>();
        causasIdList = new ArrayList<>();

        causasList.add("CAUSAS");

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
                //tipoSelected= (String) adapterView.getItemAtPosition(position);
                //tipoSelected= String.valueOf(position);

                if (position!=0){
                    Intent myIntent = new Intent(PodInterrupcionCausaActivity.this, PodInterrupcionTerminoActivity.class);
                    myIntent.putExtra("SESSION", session);
                    startActivity(myIntent);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}
