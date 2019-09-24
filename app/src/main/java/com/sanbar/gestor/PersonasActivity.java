package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class PersonasActivity extends AppCompatActivity {

    private ArrayList<String> tiposList;
    private String tipoSelected;
    private EditText et_nombre_rut;
    private String nombreFilter;


    private String[] nameArray = {
            "Nombre 1",
            "Nombre 2",
            "Nombre 3",
            "Nombre 4",
            "Nombre 5",
            "Nombre 6" };

    private String[] cargoArray = {
            "Cargo 1",
            "Cargo 2",
            "Cargo 3",
            "Cargo 4",
            "Cargo 5",
            "Cargo 6"
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

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personas);

        configureEditTextNombre();

        configureTiposList();
        configureSpinnerTipos();

        configureItemList();

        configureButtonBack();
        configureButtonFilter();



    }

    private void configureEditTextNombre() {
        et_nombre_rut= (EditText) findViewById(R.id.edittext_personas_nombre_rut);

    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_personas_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void configureButtonFilter() {

        Button btn_atras = (Button) findViewById(R.id.button_personas_busqueda);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombreFilter = String.valueOf(et_nombre_rut.getText());
                Toast.makeText(getApplicationContext(),"Filtro con tipo: "+tipoSelected+"y nombre: "+nombreFilter,Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void configureTiposList() {
        tiposList = new ArrayList<>();
        String[] items = new String[]{"TIPO", "TIPO_1", "TIPO_2", "TIPO_3"};

        tiposList.addAll(Arrays.asList(items));

    }

    private void configureSpinnerTipos() {
        Spinner spn_bodega = (Spinner) findViewById(R.id.spinner_personas_tipo);

        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(PersonasActivity.this, R.layout.spinner_item, tiposList){

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
                tipoSelected= (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void configureItemList(){
        //Lo que se pasa acá aparecerá en la lista
        CustomListAdapterPersonas list_adapter = new CustomListAdapterPersonas(this, nameArray, cargoArray, statusArray, imageArray);

        listView = (ListView) findViewById(R.id.listview_personas);
        listView.setAdapter(list_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                Intent intent = new Intent(PersonasActivity.this, PersonasDetalleActivity.class);
                String message = nameArray[position];
                intent.putExtra("item", message);
                startActivity(intent);
            }
        });

    }

}
