package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MaquinasActivity extends AppCompatActivity {

    private ArrayList<String> tiposList;
    private String tipoSelected;
    private EditText et_nombre;
    private String nombreFilter;

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

    private String[] ubicacionArray = {
            "Ubicación 1",
            "Ubicación 2",
            "Ubicación 3",
            "Ubicación 4",
            "Ubicación 5",
            "Ubicación 6"
    };

    private Integer[] imageArray = {
            R.drawable.imagen_maquina,
            R.drawable.imagen_maquina,
            R.drawable.imagen_maquina,
            R.drawable.imagen_maquina,
            R.drawable.imagen_maquina,
            R.drawable.imagen_maquina

    };

    private Integer[] pieChartArray = {
            25,
            25,
            50,
            75,
            100,
            100

    };

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maquinas);

        configureEditTextNombre();
        configureButtonBack();
        configureButtonFilter();

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
                //Toast.makeText(MaquinasActivity.this,"selected "+(String) adapterView.getItemAtPosition(position),Toast.LENGTH_LONG).show();
                tipoSelected= (String) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void configureItemList(){
        //Lo que se pasa acá aparecerá en la lista
        CustomListAdapterMaquinas list_adapter = new CustomListAdapterMaquinas(this, nameArray, codigoInternoArray, marcaArray, modeloArray, patenteArray, statusArray, ubicacionArray, imageArray,pieChartArray);

        listView = (ListView) findViewById(R.id.listview_maquinas);
        listView.setAdapter((ListAdapter) list_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                Intent intent = new Intent(MaquinasActivity.this, MaquinasDetalleActivity.class);
                String message = nameArray[position];
                intent.putExtra("item", message);
                startActivity(intent);
            }
        });

    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_maquinas_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void configureEditTextNombre() {
        et_nombre= (EditText) findViewById(R.id.edittext_maquinas_busqueda);
    }

    private void configureButtonFilter() {
        Button btn_filter = (Button) findViewById(R.id.button_maquinas_busqueda);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            nombreFilter = String.valueOf(et_nombre.getText());
            Toast.makeText(getApplicationContext(),
                    "Filtro con\n" +
                            "Tipo: "+tipoSelected+"\n"+
                            "Nombre: "+nombreFilter,
                    Toast.LENGTH_SHORT).show();

            }
        });
    }

}
