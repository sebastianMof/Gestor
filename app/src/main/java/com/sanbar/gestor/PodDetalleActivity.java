package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PodDetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_detalle);

        String savedExtra = getIntent().getStringExtra("item");
        TextView myText = (TextView) findViewById(R.id.textview_pod_detalle_nombre_tarea);
        myText.setText(savedExtra);
    }
}
