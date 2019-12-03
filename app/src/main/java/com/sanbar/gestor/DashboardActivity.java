package com.sanbar.gestor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

//----------------------

public class DashboardActivity extends AppCompatActivity {

    private Sesion session;

    private String currentDate;

    private ArrayList<String> xList;
    private ArrayList<String> yList;

    private String[] xLabels;

    private ArrayList<JSONObject> noIniciadas;
    private ArrayList<JSONObject> atrasadas;
    private ArrayList<JSONObject> iniciadas;
    private ArrayList<JSONObject> interrumpidas;
    private ArrayList<JSONObject> terminadas;

    private String xSelected;
    private String ySelected;

    //-----
    private int maxImgX=0;
    private int maxImgY=0;


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

        //configureDate();
        //getLayoutData();
        //configureImageView();

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

    public DataPoint[] data(ArrayList<JSONObject> listDataset){
        int n= listDataset.size();
        DataPoint[] values = new DataPoint[n];
        for(int i=0;i<n;i++){
            JSONObject auxObj = listDataset.get(i);
            DataPoint v = null;
            try {
                v = new DataPoint(auxObj.getDouble("x"),auxObj.getDouble("y"));
                values[i] = v;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return values;
    }

    private void getLayoutData() {

        //session.attemptLayouts(currentDate);
        session.attemptLayouts("2019-11-18");

    }

/*
    public void configureImageView(){

        ImageView iv_layout = (ImageView) findViewById(R.id.imageview_layout);
        TextView tv_layout = (TextView) findViewById(R.id.textview_layout);
        tv_layout.setText(R.string.informaci_n_layout);
        tv_layout.append("\n");

        String layouts = session.getLayouts();

        try {
            JSONArray layoutsArray = new JSONArray(layouts);
            JSONObject auxObj;
            for (int i =0; i<layoutsArray.length();i++){
                auxObj=layoutsArray.getJSONObject(i);
                //auxObj.getInt("Id");
                tv_layout.append(auxObj.getString("Nombre")+"\n");

                auxObj.getJSONObject("Dimensiones");
                maxImgX=auxObj.getJSONObject("Dimensiones").getInt("X");
                maxImgY=auxObj.getJSONObject("Dimensiones").getInt("Y");
                tv_layout.append("Tamaño imagen: "+maxImgX+","+maxImgY+"\n");

                auxObj.getString("Foto");
                auxObj.getJSONArray("Areas");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        drawTareasOnImageView();

        iv_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    drawClickOnImageView(x,y);

                    Log.e("TEST","movil size:" + view.getWidth() +" - "+ view.getHeight() );
                    Log.e("TEST","full size:" + maxImgX +" - "+ maxImgY );

                    double scaleRatioX = (double)view.getWidth()/(double)maxImgX;
                    double scaleRatioY = (double)view.getHeight()/(double)maxImgY;

                    double scaleClickPosX=scaleRatioX*(double)x;
                    double scaleClickPosY=scaleRatioY*(double)y;

                    Log.e("TEST","movil click X:"+ Integer.toString(x) + " - movil click Y:"+Integer.toString(y));
                    Log.e("TEST","scaleRatioX:"+ scaleRatioX + " - scaleRatioY"+scaleRatioY);
                    Log.e("TEST","scaleClickPosX:"+ scaleClickPosX + " - scaleClickPosY"+scaleClickPosY);

                }
                return false;
            }
        });

    }

    private void drawClickOnImageView(int x,int y) {

        BitmapFactory.Options myOptions = new BitmapFactory.Options();

        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
        myOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.imagen_maquina_extendida,myOptions);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);

        Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas = new Canvas(mutableBitmap);
        canvas.drawCircle(x, y, 25, paint);

        ImageView imageView = (ImageView)findViewById(R.id.imageview_layout);
        imageView.setAdjustViewBounds(false);
        imageView.setImageBitmap(mutableBitmap);

    }

    private void drawTareasOnImageView() {

        BitmapFactory.Options myOptions = new BitmapFactory.Options();

        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
        myOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.imagen_maquina_extendida,myOptions);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas = new Canvas(mutableBitmap);
        canvas.drawCircle(120, 130, 25, paint);
        canvas.drawCircle(340, 220, 25, paint);
        canvas.drawCircle(70, 40, 25, paint);

        ImageView imageView = (ImageView)findViewById(R.id.imageview_layout);
        imageView.setAdjustViewBounds(false);
        imageView.setImageBitmap(mutableBitmap);

    }

*/

    private void configureWebView() {
        WebView webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        JSONObject Task = new JSONObject();
        String content = addTasks(Task);

        webView.loadDataWithBaseURL(
                "file:///android_asset/",
                content,
                "text/html",
                "utf-8",
                null);
    }

    public String addTasks(JSONObject Task){

        JSONArray Tasks = new JSONArray();//Tasks.lenght o size debe ser mayor a cero
        Task = new JSONObject();

        try {
            Task.put("PersonaName","Diego Riquelme");
            Task.put("TareaName","Tarea1");
            Task.put("TareaInicio","08:30");
            Task.put("TareaFin","12:30");
            //String currentString = "Fruit: they taste good";
            //String[] separated = currentString.split(":");
            //separated[0]; // this will contain "Fruit"
            //separated[1]; // this will contain " they taste good"
            Task.put("TareaColor","#589C36");

            Tasks.put(Task);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Task = new JSONObject();

        try {
            Task.put("PersonaName","Sebastián Mofré");
            Task.put("TareaName","Tarea2");
            Task.put("TareaInicio","14:30");
            Task.put("TareaFin","22:30");
            //String currentString = "Fruit: they taste good";
            //String[] separated = currentString.split(":");
            //separated[0]; // this will contain "Fruit"
            //separated[1]; // this will contain " they taste good"
            Task.put("TareaColor","#C1BC3F");

            Tasks.put(Task);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("TEST1",Tasks.toString());

        String start ="<html>\n" +
                "   <head>\n" +
                "      <script type = \"text/javascript\" src = \"https://www.gstatic.com/charts/loader.js\"></script>\n" +
                "      <script type = \"text/javascript\" src = \"https://www.google.com/jsapi\"></script>\n" +
                "      <script type = \"text/javascript\">\n" +
                "         google.charts.load('current', {packages: ['timeline']});     \n" +
                "      </script>\n" +
                "   </head>\n" +
                "   \n" +
                "   <body>\n" +
                "      <div id = \"container\" style = \"width: 550px; height: 400px; margin: 0 auto\">\n" +
                "      </div>\n" +
                "      <script language = \"JavaScript\">\n" +
                "         function drawChart() {\n" +
                "            // Define the chart to be drawn.\n" +
                "            var data = new google.visualization.DataTable();\n" +
                "            \n" ;

        String content =
                "            data.addColumn({ \n" +
                        "               type: 'string', id: 'Person'\n" +
                        "            });\n" +
                        "            \n" +
                        "            data.addColumn({ \n" +
                        "               type: 'string', id: 'Task'\n" +
                        "            });\n" +
                        "            \n" +
                        "            data.addColumn({ \n" +
                        "               type: 'date', id: 'Start' \n" +
                        "            });\n" +
                        "            \n" +
                        "            data.addColumn({ \n" +
                        "               type: 'date', id: 'End'\n" +
                        "            });\n" +
                        "            \n" +
                        "            data.addRows([\n";


        try {
            for (int i = 0; i<Tasks.length();i++){
                JSONObject auxObj = Tasks.getJSONObject(i);

                String TareaInicio = auxObj.getString("TareaInicio");
                String[] TareaInicioSeparada = TareaInicio.split(":");

                String horaInicio=TareaInicioSeparada[0];
                String minInicio=TareaInicioSeparada[1];

                String TareaFin = auxObj.getString("TareaFin");
                String[] TareaFinSeparada = TareaFin.split(":");

                String horaFin=TareaFinSeparada[0];
                String minFin=TareaFinSeparada[1];

                content +=
                        "               [ '"+ auxObj.getString("PersonaName")+"','  "+ auxObj.getString("TareaName") +" ', new Date(0,0,0,"+horaInicio+","+minInicio+",0),  new Date(0,0,0,"+horaFin+","+minFin+",0) ],\n";

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        content+=
                "            ]);\n" +
                        "\n" +
                        "            var options = {      \n" +
                        "               width: '100%', \n" +
                        "               height: '100%',\n" +
                        "               colors: [" ;

        try {
            if (Tasks.length()==1){

                JSONObject auxObj = Tasks.getJSONObject(0);
                content+=
                        "'"+auxObj.getString("TareaColor")+"'";
            } else {

                for (int i = 0; i<Tasks.length();i++){
                    JSONObject auxObj = Tasks.getJSONObject(i);
                    content+=
                            "'"+auxObj.getString("TareaColor")+"'";
                    if (i!=Tasks.length()-1)
                        content+= ",";
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        content+=
                "               ]\n" +
                        "            };\n" ;

        String end =
                "                  \n" +
                        "            // Instantiate and draw the chart.\n" +
                        "            var chart = new google.visualization.Timeline(document.getElementById('container'));\n" +
                        "            chart.draw(data, options);\n" +
                        "         }\n" +
                        "         google.charts.setOnLoadCallback(drawChart);\n" +
                        "      </script>\n" +
                        "   </body>\n" +
                        "</html>";

        String timelineChart = start+content+end;

        Log.e("TEST",timelineChart);
        return timelineChart;

    }



}
