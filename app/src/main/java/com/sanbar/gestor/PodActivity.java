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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PodActivity extends AppCompatActivity {

    private Sesion session;

    private String nombreFilter;

    private String[] nameArray = {
            "Nombre Capataz 1",
            "Nombre Capataz 2",
            "Nombre Capataz 3",
            "Nombre Capataz 4",
            "Nombre Capataz 5",
            "Nombre Capataz 6" };

    private String[] statusArray = {
            "Status 1",
            "Status 2",
            "Status 3",
            "Status 4",
            "Status 5",
            "Status 6"
    };

    private String[] ituArray = {
            "ITU 1",
            "ITU 2",
            "ITU 3",
            "ITU 4",
            "ITU 5",
            "ITU 6"
    };

    private String[] especialidadArray = {
            "Especialidad 1",
            "Especialidad 2",
            "Especialidad 3",
            "Especialidad 4",
            "Especialidad 5",
            "Especialidad 6"
    };

    private ListView listView;

    private ArrayList<String> especialidadList;
    private ArrayList<String> especialidadIdList;

    private ArrayList<String> areaList;
    private ArrayList<String> statusList;

    private EditText et_nombre;
    private String especialidadSelected;
    private String areaSelected;
    private String statusSelected;

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
        configureItemList();

        configureButtonFilter();


        configureEspecialidadList();
        configureAreaList();
        configureStatusList();

        configureSpinnerEspecialidad();
        configureSpinnerArea();
        configureSpinnerStatus();

    }

    private void configureItemList(){
        //Lo que se pasa acá aparecerá en la lista
        CustomListAdapterPod list_adapter = new CustomListAdapterPod(this, nameArray, statusArray, ituArray,especialidadArray);

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

        session.attemptEspecialidades();

        especialidadList = new ArrayList<>();
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
                especialidadSelected= (String) adapterView.getItemAtPosition(position);
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
                areaSelected= (String) adapterView.getItemAtPosition(position);
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
                statusSelected= (String) adapterView.getItemAtPosition(position);
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
                nombreFilter = String.valueOf(et_nombre.getText());
                Toast.makeText(getApplicationContext(),
                        "Filtro con \n" +
                                "Capataz:"+nombreFilter+"\n"+
                                "Especialidad: "+ especialidadSelected+"\n"+
                                "Area: "+ areaSelected+"\n"+
                                "Status: "+ statusSelected,
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void configureEditTextNombre() {
        et_nombre= (EditText) findViewById(R.id.edittext_pod_busqueda);
    }

    private void configureItemData(){
        try {
            JSONArray workers = new JSONArray(session.getWorkers());
            JSONObject auxObj;

            List<String> nameList = new ArrayList<String>();
            List<String> categoriaList = new ArrayList<String>();
            List<String> statusList = new ArrayList<String>();
            List<Integer> imageList = new ArrayList<Integer>();

            for (int i = 0; i < workers.length(); i++) {
                auxObj=workers.getJSONObject(i);
                nameList.add(auxObj.getString("Name"));
                categoriaList.add(auxObj.getString("Categoria"));
                if (auxObj.getString("IsActivo").equals("true")){
                    statusList.add("Activo");
                }else {
                    statusList.add("No activo");
                }
                imageList.add(R.drawable.imagen);
            }

            nameArray = new String[workers.length()];

            statusArray = new String[workers.length()];


            nameArray = nameList.toArray(nameArray);

            statusArray = statusList.toArray(statusArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
