package com.sanbar.gestor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.IntegerRes;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContratoActivity extends AppCompatActivity {

    List<String> ContractNameList = new ArrayList<String>();
    List<String> ContractCodeList = new ArrayList<String>();
    Sesion session;

    private String[] nameArray;
    private String[] codeArray;

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
                    ContractNameList.add(auxObj.getString("Name"));
                    ContractCodeList.add(auxObj.getString("Code"));
                }

                nameArray = new String[contracts.length()];
                codeArray = new String[contracts.length()];

                nameArray = ContractNameList.toArray(nameArray);
                codeArray = ContractCodeList.toArray(codeArray);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            ContractNameList.add("No hay contratos");
            ContractCodeList.add("");

            nameArray = new String[1];
            codeArray = new String[1];

            nameArray = ContractNameList.toArray(nameArray);
            codeArray = ContractCodeList.toArray(codeArray);

        }

    }

    private void configureContratosList(){

        //Lo que se pasa acá aparecerá en la lista
        CustomListAdapterContrato list_adapter = new CustomListAdapterContrato(this, nameArray, codeArray);

        ListView listView;

        listView = (ListView) findViewById(R.id.listview_contrato);
        listView.setAdapter(list_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                try {
                    JSONArray contracts = new JSONArray(session.getContracts());
                    JSONObject auxObj;

                    auxObj = contracts.getJSONObject(position);

                    session.setSelectedContractId(auxObj.getString("Id"));
                    session.setSelectedContractName("Name");

                    session.attemptCambioContrato();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Intent resultIntent = new Intent();
                resultIntent.putExtra("SESSION", session);
                setResult(Activity.RESULT_OK, resultIntent);

                finish();

            }
        });

    }


}
