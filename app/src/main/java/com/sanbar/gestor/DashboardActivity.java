package com.sanbar.gestor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

//----------------------

public class  DashboardActivity extends AppCompatActivity {

    private Sesion session;

    private String currentDate;
    private String filterSelected= "Specialty";
    private ArrayList<String> filterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureButtonBack();

        configureDate();
        configureFilterList();
        configureSpinnerFilter();

        configureWebView();

    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_dashboard_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void configureDate() {
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    private void configureWebView() {
        WebView webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        String content = addTasks();

        webView.loadDataWithBaseURL(
                "file:///android_asset/",
                content,
                "text/html",
                "utf-8",
                null);
    }

    public String addTasks(){

        boolean call = session.attemptPodSummary(currentDate,filterSelected);

        if (call){
            JSONObject auxObj=null;
            JSONArray Tasks=null;

            try {
                auxObj = new JSONObject(session.getPodSummary());
                Tasks = auxObj.getJSONArray("rows");
                if (Tasks.length()==0){
                    Toast.makeText(getApplicationContext(),"No hay tareas que mostrar en dashboard.",Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String start ="<html>\n" +
                    "\n" +
                    "  <head>\n" +
                    "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\n" +
                    "\n" +
                    "    <script type=\"text/javascript\">\n" +
                    "      google.charts.load('current', {'packages':['timeline']});\n" +
                    "      google.charts.setOnLoadCallback(drawChart);\n" +
                    "\n" +
                    "      function drawChart() {\n" +
                    "        var container = document.getElementById('timeline-tooltip');\n" +
                    "        var chart = new google.visualization.Timeline(container);\n" +
                    "        var dataTable = new google.visualization.DataTable();\n" +
                    "\n";
            String content = "dataTable.addColumn({ type: 'string', id: 'Rol' });\n" +
                    "        dataTable.addColumn({ type: 'string', id: 'Nombre' });\n" +
                    "        dataTable.addColumn({ type: 'string', role: 'tooltip', p: {'html': true} });\n" +
                    "        dataTable.addColumn({ type: 'date', id: 'Start' });\n" +
                    "        dataTable.addColumn({ type: 'date', id: 'End' });\n" +
                    "\n" +
                    "        dataTable.addRows([";


            try {

                for (int i = 0; i<Tasks.length();i++){
                    JSONArray Task = Tasks.getJSONArray(i);

                    String TareaInicio = Task.getString(4);
                    String[] TareaInicioSeparada = TareaInicio.split(":");

                    String horaInicio=TareaInicioSeparada[0];
                    String minInicio=TareaInicioSeparada[1];

                    String TareaFin = Task.getString(5);
                    String[] TareaFinSeparada = TareaFin.split(":");

                    String horaFin=TareaFinSeparada[0];
                    String minFin=TareaFinSeparada[1];

                    content +=
                            "[ '"+ Task.getString(0)+"','"+ Task.getString(1) +"','"+  Task.getString(2) + "', new Date(0,0,0,"+horaInicio+","+minInicio+",0),  new Date(0,0,0,"+horaFin+","+minFin+",0) ],\n";

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            content+=
                    "            ]);\n" +
                            "\n" +
                            "            var options = {      \n" +
                            "               tooltip: { isHtml: true }, \n" +
                            "               width: '100%', \n" +
                            "               height: '100%',\n" +
                            "               colors: [" ;

            try {
                if (Tasks.length()==1){

                    JSONArray Task = Tasks.getJSONArray(0);
                    content+=
                            "'"+Task.getString(3)+"'";
                } else {

                    for (int i = 0; i<Tasks.length();i++){
                        JSONArray Task = Tasks.getJSONArray(i);
                        content+=
                                "'"+Task.getString(3)+"'";
                        if (i!=Tasks.length()-1)
                            content+= ",";
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            content+=
                    "]\n" +
                            "            };\n" ;

            String end ="        chart.draw(dataTable, options);\n" +
                    "      }\n" +
                    "    </script>\n" +
                    "\n" +
                    "  </head>\n" +
                    "\n" +
                    "  <body>\n" +
                    "\n" +
                    "    <div id=\"timeline-tooltip\" style = \"width: 550px; height: 400px; margin: 0 auto\"></div>\n" +
                    "\n" +
                    "  </body>\n" +
                    "\n" +
                    "</html>";

            String timelineChart = start+content+end;

            return timelineChart;
        }
        return null;

    }

    private void configureFilterList() {
        filterList = new ArrayList<>();

        filterList.add("Especialidad");
        filterList.add("Area");
        filterList.add("Cuadrilla");

    }

    private void configureSpinnerFilter() {
        Spinner spn_bodega = (Spinner) findViewById(R.id.spinner_dashboard);

        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(DashboardActivity.this, R.layout.spinner_item, filterList){

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
                if (position==0){
                    filterSelected= "Specialty";
                } else if (position==1){
                    filterSelected= "Area";
                } else if (position==2){
                    filterSelected= "Crew";
                }
                configureWebView();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filterSelected="Specialty";
            }
        });

    }



}
