package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PodDetalleGestionarInterrupcionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_detalle_gestionar_interrupcion);

        configureButtonBack();
        configureButtonNotificar();
    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_pod_detalle_gestionar_interrupcion_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void configureButtonNotificar() {

        Button btn_atras = (Button) findViewById(R.id.button_pod_detalle_gestionar_interrupcion_notificar);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}
