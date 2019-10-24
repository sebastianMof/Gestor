package com.sanbar.gestor;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.ArrayAdapter;


import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class CustomListAdapterMaquinas extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    private final Integer[] imageIDarray;
    private final Integer[] pieChartArray;

    private final String[] codeArray;
    private final String[] marcaArray;
    private final String[] modeloArray;
    private final String[] patenteArray;
    private final String[] isActivoArray;


    public CustomListAdapterMaquinas(Activity context,
                             String[] codeArrayParam,
                             String[] marcaArrayParam,
                             String[] modeloArrayParam,
                             String[] patenteArrayParam,
                             String[] isActivoArrayParam,
                             Integer[] imageIDArrayParam,
                             Integer[] pieChartArrayParam){

        super(context,R.layout.listview_row_maquina, codeArrayParam);


        //


        this.context=context;
        this.imageIDarray = imageIDArrayParam;
        this.pieChartArray = pieChartArrayParam;

        this.codeArray = codeArrayParam;
        this.marcaArray = marcaArrayParam;
        this.modeloArray = modeloArrayParam;
        this.patenteArray = patenteArrayParam;
        this.isActivoArray = isActivoArrayParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row_maquina, null,true);

        //this code gets references to objects in the listview_row_persona_persona.xml file
        TextView tv_nombre = (TextView) rowView.findViewById(R.id.textview_maquinas_item_nombre);
        TextView tv_cod = (TextView) rowView.findViewById(R.id.textview_maquinas_item_codigo);
        TextView tv_marca = (TextView) rowView.findViewById(R.id.textview_maquinas_item_marca);
        TextView tv_modelo = (TextView) rowView.findViewById(R.id.textview_maquinas_item_modelo);
        TextView tv_patente = (TextView) rowView.findViewById(R.id.textview_maquinas_item_patente);
        TextView tv_status = (TextView) rowView.findViewById(R.id.textview_maquinas_item_status);
        TextView tv_ubicacion = (TextView) rowView.findViewById(R.id.textview_maquinas_item_ubicacion);

        ImageView imgv_imagen = (ImageView) rowView.findViewById(R.id.imageview_maquinas_item);
        PieChartView pieChartView = rowView.findViewById(R.id.piechart);

        //this code sets the values of the objects to values from the arrays
        tv_nombre.setText("NOMBRE");
        tv_cod.setText(codeArray[position]);
        tv_marca.setText(marcaArray[position]);
        tv_modelo.setText(modeloArray[position]);
        tv_patente.setText(patenteArray[position]);
        tv_status.setText(isActivoArray[position]);
        tv_ubicacion.setText("ÚLTIMA UBICACIÓN");

        imgv_imagen.setImageResource(imageIDarray[position]);

        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue((100-pieChartArray[position]), Color.rgb(235,169,119)));
        pieData.add(new SliceValue(pieChartArray[position], Color.rgb(244,106,0)));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasCenterCircle(true).setCenterText1(String.valueOf(pieChartArray[position])).setCenterText1FontSize(20).setCenterText1Color(Color.rgb(244,106,0));
        pieChartView.setPieChartData(pieChartData);
        return rowView;

    };

}
