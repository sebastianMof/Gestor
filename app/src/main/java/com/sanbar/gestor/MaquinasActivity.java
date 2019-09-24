package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MaquinasActivity extends AppCompatActivity {

    private ArrayList<String> tiposList;

    private String[] nameArray = {
            "Nombre 1",
            "Nombre 2",
            "Nombre 3",
            "Nombre 4",
            "Nombre 5",
            "Nombre 6" };

    private String[] codigoInternoArray = {
            "Código Interno 1",
            "Código Interno 2",
            "Código Interno 3",
            "Código Interno 4",
            "Código Interno 5",
            "Código Interno 6"
    };

    private String[] marcaArray = {
            "Marca 1",
            "Marca 2",
            "Marca 3",
            "Marca 4",
            "Marca 5",
            "Marca 6"
    };

    private String[] modeloArray = {
            "Modelo 1",
            "Modelo 2",
            "Modelo 3",
            "Modelo 4",
            "Modelo 5",
            "Modelo 6"
    };
    private String[] patenteArray = {
            "Patente 1",
            "Patente 2",
            "Patente 3",
            "Patente 4",
            "Patente 5",
            "Patente 6"
    };
    private String[] statusArray = {
            "Status 1",
            "Status 2",
            "Status 3",
            "Status 4",
            "Status 5",
            "Status 6"
    };

    private Integer[] imageArray = {
            R.drawable.imagen,
            R.drawable.imagen,
            R.drawable.imagen,
            R.drawable.imagen,
            R.drawable.imagen,
            R.drawable.imagen

    };

    private Integer[] pieChartArray = {
            R.drawable.imagen,
            R.drawable.imagen,
            R.drawable.imagen,
            R.drawable.imagen,
            R.drawable.imagen,
            R.drawable.imagen

    };

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maquinas);

        configureTiposList();
        configureSpinnerTipos();

        configureItemList();

    }


    private void configureTiposList() {
        tiposList = new ArrayList<>();
        String[] items = new String[]{"TIPO", "TIPO_1", "TIPO_2", "TIPO_3"};

        tiposList.addAll(Arrays.asList(items));

    }

    private void configureSpinnerTipos() {
        Spinner spn_bodega = (Spinner) findViewById(R.id.spinner_maquinas_tipo);

        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(MaquinasActivity.this, R.layout.spinner_item, tiposList){

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.colorBlue));

                return view;
            }
        };
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_bodega.setAdapter(spnAdapter);

        spn_bodega.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MaquinasActivity.this,"selected "+(String) adapterView.getItemAtPosition(position),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void configureItemList(){
        //Lo que se pasa acá aparecerá en la lista
        CustomListAdapterMaquinas list_adapter = new CustomListAdapterMaquinas(this, nameArray, codigoInternoArray, marcaArray, modeloArray, patenteArray, statusArray, imageArray,pieChartArray);


        listView = (ListView) findViewById(R.id.listview_maquinas);
        listView.setAdapter((ListAdapter) list_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                //Intent intent = new Intent(MaquinasActivity.this, MaquinasDetalleActivity.class);
                String message = nameArray[position];
                //intent.putExtra("item", message);
                //startActivity(intent);
            }
        });

    }

}
