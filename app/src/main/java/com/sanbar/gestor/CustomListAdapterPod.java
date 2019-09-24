package com.sanbar.gestor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapterPod extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    private final String[] nameArray;
    private final String[] contratistaArray;
    private final String[] contratoArray;
    private final String[] ituArray;


    public CustomListAdapterPod(Activity context,
                                String[] nameArrayParam,
                                String[] contratistaArrayParam,
                                String[] contratoArrayParam,
                                String[] ituArrayParam){

        super(context,R.layout.listview_row_persona, nameArrayParam);

        this.context=context;
        this.nameArray = nameArrayParam;
        this.contratistaArray = contratistaArrayParam;
        this.contratoArray = contratoArrayParam;
        this.ituArray = ituArrayParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row_pod, null,true);

        TextView tv_nombre = (TextView) rowView.findViewById(R.id.textview_pod_item_nombre_tarea);
        TextView tv_contratista = (TextView) rowView.findViewById(R.id.textview_pod_item_codigo_contratista);
        TextView tv_contrato = (TextView) rowView.findViewById(R.id.textview_pods_item_codigo_contrato);
        TextView tv_itu = (TextView) rowView.findViewById(R.id.textview_pod_item_codigo_itu);

        tv_nombre.setText(nameArray[position]);
        tv_contratista.setText(contratistaArray[position]);
        tv_contrato.setText(contratoArray[position]);
        tv_itu.setText(ituArray[position]);

        return rowView;

    };
}
