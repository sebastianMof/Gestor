package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.sanbar.gestor.R.color.colorGreen;
import static com.sanbar.gestor.R.color.colorOrangeUnselected;

public class PodDetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_detalle);

        String savedExtra = getIntent().getStringExtra("item");
        TextView myText = (TextView) findViewById(R.id.textview_pod_detalle_nombre_capataz);
        myText.setText(savedExtra);

        configureButtonBack();

        LinearLayout ll_pod_detalle_tarea = (LinearLayout)findViewById(R.id.linearlayout_pod_detalle_tarea);

        ll_pod_detalle_tarea.setBackground(getResources().getDrawable(R.drawable.rounded_button_green));

    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_pod_detalle_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}
