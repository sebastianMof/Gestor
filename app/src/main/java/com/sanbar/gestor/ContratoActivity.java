package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ContratoActivity extends AppCompatActivity {

    List<String> ContractArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrato);
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
        ContractArray.add("Contrato 1");
        ContractArray.add("Contrato 2");
        ContractArray.add("Contrato 3");
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

                // do something with ContractArray[position];

            }
        });
    }


}
