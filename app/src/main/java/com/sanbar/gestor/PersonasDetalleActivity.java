package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PersonasDetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personas_detalle);

        String savedExtra = getIntent().getStringExtra("item");
        TextView myText = (TextView) findViewById(R.id.textview_personas_detalle_nombre);
        myText.setText(savedExtra);

    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_personas_detalle_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}
