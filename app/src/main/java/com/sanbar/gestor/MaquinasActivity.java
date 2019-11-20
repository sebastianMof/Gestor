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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
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

public class MaquinasActivity extends AppCompatActivity {

    private Sesion session;

    private ArrayList<String> tiposList;
    private ArrayList<String> tiposIdList;
    private ArrayList<String> equiposIdList;

    private String tipoSelected;
    private EditText et_nombre;
    private String nombreFilter;

    private String[] nameArray;
    private String[] codeArray;
    private String[] marcaArray;
    private String[] modeloArray;
    private String[] patenteArray;
    private String[] isActivoArray;
    private String[] ubicacionArray;
    private Integer[] imageArray;
    //R.drawable.imagen_maquina
    private Integer[] pieChartArray;
    //25 50 75 100

    private ListView listView;

    private boolean filtersHided = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maquinas);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureEditTextNombre();

        configureTiposList();
        configureSpinnerTipos();

        session.attemptEquipos(null,null);

        configureItemData();
        configureItemList();

        configureButtonBack();
        configureButtonFilter();
        configureButtonFilterSearch();
    }

    private void configureButtonFilter() {
        Button btn_filter = (Button) findViewById(R.id.button_maquinas_filter);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout filters = (LinearLayout) findViewById(R.id.linearlayout_maquinas_filters);

                if (filtersHided){
                    filters.setVisibility(View.VISIBLE);
                    filtersHided=true;
                } else {
                    filters.setVisibility(View.GONE);
                    filtersHided=true;
                }


            }
        });
    }


    private void configureTiposList() {
        tiposList = new ArrayList<>();
        tiposIdList = new ArrayList<>();

        tiposList.add("TIPO DE EQUIPO");

        session.attemptTipoEquipos();
        JSONArray tipoEquipos = null;

        try {
            tipoEquipos = new JSONArray(session.getTipoEquipos());

            JSONObject auxObj;
            for (int i = 0; i < tipoEquipos.length(); i++) {

                auxObj = tipoEquipos.getJSONObject(i);
                tiposList.add(auxObj.getString("Name"));
                tiposIdList.add(auxObj.getString("Id"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                tipoSelected= String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    private void configureButtonFilterSearch() {
        Button btn_filter_search = (Button) findViewById(R.id.button_maquinas_busqueda);
        btn_filter_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = et_nombre.getText().toString();
                if (!nombre.equals("")){
                    nombreFilter = nombre;
                } else {
                    nombreFilter=null;
                }

                String tipoFilter;
                if (!tipoSelected.equals("0")){
                    tipoFilter = tiposIdList.get(Integer.valueOf(tipoSelected)-1);

                } else {
                    tipoFilter=null;
                }

                session.attemptEquipos(nombreFilter,tipoFilter);

                configureItemData();
                configureItemList();

            }
        });
    }

    private void configureItemData(){
        try {
            JSONArray equipos = new JSONArray(session.getEquipos());
            JSONObject auxObj;

            equiposIdList = new ArrayList<>();

            List<String> nameList = new ArrayList<String>();
            List<String> codeList = new ArrayList<String>();
            List<String> marcaList = new ArrayList<String>();
            List<String> modeloList = new ArrayList<String>();
            List<String> patenteList = new ArrayList<String>();
            List<String> isActivoList = new ArrayList<String>();
            List<String> ubicacionList = new ArrayList<String>();
            List<Integer> combustibleList = new ArrayList<Integer>();

            List<Integer> imageList = new ArrayList<Integer>();

            for (int i = 0; i < equipos.length(); i++) {
                auxObj=equipos.getJSONObject(i);

                equiposIdList.add(String.valueOf(auxObj.getInt("Id")));

                nameList.add(auxObj.getString("Nombre"));
                codeList.add(auxObj.getString("Código"));
                marcaList.add(auxObj.getString("Marca"));
                modeloList.add(auxObj.getString("Modelo"));
                patenteList.add(auxObj.getString("Patente"));
                ubicacionList.add(auxObj.getString("UltimaUbicación"));
                if (auxObj.getString("EsActivo").equals("true")){
                    isActivoList.add("Activo");
                }else {
                    isActivoList.add("No activo");
                }
                Double combustibleDouble = auxObj.getDouble("PorcentajeCombustible");
                Integer combustible = combustibleDouble.intValue();
                combustibleList.add(combustible);
                imageList.add(R.drawable.imagen);
            }

            nameArray = new String[equipos.length()];
            codeArray = new String[equipos.length()];
            marcaArray = new String[equipos.length()];
            modeloArray = new String[equipos.length()];
            patenteArray = new String[equipos.length()];
            isActivoArray = new String[equipos.length()];
            ubicacionArray = new String[equipos.length()];
            imageArray = new Integer[equipos.length()];
            //R.drawable.imagen_maquina
            pieChartArray = new Integer[equipos.length()];
            //25 50 75 100

            nameArray = nameList.toArray(nameArray);
            codeArray = codeList.toArray(codeArray);
            marcaArray = marcaList.toArray(marcaArray);
            modeloArray = modeloList.toArray(modeloArray);
            patenteArray = patenteList.toArray(patenteArray);
            isActivoArray = isActivoList.toArray(isActivoArray);
            ubicacionArray = ubicacionList.toArray(ubicacionArray);
            imageArray = imageList.toArray(imageArray);
            pieChartArray = combustibleList.toArray(pieChartArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void configureItemList(){
        //Lo que se pasa acá aparecerá en la lista
        CustomListAdapterMaquinas list_adapter = new CustomListAdapterMaquinas(this, nameArray, codeArray, marcaArray, modeloArray, patenteArray, isActivoArray, ubicacionArray, imageArray, pieChartArray);

        listView = (ListView) findViewById(R.id.listview_maquinas);
        listView.setAdapter((ListAdapter) list_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                Intent intent = new Intent(MaquinasActivity.this, MaquinasDetalleActivity.class);
                intent.putExtra("SESSION", session);
                intent.putExtra("equipoId", equiposIdList.get(position));
                startActivity(intent);

            }
        });

    }

}
