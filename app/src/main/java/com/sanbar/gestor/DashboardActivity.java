package com.sanbar.gestor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


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

        configureDate();

        configureXList();
        configureSpinnerX();

        configureYList();
        configureSpinnerY();

        getPodSummaryData();
        plotPodSummaryData();

        getLayoutData();
        configureImageView();



    }

    private void configureDate() {
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    }

    private void getPodSummaryData() {

        boolean aux = session.attemptPodSummary("Specialty","InicioProgramado",currentDate);
        String podSummary = session.getPodSummary();

        //Labels for x axis
        try {
            JSONObject podSummaryObj = new JSONObject(podSummary);
            JSONArray labelsJSONArray = podSummaryObj.getJSONArray("labels");

            ArrayList<String> arrayListLabels = new ArrayList<>();
            arrayListLabels.add(" ");
            for (int i = 0; i < labelsJSONArray.length(); i++){
                arrayListLabels.add(labelsJSONArray.getString(i));
            }
            arrayListLabels.add(" ");
            xLabels= arrayListLabels.toArray(new String[0]);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //completationRate
        try {
            JSONObject podSummaryObj = new JSONObject(podSummary);

            TextView tv_completation_ratio = (TextView) findViewById(R.id.textview_layout_tareas_completadas);
            tv_completation_ratio.setText(R.string.tareas_completadas);
            String ratio = String.valueOf(podSummaryObj.getInt("completionRate"));
            tv_completation_ratio.append(ratio);
            tv_completation_ratio.append("%");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //datasets
        try {
            JSONObject podSummaryObj = new JSONObject(podSummary);
            JSONArray datasets = podSummaryObj.getJSONArray("datasets");
            noIniciadas=new ArrayList<JSONObject>();
            atrasadas=new ArrayList<JSONObject>();
            iniciadas=new ArrayList<JSONObject>();
            interrumpidas=new ArrayList<JSONObject>();
            terminadas=new ArrayList<JSONObject>();

            for (int i=0; i<datasets.length(); i++){
                JSONObject auxObj = datasets.getJSONObject(i);

                if (auxObj.getString("label").equals("No iniciadas") ){
                    JSONArray auxArray = auxObj.getJSONArray("data");
                    for (int j =0; j<auxArray.length();j++){
                        noIniciadas.add(auxArray.getJSONObject(j));
                    }

                }

                if (auxObj.getString("label").equals("Atrasada") ){
                    JSONArray auxArray = auxObj.getJSONArray("data");
                    for (int j =0; j<auxArray.length();j++){
                        atrasadas.add(auxArray.getJSONObject(j));
                    }

                }

                if (auxObj.getString("label").equals("Iniciada") ){
                    JSONArray auxArray = auxObj.getJSONArray("data");
                    for (int j =0; j<auxArray.length();j++){
                        iniciadas.add(auxArray.getJSONObject(j));
                    }

                }

                if (auxObj.getString("label").equals("Interrumpida") ){
                    JSONArray auxArray = auxObj.getJSONArray("data");
                    for (int j =0; j<auxArray.length();j++){
                        interrumpidas.add(auxArray.getJSONObject(j));
                    }

                }

                if (auxObj.getString("label").equals("Terminada") ){
                    JSONArray auxArray = auxObj.getJSONArray("data");
                    for (int j =0; j<auxArray.length();j++){
                        terminadas.add(auxArray.getJSONObject(j));
                    }

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getPodSummaryDataFiltered() {

        boolean aux = session.attemptPodSummary(xSelected,ySelected,currentDate);
        String podSummary = session.getPodSummary();

        //Labels for x axis
        try {
            JSONObject podSummaryObj = new JSONObject(podSummary);
            JSONArray labelsJSONArray = podSummaryObj.getJSONArray("labels");

            ArrayList<String> arrayListLabels = new ArrayList<>();
            arrayListLabels.add(" ");
            for (int i = 0; i < labelsJSONArray.length(); i++){
                arrayListLabels.add(labelsJSONArray.getString(i));
            }
            arrayListLabels.add(" ");
            xLabels= arrayListLabels.toArray(new String[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //completationRate
        try {
            JSONObject podSummaryObj = new JSONObject(podSummary);

            TextView tv_completation_ratio = (TextView) findViewById(R.id.textview_layout_tareas_completadas);
            tv_completation_ratio.setText(R.string.tareas_completadas);
            String ratio = String.valueOf(podSummaryObj.getInt("completionRate"));
            tv_completation_ratio.append(ratio);
            tv_completation_ratio.append("%");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //datasets
        try {
            JSONObject podSummaryObj = new JSONObject(podSummary);
            JSONArray datasets = podSummaryObj.getJSONArray("datasets");
            noIniciadas=new ArrayList<JSONObject>();
            atrasadas=new ArrayList<JSONObject>();
            iniciadas=new ArrayList<JSONObject>();
            interrumpidas=new ArrayList<JSONObject>();
            terminadas=new ArrayList<JSONObject>();

            for (int i=0; i<datasets.length(); i++){
                JSONObject auxObj = datasets.getJSONObject(i);

                if (auxObj.getString("label").equals("No iniciadas") ){
                    JSONArray auxArray = auxObj.getJSONArray("data");
                    for (int j =0; j<auxArray.length();j++){
                        noIniciadas.add(auxArray.getJSONObject(j));
                    }

                }

                if (auxObj.getString("label").equals("Atrasada") ){
                    JSONArray auxArray = auxObj.getJSONArray("data");
                    for (int j =0; j<auxArray.length();j++){
                        atrasadas.add(auxArray.getJSONObject(j));
                    }

                }

                if (auxObj.getString("label").equals("Iniciada") ){
                    JSONArray auxArray = auxObj.getJSONArray("data");
                    for (int j =0; j<auxArray.length();j++){
                        iniciadas.add(auxArray.getJSONObject(j));
                    }

                }

                if (auxObj.getString("label").equals("Interrumpida") ){
                    JSONArray auxArray = auxObj.getJSONArray("data");
                    for (int j =0; j<auxArray.length();j++){
                        interrumpidas.add(auxArray.getJSONObject(j));
                    }

                }

                if (auxObj.getString("label").equals("Terminada") ){
                    JSONArray auxArray = auxObj.getJSONArray("data");
                    for (int j =0; j<auxArray.length();j++){
                        terminadas.add(auxArray.getJSONObject(j));
                    }

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void plotPodSummaryData() {
        //x always > 0

        GraphView graph = (GraphView) findViewById(R.id.graph);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(24);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(xLabels.length-1);

        // enable scaling and scrolling
        //graph.getViewport().setScalable(true);
        //graph.getViewport().setScalableY(true);

        PointsGraphSeries<DataPoint> tareasNoIniciadas = new PointsGraphSeries<>(data(noIniciadas));
        graph.addSeries(tareasNoIniciadas);
        tareasNoIniciadas.setShape(PointsGraphSeries.Shape.POINT);
        tareasNoIniciadas.setColor(Color.parseColor("#4f4f4f"));

        PointsGraphSeries<DataPoint> tareasAtrasadas = new PointsGraphSeries<>(data(atrasadas));
        graph.addSeries(tareasAtrasadas);
        tareasAtrasadas.setShape(PointsGraphSeries.Shape.POINT);
        tareasAtrasadas.setColor(Color.parseColor("#f2f21e"));

        PointsGraphSeries<DataPoint> tareasIniciadas = new PointsGraphSeries<>(data(iniciadas));
        graph.addSeries(tareasIniciadas);
        tareasIniciadas.setShape(PointsGraphSeries.Shape.POINT);
        tareasIniciadas.setColor(Color.parseColor("#589c36"));

        PointsGraphSeries<DataPoint> tareasInterrumpidas = new PointsGraphSeries<>(data(interrumpidas));
        graph.addSeries(tareasInterrumpidas);
        tareasInterrumpidas.setShape(PointsGraphSeries.Shape.POINT);
        tareasInterrumpidas.setColor(Color.parseColor("#e35253"));

        PointsGraphSeries<DataPoint> tareasTerminadas = new PointsGraphSeries<>(data(terminadas));
        graph.addSeries(tareasTerminadas);
        tareasTerminadas.setShape(PointsGraphSeries.Shape.POINT);
        tareasTerminadas.setColor(Color.parseColor("#cccccc"));

        TextView tv_labels = (TextView) findViewById(R.id.textview_layout_graph_labels);
        tv_labels.setText("Número - Etiqueta\n");
        for (int i =1;i<xLabels.length-1;i++){
            tv_labels.append(Integer.toString(i)+" - "+xLabels[i]);
            tv_labels.append("\n");
        }

        // custom label formatter to show currency "EUR"
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show hour for y values
                    return super.formatLabel(value, isValueX) + ":00";
                }
            }
        });
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

    private void configureXList() {

        xList = new ArrayList<>();

        xList.add("Especialidad");
        xList.add("Area");
        xList.add("Cuadrilla");
        //Specialty, Area, Crew


    }

    private void configureYList() {

        yList = new ArrayList<>();

        yList.add("InicioProgramado");
        yList.add("InicioReal");
        yList.add("TerminoProgramado");
        yList.add("TerminoReal");
        //InicioProgramado, InicioReal, TerminoProgramado, TerminoReal


    }

    private void configureSpinnerX() {
        Spinner spn_x = (Spinner) findViewById(R.id.spinner_layout_x);

        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(DashboardActivity.this, R.layout.spinner_item, xList){

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.colorBlue));

                return view;
            }
        };
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_x.setAdapter(spnAdapter);

        spn_x.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (xList.get(position)) {
                    case "Especialidad":
                        xSelected = "Specialty";
                        break;
                    case "Area":
                        xSelected = "Area";
                        break;
                    case "Cuadrilla":
                        xSelected = "Crew";
                        break;
                }

                if (ySelected == null ||ySelected.equals("")){
                    try{
                        ySelected=yList.get(0);
                    } catch (Exception e){
                        Log.e("TEST","exception")   ;
                    }
                }
                getPodSummaryDataFiltered();
                plotPodSummaryData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void configureSpinnerY() {
        Spinner spn_x = (Spinner) findViewById(R.id.spinner_layout_y);

        ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(DashboardActivity.this, R.layout.spinner_item, yList){

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.colorBlue));

                return view;
            }
        };
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_x.setAdapter(spnAdapter);

        spn_x.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                ySelected = yList.get(position);

                if (xSelected == null ||xSelected.equals("")){
                    try{
                        xSelected=xList.get(0);
                    } catch (Exception e){
                        Log.e("TEST","exception")   ;
                    }
                }

                getPodSummaryDataFiltered();
                plotPodSummaryData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getLayoutData() {

        //session.attemptLayouts(currentDate);
        session.attemptLayouts("2019-11-18");

    }

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

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_dashboard_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }


}
