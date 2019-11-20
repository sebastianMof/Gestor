package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PodInterrupcionResponsableActivity extends AppCompatActivity {

    private Sesion session;
    private String tareaId;
    private String horaInicio;
    private String causaId;
    private String horaTerminoEstimada;
    private String responsableId;

    private boolean userResponsable;

    List<String> ResponsablesList = new ArrayList<String>();
    List<String> ResponsablesIdList = new ArrayList<String>();

    TextView tv_responsable;
    private AutoCompleteTextView atv_responsable;
    private String[] responsables;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_interrupcion_responsable);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
            tareaId = getIntent().getStringExtra("tareaId");
            horaInicio = getIntent().getStringExtra("horaInicio");
            causaId = getIntent().getStringExtra("causaId");
            horaTerminoEstimada = getIntent().getStringExtra("horaTerminoEstimada");

        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        getResponsablesList();
        configureResponsablesList();
        configureSwitch();
        configureButtonBack();
        configureButtonConfirmar();

        configureAutoTextView();
        configureTextView();

    }

    private void configureTextView() {
        tv_responsable = findViewById(R.id.textview_pod_interrupcion_responsable);
        tv_responsable.setText("-");
    }

    private void configureAutoTextView() {
        atv_responsable = findViewById(R.id.autoTextView_pod_interrupcion_responsable);

        responsables = new String[ResponsablesList.size()];
        responsables = ResponsablesList.toArray(responsables);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, responsables);
        atv_responsable.setThreshold(1); //will start working from first character
        atv_responsable.setAdapter(adapter);

        atv_responsable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Object item = parent.getItemAtPosition(position);
                responsableId = ResponsablesIdList.get(position);
                tv_responsable.setText(ResponsablesList.get(position));
                Toast.makeText(getApplicationContext(),"Responsable seleccionado: "+ResponsablesList.get(position),Toast.LENGTH_LONG).show();

            }
        });


    }

    private void getResponsablesList() {

        Boolean aux = session.attemptResponsables();
        if (aux){
            try {
                JSONArray responsables = new JSONArray(session.getResponsables());
                JSONObject auxObj;
                for (int i = 0; i < responsables.length(); i++) {
                    auxObj = responsables.getJSONObject(i);
                    ResponsablesList.add(auxObj.getString("FullNameComputed"));
                    ResponsablesIdList.add(auxObj.getString("Id"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ResponsablesList.add("No hay responsables disponibles");
        }
    }

    private void configureResponsablesList(){

        ListView lv_responsables = (ListView) findViewById(R.id.listview_pod_interrupcion_responsable);

        ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ResponsablesList);
        lv_responsables.setAdapter(adaptador);

        lv_responsables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                responsableId = ResponsablesIdList.get(position);
                tv_responsable.setText(ResponsablesList.get(position));
                Toast.makeText(getApplicationContext(),"Responsable seleccionado: "+ResponsablesList.get(position),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void configureSwitch() {
        final LinearLayout ll_lista = (LinearLayout)findViewById(R.id.linearlayout_pod_interrupcion_responsable_lista);

        Switch sw_complete = (Switch) findViewById(R.id.switch_pod_interrupcion_responsable);

        sw_complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                userResponsable=isChecked;

                if (isChecked){
                    ll_lista.setVisibility(View.GONE);
                } else {
                    ll_lista.setVisibility(View.VISIBLE);
                    configureTextView();
                }

            }
        });
    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_pod_interrupcion_responsable_back);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void configureButtonConfirmar() {

        Button btn_atras = (Button) findViewById(R.id.button_pod_interrupcion_responsable_confirmar);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userResponsable){
                    responsableId=session.getUserId();
                }

                boolean interrupcion = session.attemptInterrupciones(tareaId,responsableId,causaId,horaInicio,horaTerminoEstimada);

                if (interrupcion){
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }

            }
        });
    }


}
