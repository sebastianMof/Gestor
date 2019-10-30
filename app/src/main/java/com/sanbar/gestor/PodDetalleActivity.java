package com.sanbar.gestor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.graphics.Color.parseColor;

public class PodDetalleActivity extends AppCompatActivity {

    private Sesion session;
    private String tareaId;

    //PICKERS--------------------------------------------
    private static final String CERO = "0";
    private static final String DOS_CEROS = "00";
    private static final String BARRA = "/";
    private static final String DOS_PUNTOS = ":";

    public final Calendar c = Calendar.getInstance();

    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    String horaObtenida;
    String fechaObtenida;
    //----------------------------------------------------
    boolean tarea_iniciada = false;
    boolean tarea_interrumpida = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_detalle);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
            tareaId = getIntent().getStringExtra("tareaId");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureData();
        configureButtonBack();
        configureButtonsIniciar();
        configureButtonInterrupcion();

    }

                /*
            tarea.getString("EspecialidadName");//"manejo de material"
            tarea.getString("TareaStatusName");//"No iniciada"
            tarea.getString("UnidadMedida");//Dollar
            tarea.getString("CantidadPlanificada");//10
            tarea.getString("CantidadCompletada");//null
*/

    @SuppressLint("SetTextI18n")
    private void configureData(){
        session.attemptTareasTareaId(tareaId);

        TextView ito = findViewById(R.id.textview_pod_detalle_ito);
        TextView horaInicio = findViewById(R.id.textview_pod_detalle_hora_inicio);
        TextView horaFin = findViewById(R.id.textview_pod_detalle_hora_fin);
        TextView horaInterrupcion = findViewById(R.id.textview_pod_detalle_hora_interrupcion);

        LinearLayout ll_pod_detalle_tarea = (LinearLayout)findViewById(R.id.linearlayout_pod_detalle_tarea);

        try {
            JSONObject tarea = new JSONObject(session.getTareasTareaId());

            ito.setText("Ito de la tarea con id: "+tarea.getString("Id"));

            if ( tarea.isNull("InicioReal")) {
                horaInicio.setText("Inicio programado: \n"+tarea.getString("InicioPrograma"));
            }else {
                horaInicio.setText("Inicio: \n"+tarea.getString("InicioReal"));
            }

            if (tarea.isNull("TerminoReal")) {
                horaFin.setText("Fin programado: \n"+tarea.getString("TerminoProgramada"));
            }else {
                horaFin.setText("Fin: \n"+tarea.getString("TerminoReal"));
            }

            if (!tarea.isNull("UltimaInterrupcion")){
                horaInterrupcion.setText("Interrupción: \n"+tarea.getString("UltimaInterrupcion"));
            }

            int color = parseColor(tarea.getString("Color"));
            ColorDrawable cd = new ColorDrawable(color);
            ll_pod_detalle_tarea.setBackground(cd);

        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    private void configureButtonsIniciar() {

        final Button btn_iniciar = (Button) findViewById(R.id.button_pod_detalle_iniciar);
        final Button btn_terminar = (Button) findViewById(R.id.button_pod_detalle_terminar);

        try {
            JSONObject tarea = new JSONObject(session.getTareasTareaId());

            if (tarea.getString("TareaStatusName").equals("No iniciada")){
                btn_iniciar.setVisibility(View.VISIBLE);
                btn_terminar.setVisibility(View.VISIBLE);
            }
                //puede interrumpirse y terminarse o iniciarse
            if (tarea.getString("TareaStatusName").equals("Atrasada")){
                btn_iniciar.setVisibility(View.VISIBLE);
                btn_terminar.setVisibility(View.VISIBLE);
            }
                //puede interrumpirse y terminarse o iniciarse
            if (tarea.getString("TareaStatusName").equals("Iniciada")){
                btn_iniciar.setVisibility(View.GONE);
                btn_terminar.setVisibility(View.VISIBLE);
            }
                //puede interrumpirse y terminarse
            if (tarea.getString("TareaStatusName").equals("Interrupcion")){
                btn_iniciar.setVisibility(View.GONE);
                btn_terminar.setVisibility(View.VISIBLE);
            }
                //puede desinterruprse y terminarse
            if (tarea.getString("TareaStatusName").equals("Terminada")){
                btn_iniciar.setVisibility(View.GONE);
                btn_terminar.setVisibility(View.GONE);
            }
                //nada

        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                obtenerHoraIniciar();
            }
        });

        btn_terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                obtenerHoraTerminar();
            }
        });
    }

    private void configureButtonInterrupcion() {

        final Button btn_interrupcion = (Button) findViewById(R.id.button_pod_detalle_interrupcion);

        try {
            JSONObject tarea = new JSONObject(session.getTareasTareaId());

            if (tarea.getString("TareaStatusName").equals("No iniciada")){
                btn_interrupcion.setVisibility(View.VISIBLE);
                tarea_interrumpida=false;
            }
            //puede interrumpirse y terminarse o iniciarse
            if (tarea.getString("TareaStatusName").equals("Atrasada")){
                btn_interrupcion.setVisibility(View.VISIBLE);
                tarea_interrumpida=false;
            }
            //puede interrumpirse y terminarse o iniciarse
            if (tarea.getString("TareaStatusName").equals("Iniciada")){
                btn_interrupcion.setVisibility(View.VISIBLE);
                tarea_interrumpida=false;
            }
            //puede interrumpirse y terminarse
            if (tarea.getString("TareaStatusName").equals("Interrupcion")){
                btn_interrupcion.setVisibility(View.VISIBLE);
                tarea_interrumpida=true;
            }
            //puede desinterruprse y terminarse
            if (tarea.getString("TareaStatusName").equals("Terminada")){
                btn_interrupcion.setVisibility(View.GONE);
            }
            //nada

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!tarea_interrumpida){
            btn_interrupcion.setText("INTERRUPCIÓN");
        } else {
            btn_interrupcion.setText("TERMINAR INTERRUPCIÓN");
        }

        btn_interrupcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!tarea_interrumpida){
                    obtenerHoraInterrupcion();

                } else {
                    obtenerHoraTerminoInterrupcion();
                }
            }
        });
    }



    private void obtenerHoraIniciar(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Hora con el formato deseado
                horaObtenida = String.valueOf(hourOfDay) + DOS_PUNTOS + String.valueOf(minute)+DOS_PUNTOS+DOS_CEROS;
                boolean aux = session.attemptIniciarTarea(tareaId,horaObtenida);
                if (aux){
                    Toast.makeText(getApplicationContext(),"Tarea iniciada",Toast.LENGTH_SHORT).show();
                    configureData();
                    configureButtonsIniciar();
                    configureButtonInterrupcion();
                } else {
                    Toast.makeText(getApplicationContext(),"Acción no concretada",Toast.LENGTH_SHORT).show();
                }

            }
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, true);

        recogerHora.show();
    }

    private void obtenerHoraTerminar(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Hora con el formato deseado
                horaObtenida = String.valueOf(hourOfDay) + DOS_PUNTOS + String.valueOf(minute)+DOS_PUNTOS+DOS_CEROS;

                Intent myIntent = new Intent(PodDetalleActivity.this, PodFinalizarTareaActivity.class);
                myIntent.putExtra("SESSION", session);
                myIntent.putExtra("tareaId", tareaId);
                myIntent.putExtra("horaObtenida", horaObtenida);
                startActivityForResult(myIntent,1);

            }
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, true);

        recogerHora.show();
    }

    private void obtenerHoraInterrupcion(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Hora con el formato deseado
                horaObtenida = String.valueOf(hourOfDay) + DOS_PUNTOS + String.valueOf(minute)+DOS_PUNTOS+DOS_CEROS;

                Intent myIntent = new Intent(PodDetalleActivity.this, PodInterrupcionCausaActivity.class);
                myIntent.putExtra("SESSION", session);
                myIntent.putExtra("tareaId", tareaId);
                myIntent.putExtra("horaInicio", horaObtenida);
                startActivityForResult(myIntent,2);

            }
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, true);

        recogerHora.show();
    }

    private void obtenerHoraTerminoInterrupcion(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Hora con el formato deseado
                horaObtenida = String.valueOf(hourOfDay) + DOS_PUNTOS + String.valueOf(minute)+DOS_PUNTOS+DOS_CEROS;

                boolean aux = session.attemptTerminarInterrupcion(tareaId,horaObtenida);
                if (aux){
                    Toast.makeText(getApplicationContext(),"Interrupción terminada",Toast.LENGTH_SHORT).show();
                    configureData();
                    configureButtonsIniciar();
                    configureButtonInterrupcion();
                } else {
                    Toast.makeText(getApplicationContext(),"Acción no concretada",Toast.LENGTH_SHORT).show();
                }

            }
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, true);

        recogerHora.show();
    }

    private void obtenerFechaInterrupcion(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Aumenta en uno el mes porque comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                //Fecha con el formato deseado
                fechaObtenida=diaFormateado + BARRA + mesFormateado + BARRA + year;

            }
        },anio, mes, dia);

        recogerFecha.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {//TERMINAR tarea
            if(resultCode == Activity.RESULT_OK){
                configureData();
                configureButtonsIniciar();
                configureButtonInterrupcion();
                Toast.makeText(getApplicationContext(),"Tarea finalizada",Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),"Acción no concretada",Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 2) {//INTERRUMPIR tarea
            if(resultCode == Activity.RESULT_OK){
                configureData();
                configureButtonsIniciar();
                configureButtonInterrupcion();
                Toast.makeText(getApplicationContext(),"Tarea interrumpida",Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),"Acción no concretada",Toast.LENGTH_SHORT).show();
            }
        }



    }//onActivityResult

}
