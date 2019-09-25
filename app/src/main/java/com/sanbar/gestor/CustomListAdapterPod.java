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
    private final String[] statusArray;
    private final String[] ituArray;
    private final String[] especialidadArray;


    public CustomListAdapterPod(Activity context,
                                String[] nameArrayParam,
                                String[] statusArrayParam,
                                String[] ituArrayParam,
                                String[] especialidadArrayParam){

        super(context,R.layout.listview_row_persona, nameArrayParam);

        this.context=context;
        this.nameArray = nameArrayParam;
        this.statusArray = statusArrayParam;
        this.ituArray = ituArrayParam;
        this.especialidadArray = especialidadArrayParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row_pod, null,true);

        TextView tv_nombre = (TextView) rowView.findViewById(R.id.textview_pod_item_nombre_capataz);
        TextView tv_status = (TextView) rowView.findViewById(R.id.textview_pod_item_status);
        TextView tv_itu = (TextView) rowView.findViewById(R.id.textview_pods_item_codigo_itu);
        TextView tv_especialidad = (TextView) rowView.findViewById(R.id.textview_pod_item_especialidad);

        tv_nombre.setText(nameArray[position]);
        tv_status.setText(statusArray[position]);
        tv_itu.setText(ituArray[position]);
        tv_especialidad.setText(especialidadArray[position]);

        return rowView;

    };


}
