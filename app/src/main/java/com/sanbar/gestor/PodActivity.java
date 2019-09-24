package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class PodActivity extends AppCompatActivity {

    private String[] nameArray = {
            "Nombre 1",
            "Nombre 2",
            "Nombre 3",
            "Nombre 4",
            "Nombre 5",
            "Nombre 6" };

    private String[] contratistaArray = {
            "Contratista 1",
            "Contratista 2",
            "Contratista 3",
            "Contratista 4",
            "Contratista 5",
            "Contratista 6"
    };

    private String[] contratoArray = {
            "Contrato 1",
            "Contrato 2",
            "Contrato 3",
            "Contrato 4",
            "Contrato 5",
            "Contrato 6"
    };

    private String[] ituArray = {
            "ITU 1",
            "ITU 2",
            "ITU 3",
            "ITU 4",
            "ITU 5",
            "ITU 6"
    };

    private ListView listView;

    private ArrayList<String> especialidadList;
    private ArrayList<String> areaList;
    private ArrayList<String> statusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod);

        configureItemList();

        configureEspecialidadList();
        configureAreaList();
        configureStatusList();

        configureSpinnerEspecialidad();
        configureSpinnerArea();
        configureSpinnerStatus();

    }

    private void configureItemList(){
        //Lo que se pasa acá aparecerá en la lista
        CustomListAdapterPod list_adapter = new CustomListAdapterPod(this, nameArray, contratistaArray, contratoArray, ituArray);

        listView = (ListView) findViewById(R.id.listview_pod);
        listView.setAdapter(list_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(PodActivity.this, PodDetalleActivity.class);
                String message = nameArray[position];
                intent.putExtra("item", message);
                startActivity(intent);
            }
        });

    }

    private void configureEspecialidadList() {
        especialidadList = new ArrayList<>();
        String[] items = new String[]{"ESPECIALIDAD", "ESPECIALIDAD_1", "ESPECIALIDAD_2", "ESPECIALIDAD_3"};

        especialidadList.addAll(Arrays.asList(items));

    }
    private void configureAreaList() {
        areaList = new ArrayList<>();
        String[] items = new String[]{"AREA", "AREA_1", "AREA_2", "AREA_3"};

        areaList.addAll(Arrays.asList(items));

    }
    private void configureStatusList() {
        statusList = new ArrayList<>();
        String[] items = new String[]{"STATUS", "STATUS_1", "STATUS_2", "STATUS_3"};

        statusList.addAll(Arrays.asList(items));

    }

    private void configureSpinnerEspecialidad() {
        Spinner spn_bodega = (Spinner) findViewById(R.id.spinner_pod_especialidad);

        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(PodActivity.this, R.layout.spinner_item, especialidadList){

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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void configureSpinnerArea() {
        Spinner spn_bodega = (Spinner) findViewById(R.id.spinner_pod_area);

        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(PodActivity.this, R.layout.spinner_item, areaList){

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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void configureSpinnerStatus() {
        Spinner spn_bodega = (Spinner) findViewById(R.id.spinner_pod_status);

        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(PodActivity.this, R.layout.spinner_item, statusList){

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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}
