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

    private final String[] nameArray;
    private final String[] codigoInternoArray;
    private final String[] marcaArray;
    private final String[] modeloArray;
    private final String[] patenteArray;
    private final String[] statusArray;



    public CustomListAdapterMaquinas(Activity context,
                             String[] nameArrayParam,
                             String[] codigoInternoArrayParam,
                             String[] marcaArrayParam,
                             String[] modeloArrayParam,
                             String[] patenteArrayParam,
                             String[] statusArrayParam,
                             Integer[] imageIDArrayParam,
                             Integer[] pieChartArrayParam){

        super(context,R.layout.listview_row_maquina, nameArrayParam);

        this.context=context;
        this.imageIDarray = imageIDArrayParam;
        this.pieChartArray = pieChartArrayParam;

        this.nameArray = nameArrayParam;
        this.codigoInternoArray = codigoInternoArrayParam;
        this.marcaArray = marcaArrayParam;
        this.modeloArray = modeloArrayParam;
        this.patenteArray = patenteArrayParam;
        this.statusArray = statusArrayParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row_maquina, null,true);

        //this code gets references to objects in the listview_row_persona_persona.xml file
        TextView tv_nombre = (TextView) rowView.findViewById(R.id.textview_maquinas_item_nombre);
        TextView tv_cod = (TextView) rowView.findViewById(R.id.textview_maquinas_item_codigo_interno);
        TextView tv_marca = (TextView) rowView.findViewById(R.id.textview_maquinas_item_marca);
        TextView tv_modelo = (TextView) rowView.findViewById(R.id.textview_maquinas_item_modelo);
        TextView tv_patente = (TextView) rowView.findViewById(R.id.textview_maquinas_item_patente);
        TextView tv_status = (TextView) rowView.findViewById(R.id.textview_maquinas_item_status);
        ImageView imgv_imagen = (ImageView) rowView.findViewById(R.id.imageview_maquinas_item);
        PieChartView pieChartView = rowView.findViewById(R.id.piechart);

        //this code sets the values of the objects to values from the arrays
        tv_nombre.setText(nameArray[position]);
        tv_cod.setText(codigoInternoArray[position]);
        tv_marca.setText(marcaArray[position]);
        tv_modelo.setText(modeloArray[position]);
        tv_patente.setText(patenteArray[position]);
        tv_status.setText(statusArray[position]);

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
