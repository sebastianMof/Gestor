package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.INTEGER;

public class ContratoActivity extends AppCompatActivity {

    List<String> ContractArray = new ArrayList<String>();
    Sesion session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrato);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureButtonBack();
        getContractList();
        configureContratosList();

    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_contrato_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void getContractList() {
        Boolean aux = session.attemptContracts();
        if (aux){
            try {
                JSONArray contracts = new JSONArray(session.getContracts());
                JSONObject auxObj;
                for (int i = 0; i < contracts.length(); i++) {
                    auxObj = contracts.getJSONObject(i);
                    ContractArray.add(auxObj.getString("Name"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ContractArray.add("No hay contratos");
        }
    }

    private void configureContratosList(){

        ListView lista;
        lista = (ListView) findViewById(R.id.listview_contrato);

        ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ContractArray);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

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


}
