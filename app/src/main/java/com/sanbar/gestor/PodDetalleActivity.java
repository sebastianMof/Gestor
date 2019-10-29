package com.sanbar.gestor;

import android.app.Activity;
import android.content.Intent;
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
import java.util.Calendar;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PodDetalleActivity extends AppCompatActivity {

    private Sesion session;

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

        String savedExtra = getIntent().getStringExtra("item");
        TextView myText = (TextView) findViewById(R.id.textview_pod_detalle_ito);
        myText.setText(savedExtra);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureButtonBack();
        configureButtonIniciar();
        configureButtonInterrupcion();

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

    private void configureButtonIniciar() {

        final Button btn_iniciar = (Button) findViewById(R.id.button_pod_detalle_iniciar);

        if (!tarea_iniciada){
            btn_iniciar.setText("INICIAR");
        } else {
            btn_iniciar.setText("TERMINAR");
        }

        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!tarea_iniciada){
                    obtenerHoraIniciar();
                } else {
                    //METODO TERMINAR TAREA
                    Intent myIntent = new Intent(PodDetalleActivity.this, PodFinalizarTareaActivity.class);
                    startActivityForResult(myIntent,1);
                }

            }
        });
    }

    private void configureButtonInterrupcion() {

        final Button btn_interrupcion = (Button) findViewById(R.id.button_pod_detalle_interrupcion);

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
                    //METODO TERMINAR INTERRUPCION
                    Intent myIntent = new Intent(PodDetalleActivity.this, PodFinalizarTareaActivity.class);
                    myIntent.putExtra("SESSION", session);
                    startActivity(myIntent);
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

                //METODO DE INICIO
                tarea_iniciada=true;
                configureHoraInicio();
                configureButtonIniciar();

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

                //METODO DE Interrupcion
                tarea_interrumpida=true;
                configureHoraInterrupcion();
                configureButtonInterrupcion();
                Intent myIntent = new Intent(PodDetalleActivity.this, PodInterrupcionCausaActivity.class);
                myIntent.putExtra("SESSION", session);
                startActivity(myIntent);

            }
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, true);

        recogerHora.show();
    }

    private void obtenerFechaIniciar(){
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

    private void configureHoraInicio(){
        TextView et_hora_inicio = (TextView) findViewById(R.id.textview_pod_detalle_hora_inicio);
        et_hora_inicio.setText("HORA DE INICIO: "+horaObtenida);
    }

    private void configureHoraFin(){
        TextView et_hora_inicio = (TextView) findViewById(R.id.textview_pod_detalle_hora_fin);
        et_hora_inicio.setText("HORA DE FIN: "+horaObtenida);
    }

    private void configureHoraInterrupcion(){
        TextView et_hora_inicio = (TextView) findViewById(R.id.textview_pod_detalle_hora_interrupcion);
        et_hora_inicio.setText("HORA DE INTERRUPCION: "+horaObtenida);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Log.e("TEST","onresult ok");
                tarea_iniciada=false;
                configureButtonIniciar();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),"No se pudo finalizar actividad",Toast.LENGTH_SHORT).show();
            }
        }
    }//onActivityResult

}
