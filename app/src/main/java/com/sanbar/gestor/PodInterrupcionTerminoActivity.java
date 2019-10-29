package com.sanbar.gestor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class PodInterrupcionTerminoActivity extends AppCompatActivity {

    private Sesion session;

    private boolean switch_state = false;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pod_interrupcion_termino);

        try {
            Intent intent = getIntent();
            session = intent.getParcelableExtra("SESSION");
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"PROBLEMA CON DATOS DE LA CUENTA",Toast.LENGTH_SHORT).show();
        }

        configureSwitch();
        configureButtonContinuar();
        configureButtonBack();

    }

    private void configureButtonContinuar() {

        final Button btn_continuar = (Button) findViewById(R.id.button_pod_interrupcion_termino_continuar);

        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!switch_state){
                    obtenerFechaTermino();
                } else {
                    obtenerHoraTermino();
                }

            }
        });

    }

    private void configureSwitch() {

        Switch sw_complete = (Switch) findViewById(R.id.switch_pod_interrupcion_termino);
        sw_complete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                switch_state = isChecked;

            }
        });
    }

    private void configureButtonBack() {

        Button btn_atras = (Button) findViewById(R.id.button_pod_interrupcion_termino_back);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void obtenerFechaTermino(){
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

                Intent myIntent = new Intent(PodInterrupcionTerminoActivity.this, PodInterrupcionResponsableActivity.class);
                myIntent.putExtra("SESSION", session);
                myIntent.putExtra("FECHA", fechaObtenida);
                startActivity(myIntent);

            }
        },anio, mes, dia);

        recogerFecha.show();
    }

    private void obtenerHoraTermino(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Hora con el formato deseado
                horaObtenida = String.valueOf(hourOfDay) + DOS_PUNTOS + String.valueOf(minute)+DOS_PUNTOS+DOS_CEROS;

                Intent myIntent = new Intent(PodInterrupcionTerminoActivity.this, PodInterrupcionResponsableActivity.class);
                myIntent.putExtra("SESSION", session);
                myIntent.putExtra("HORA", horaObtenida);
                startActivity(myIntent);

            }
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, true);

        recogerHora.show();
    }




}
