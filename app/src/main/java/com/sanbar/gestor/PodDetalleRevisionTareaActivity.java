package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PodDetalleRevisionTareaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_detalle_revision_tarea);

        configureButtonBack();
        configureLog();

    }

    private void configureLog() {
        TextView tv_log = (TextView)findViewById(R.id.textview_pod_detalle_revision_tarea_log);

        tv_log.append("\n");

        tv_log.append("\n");
        tv_log.append("SUCESO 1");
        tv_log.append("\n");
        tv_log.append("SUCESO 2");
        tv_log.append("\n");
        tv_log.append("SUCESO 3");

    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_pod_detalle_revisar_tarea_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}
