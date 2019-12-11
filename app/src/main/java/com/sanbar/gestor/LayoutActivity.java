package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.sanbar.gestor.ui.login.LoginActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class LayoutActivity extends AppCompatActivity {

    private Sesion session;
    private String currentDate;

    private ArrayList<Integer> coordenadaX;
    private ArrayList<Integer> coordenadaY;
    private ArrayList<Integer> Id;

    //-----
    private double maxImgX=0.0;
    private double maxImgY=0.0;

    private String layoutName;
    private String layoutFotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureDate();
        configureButtonBack();

        getData();
        plotData();

    }

    private void getData() {

        session.attemptLayouts(currentDate);
        String layouts = session.getLayouts();

        try {
            JSONArray layoutsArray = new JSONArray(layouts);
            JSONObject auxObj;
            for (int i =0; i<layoutsArray.length();i++){
                auxObj=layoutsArray.getJSONObject(i);
                //auxObj.getInt("Id");
                layoutName = auxObj.getString("Nombre");
                maxImgX= (double) auxObj.getJSONObject("Dimensiones").getInt("X");
                maxImgY= (double) auxObj.getJSONObject("Dimensiones").getInt("Y");
                layoutFotoUrl = auxObj.getString("Foto");

                JSONArray tareas = auxObj.getJSONArray("Areas").getJSONObject(0).getJSONArray("Tareas");

                for (int j = 0; j < tareas.length() ; j++) {
                    auxObj=tareas.getJSONObject(j);
                    Id.add(auxObj.getInt("Id"));
                    coordenadaX.add(auxObj.getJSONObject("Coordenadas").getInt("X"));
                    coordenadaY.add(auxObj.getJSONObject("Coordenadas").getInt("Y"));
                }

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("TEST", String.valueOf(coordenadaX));
    }

    private void plotData() {
        GraphView graph = (GraphView) findViewById(R.id.graph);

        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        series.setShape(PointsGraphSeries.Shape.POINT);
        series.setColor(Color.GREEN);

        graph.setTitle(layoutName);

        // set manual X bounds
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(maxImgX);

        // set manual Y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMaxY(maxImgY);

        Drawable image = ContextCompat.getDrawable(this, R.drawable.imagen_maquina_extendida);
        graph.setBackground(image);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"",""});
        staticLabelsFormatter.setVerticalLabels(new String[] {"",""});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

    }

    private void configureButtonBack() {
        Button btn_atras = (Button) findViewById(R.id.button_layout_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    private void configureDate() {
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        currentDate = "2019-11-26";
    }


}
