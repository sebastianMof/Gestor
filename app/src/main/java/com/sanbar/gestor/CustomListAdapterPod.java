package com.sanbar.gestor;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.graphics.Color.parseColor;

public class CustomListAdapterPod extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    private final String[] nameArray;
    private final String[] areaArray;
    private final String[] statusArray;
    private final String[] especialidadArray;
    private final String[] colorArray;


    public CustomListAdapterPod(Activity context,
                                String[] nameArrayParam,
                                String[] areaArrayParam,
                                String[] statusArrayParam,
                                String[] especialidadArrayParam,
                                String[] colorArrayParam){

        super(context,R.layout.listview_row_persona, nameArrayParam);

        this.context=context;
        this.nameArray = nameArrayParam;
        this.areaArray = areaArrayParam;
        this.statusArray = statusArrayParam;
        this.especialidadArray = especialidadArrayParam;
        this.colorArray = colorArrayParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row_pod, null,true);

        TextView tv_nombre = (TextView) rowView.findViewById(R.id.textview_pod_item_nombre_capataz);
        TextView tv_area = (TextView) rowView.findViewById(R.id.textview_pod_item_area);
        TextView tv_status = (TextView) rowView.findViewById(R.id.textview_pod_item_status);
        TextView tv_especialidad = (TextView) rowView.findViewById(R.id.textview_pod_item_especialidad);
        LinearLayout ll_row = (LinearLayout) rowView.findViewById(R.id.linearlayout_pod_listrow);

        tv_nombre.setText(nameArray[position]);
        tv_area.setText(areaArray[position]);
        tv_status.setText(statusArray[position]);
        tv_especialidad.setText(especialidadArray[position]);

        int color = parseColor (colorArray[position]);
        ColorDrawable cd = new ColorDrawable(color);
        ll_row.setBackground(cd);


        return rowView;

    };


}
