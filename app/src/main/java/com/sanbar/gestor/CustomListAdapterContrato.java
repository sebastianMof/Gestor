package com.sanbar.gestor;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.graphics.Color.parseColor;

public class CustomListAdapterContrato extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    private final String[] nameArray;
    private final String[] codeArray;



    public CustomListAdapterContrato(Activity context,
                                     String[] nameArrayParam,
                                     String[] codeArrayParam){

        super(context,R.layout.listview_row_contrato, nameArrayParam);

        this.context=context;
        this.nameArray = nameArrayParam;
        this.codeArray = codeArrayParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row_contrato, null,true);

        TextView tv_nombre = (TextView) rowView.findViewById(R.id.textview_contrato_item_name);
        TextView tv_codigo = (TextView) rowView.findViewById(R.id.textview_contrato_item_code);

        tv_nombre.setText(nameArray[position]);
        tv_codigo.setText(codeArray[position]);

        return rowView;

    };


}
