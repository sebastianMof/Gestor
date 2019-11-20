package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class PodFinalizarTareaActivity extends AppCompatActivity {

    private Sesion session;

    private String tareaId;
    private String horaObtenida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_finalizar_tarea);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
            tareaId = getIntent().getStringExtra("tareaId");
            horaObtenida = getIntent().getStringExtra("horaObtenida");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureButtonBack();
        configureButtonFinalizarTarea();
        configureProgressViews();
    }

    private void configureProgressViews() {

        EditText et_progreso = (EditText) findViewById(R.id.edittext_pod_detalle_finalizar_tarea_avance);
        TextView avance = findViewById(R.id.textview_pod_detalle_finalizar_tarea_avance);

        try {
            JSONObject auxObj = new JSONObject(session.getTareasTareaId());
            et_progreso.setText(auxObj.getString("CantidadPlanificada"));
            avance.setText(" / "+auxObj.getString("CantidadPlanificada") +" "+auxObj.getString("UnidadMedida"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_pod_detalle_finalizar_tarea_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void configureButtonFinalizarTarea() {

        final EditText et_cantidad = findViewById(R.id.edittext_pod_detalle_finalizar_tarea_avance);
        Button btn_atras = (Button) findViewById(R.id.button_pod_detalle_finalizar_tarea_confirmar);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeLinearLayout();

                String cantidadReal="";

                cantidadReal= et_cantidad.getText().toString();

                boolean finalizada = session.attemptFinalizarTarea(tareaId,horaObtenida,cantidadReal);

                Intent returnIntent = new Intent();
                if (finalizada){
                    setResult(Activity.RESULT_OK,returnIntent);
                } else{
                    setResult(Activity.RESULT_CANCELED,returnIntent);
                }
                finish();

            }
        });
    }

    public void changeLinearLayout(){

        final LinearLayout ll_progressBar = (LinearLayout) findViewById(R.id.linearlayout_pod_finalizar_progressbar);
        final LinearLayout ll_activity = (LinearLayout) findViewById(R.id.linearlayout_pod_finalizar_activity);

        if (ll_progressBar.getVisibility()==View.GONE){
            ll_activity.setVisibility(View.GONE);
            ll_progressBar.setVisibility(View.VISIBLE);
        } else if (ll_progressBar.getVisibility()==View.VISIBLE){
            ll_progressBar.setVisibility(View.GONE);
            ll_activity.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onResume(){
        super.onResume();

        final LinearLayout ll_progressBar = (LinearLayout) findViewById(R.id.linearlayout_pod_finalizar_progressbar);
        final LinearLayout ll_activity = (LinearLayout) findViewById(R.id.linearlayout_pod_finalizar_activity);
        ll_progressBar.setVisibility(View.GONE);
        ll_activity.setVisibility(View.VISIBLE);
    }

}
