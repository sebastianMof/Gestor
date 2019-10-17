package com.sanbar.gestor;

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

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PersonasActivity extends AppCompatActivity {

    private Sesion session;

    private ArrayList<String> tiposList;
    private ArrayList<String> categoriaIdList;
    private ArrayList<String> userIdList;

    private String tipoSelected;
    private EditText et_nombre_rut;
    private String nombreFilter;

    private String[] nameArray;
    private String[] cargoArray;
    private String[] statusArray;
    private Integer[] imageArray;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personas);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureEditTextNombre();

        configureTiposList();
        configureSpinnerTipos();

        session.attemptWorkers(null,null);

        configureItemData();
        configureItemList();

        configureButtonBack();
        configureButtonFilter();

    }

    private void configureEditTextNombre() {
        et_nombre_rut= (EditText) findViewById(R.id.edittext_personas_busqueda);

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

            String nombre_rut = et_nombre_rut.getText().toString();
            if (!nombre_rut.equals("")){
                nombreFilter = nombre_rut;
            } else {
                nombreFilter=null;
            }

            String categoriaFilter;
            if (!tipoSelected.equals("0")){
                categoriaFilter = categoriaIdList.get(Integer.valueOf(tipoSelected)-1);

            } else {
                categoriaFilter=null;
            }

            session.attemptWorkers(nombreFilter,categoriaFilter);

            configureItemData();
            configureItemList();

            }
        });
    }

    private void configureTiposList() {
        tiposList = new ArrayList<>();
        categoriaIdList = new ArrayList<>();

        tiposList.add("CATEGORIA");

        session.attemptWorkerCategorias();
        JSONArray workerCategorias = null;

        try {
            workerCategorias = new JSONArray(session.getWorkerCategorias());

            JSONObject auxObj;
            for (int i = 0; i < workerCategorias.length(); i++) {

                auxObj = workerCategorias.getJSONObject(i);
                tiposList.add(auxObj.getString("Name"));
                categoriaIdList.add(auxObj.getString("Id"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                //tipoSelected= (String) adapterView.getItemAtPosition(position);
                tipoSelected= String.valueOf(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
            cargoArray = new String[workers.length()];
            statusArray = new String[workers.length()];
            imageArray = new Integer[workers.length()];

            nameArray = nameList.toArray(nameArray);
            cargoArray = categoriaList.toArray(cargoArray);
            statusArray = statusList.toArray(statusArray);
            imageArray = imageList.toArray(imageArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

                intent.putExtra("itemPosition", String.valueOf(position));
                intent.putExtra("SESSION", session);
                startActivity(intent);
            }
        });

    }

}
