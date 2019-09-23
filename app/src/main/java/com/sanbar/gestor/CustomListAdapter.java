package com.sanbar.gestor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    private final Integer[] imageIDarray;

    private final String[] nameArray;
    private final String[] cargoArray;
    private final String[] statusArray;


    public CustomListAdapter(Activity context, String[] nameArrayParam, String[] cargoArrayParam, String[] statusArrayParam, Integer[] imageIDArrayParam){

        super(context,R.layout.listview_row_persona, nameArrayParam);

        this.context=context;
        this.imageIDarray = imageIDArrayParam;
        this.nameArray = nameArrayParam;
        this.cargoArray = cargoArrayParam;
        this.statusArray = statusArrayParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row_persona, null,true);

        //this code gets references to objects in the listview_row_persona_persona.xml file
        TextView tv_nombre = (TextView) rowView.findViewById(R.id.textview_personas_item_nombre);
        TextView tv_cargo = (TextView) rowView.findViewById(R.id.textview_personas_item_cargo);
        TextView tv_status = (TextView) rowView.findViewById(R.id.textview_personas_item_status);
        ImageView imgv_imagen = (ImageView) rowView.findViewById(R.id.imageview_personas_item);

        //this code sets the values of the objects to values from the arrays
        tv_nombre.setText(nameArray[position]);
        tv_cargo.setText(cargoArray[position]);
        tv_status.setText(statusArray[position]);
        imgv_imagen.setImageResource(imageIDarray[position]);

        return rowView;

    };
}
