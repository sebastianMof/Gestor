package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MaquinasDetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maquinas_detalle);

        String savedExtra = getIntent().getStringExtra("item");
        TextView myText = (TextView) findViewById(R.id.textview_maquinas_detalle_nombre);
        myText.setText(savedExtra);
    }
}
