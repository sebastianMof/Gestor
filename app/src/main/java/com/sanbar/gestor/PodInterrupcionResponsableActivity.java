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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
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

    List<String> ResponsablesArray = new ArrayList<String>();

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

        configureEditText();
        getResponsablesList();
        configureResponsablesList();
        configureSwitch();
        configureButtonBack();
        configureButtonConfirmar();

    }

    private void configureEditText() {
        final EditText et_responsable = findViewById(R.id.edittext_pod_interrupcion_responsable);

        et_responsable.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                Log.e("TEST",et_responsable.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void getResponsablesList() {

        Boolean aux = session.attemptContracts();
        if (aux){
            try {
                JSONArray contracts = new JSONArray(session.getContracts());
                JSONObject auxObj;
                for (int i = 0; i < contracts.length(); i++) {
                    auxObj = contracts.getJSONObject(i);
                    ResponsablesArray.add(auxObj.getString("Name"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ResponsablesArray.add("No hay responsables disponibles");
        }
    }

    private void configureResponsablesList(){

        ListView lv_responsables = (ListView) findViewById(R.id.listview_pod_interrupcion_responsable);

        ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ResponsablesArray);
        lv_responsables.setAdapter(adaptador);

        lv_responsables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                responsableId = ResponsablesArray.get(position-1);
                try {
                    JSONArray contracts = new JSONArray(session.getContracts());
                    JSONObject auxObj;

                    auxObj = contracts.getJSONObject(position);

                    session.setSelectedContractId(auxObj.getString("Id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                session.attemptCambioContrato();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("SESSION", session);
                setResult(Activity.RESULT_OK, resultIntent);

                finish();

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
                } else {
                    //LISTA
                    responsableId=session.getUserId();
                }
                //boolean interrupcion = session.attemptInterrupciones(tareaId,responsableId,causaId,horaInicio,horaTerminoEstimada);
                Log.e("TEST",tareaId+"-"+responsableId+"-"+causaId+"-"+horaInicio+"-"+horaTerminoEstimada);
                Toast.makeText(getApplicationContext(),tareaId+responsableId+causaId+horaInicio+horaTerminoEstimada,Toast.LENGTH_SHORT).show();
            }
        });
    }


}
