package com.sanbar.gestor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PodActivity extends AppCompatActivity {

    private Sesion session;

    private String[] nameArray;
    private String[] statusArray;
    private String[] especialidadArray;
    private String[] colorArray;

    private ArrayList<String> especialidadList;
    private ArrayList<String> especialidadIdList;
    private ArrayList<String> areaList;
    private ArrayList<String> areaIdList;
    private ArrayList<String> statusList;
    private ArrayList<String> statusIdList;

    private ArrayList<String> tareaIdList;

    private EditText et_nombre;
    private ListView listView;

    private String nombreFilter;
    private String especialidadSelected;
    private String areaSelected;
    private String statusSelected;

    private int onCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureEditTextNombre();
        configureButtonBack();

        configureItemData();
        configureItemList();

        configureButtonFilter();

        configureEspecialidadList();
        configureAreaList();
        configureStatusList();

        configureSpinnerEspecialidad();
        configureSpinnerArea();
        configureSpinnerStatus();

        onCreate=0;

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (onCreate>0){
            configureEditTextNombre();
            configureButtonBack();

            configureItemData();
            configureItemList();

            configureButtonFilter();

            configureEspecialidadList();
            configureAreaList();
            configureStatusList();

            configureSpinnerEspecialidad();
            configureSpinnerArea();
            configureSpinnerStatus();
        }
        onCreate++;

    }



    private void configureItemList(){
        //Lo que se pasa acá aparecerá en la lista
        CustomListAdapterPod list_adapter = new CustomListAdapterPod(this, nameArray, statusArray,especialidadArray,colorArray);

        listView = (ListView) findViewById(R.id.listview_pod);
        listView.setAdapter(list_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(PodActivity.this, PodDetalleActivity.class);
                intent.putExtra("tareaId", tareaIdList.get(position));
                intent.putExtra("SESSION", session);
                startActivity(intent);

            }
        });

    }

    private void configureEspecialidadList() {

        session.attemptEspecialidades();

        especialidadList = new ArrayList<>();
        especialidadList.add("ESPECIALIDAD");
        especialidadIdList = new ArrayList<>();

        try {
            JSONArray especialidades = new JSONArray(session.getEspecialidades());
            JSONObject auxObj;
            for (int i = 0; i < especialidades.length(); i++) {
                auxObj = especialidades.getJSONObject(i);
                especialidadList.add(auxObj.getString("Name"));
                especialidadIdList.add(auxObj.getString("Id"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void configureAreaList() {

        session.attemptAreaContratos();

        areaList = new ArrayList<>();
        areaList.add("AREA");
        areaIdList = new ArrayList<>();

        try {
            JSONArray areas = new JSONArray(session.getAreaContratos());
            JSONObject auxObj;
            for (int i = 0; i < areas.length(); i++) {
                auxObj = areas.getJSONObject(i);
                areaList.add(auxObj.getString("Name"));
                areaIdList.add(auxObj.getString("Id"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void configureStatusList() {

        session.attemptTareaStatus();

        statusList = new ArrayList<>();
        statusList.add("ESTADO");
        statusIdList = new ArrayList<>();

        try {
            JSONArray tareaStatus = new JSONArray(session.getTareaStatus());
            JSONObject auxObj;
            for (int i = 0; i < tareaStatus.length(); i++) {
                auxObj = tareaStatus.getJSONObject(i);
                statusList.add(auxObj.getString("Name"));
                statusIdList.add(auxObj.getString("Id"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


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
                if (position!=0){
                    especialidadSelected = especialidadIdList.get(position-1);
                } else {
                    especialidadSelected = null;
                }


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
                if (position!=0){
                    areaSelected= areaIdList.get(position-1);
                } else {
                    areaSelected = null;
                }

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
                if (position!=0){
                    statusSelected= statusIdList.get(position-1);
                } else {
                    statusSelected = null;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_pod_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void configureButtonFilter() {
        Button btn_filter = (Button) findViewById(R.id.button_pod_busqueda);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!et_nombre.getText().equals("")){
                    nombreFilter = String.valueOf(et_nombre.getText());
                } else {
                    nombreFilter=null;
                }

                session.attemptTareas(nombreFilter, especialidadSelected, areaSelected, statusSelected);

                configureItemData();
                configureItemList();

            }
        });
    }

    private void configureEditTextNombre() {
        et_nombre= (EditText) findViewById(R.id.edittext_pod_busqueda);
    }

    private void configureItemData(){

        session.attemptTareas(null,null,null,null);

        try {
            JSONArray tareas = new JSONArray(session.getTareas());
            JSONObject auxObj;

            List<String> nameList = new ArrayList<String>();
            List<String> statusList = new ArrayList<String>();
            List<String> especialidadList = new ArrayList<String>();
            List<String> colorList = new ArrayList<String>();
            tareaIdList = new ArrayList<String>();

            for (int i = 0; i < tareas.length(); i++) {
                auxObj=tareas.getJSONObject(i);

                tareaIdList.add(auxObj.getString("Id"));
                nameList.add(auxObj.getString("Name"));
                statusList.add(auxObj.getString("TareaStatusName"));
                especialidadList.add(auxObj.getString("EspecialidadName"));
                colorList.add(auxObj.getString("Color"));

            }

            nameArray = new String[tareas.length()];
            statusArray = new String[tareas.length()];
            especialidadArray = new String[tareas.length()];
            colorArray = new String[tareas.length()];

            nameArray = nameList.toArray(nameArray);
            statusArray = statusList.toArray(statusArray);
            especialidadArray = especialidadList.toArray(especialidadArray);
            colorArray = colorList.toArray(colorArray);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
