package com.example.josediego.tap_u5_proyectoandroid;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ListaSimple extends AppCompatActivity {
    private ListView lista;
    final ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
    private SimpleDateFormat formatoFecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);

        for (int i = 0; i <= 300; i++) {
            datos.add(new Lista_entrada("Objeto " + i, "NOMBRE " + i, i, i%5==0,"22-05-2016",sacarFecha(i)));
        }
        lista = (ListView) findViewById(R.id.ListView_listado);
        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, datos) {
            @Override
            public void onEntrada(final Object entrada, View view) {
                TextView textoObjeto = (TextView) view.findViewById(R.id.textView_superior);
                TextView textoNombre = (TextView) view.findViewById(R.id.textView_inferior);
                TextView textoFechaIni = (TextView) view.findViewById(R.id.textView_fecha_inicio);
                TextView textoFechaFin = (TextView) view.findViewById(R.id.textView_fecha_fin);
                final CheckBox check=(CheckBox) view.findViewById(R.id.checked);
                textoObjeto.setText(((Lista_entrada) entrada).get_textoEncima());
                textoNombre.setText(((Lista_entrada) entrada).get_textoDebajo());
                textoFechaIni.setText(((Lista_entrada) entrada).getFechaPrestamo());
                textoFechaFin.setText(((Lista_entrada) entrada).getFechaDevolucion());
                check.setChecked(((Lista_entrada) entrada).getEntregado());
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(((Lista_entrada) entrada).getEntregado()){
                            ((Lista_entrada) entrada).setEntregado(false);
                            cambio("Hecho falso "+((Lista_entrada) entrada).get_id());
                        }else{
                            ((Lista_entrada) entrada).setEntregado(true);
                            cambio("Hecho verdadero "+((Lista_entrada) entrada).get_id());
                            mFechaPrestamo.show();
                        }
                        check.setChecked(((Lista_entrada) entrada).getEntregado());
                    }
                });
            }
        });
        seleccionarFecha();
        formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

    }


    private DatePickerDialog mFechaPrestamo;

    private void seleccionarFecha() {
        Calendar calendario = Calendar.getInstance();
        mFechaPrestamo = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int anio, int mes, int dia) {
                Calendar fecha = Calendar.getInstance();
                fecha.set(anio, mes, dia);
                //fechaPrestamo.setText(formatoFecha.format(fecha.getTime()));
            }
        }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
    }

    public void cambio(String s){
        Toast toast = Toast.makeText(getApplicationContext(),s+ " :D " , Toast.LENGTH_SHORT);
        toast.show();
    }
    public String sacarFecha(int i){
        if(i%10==0){
            return "22-05-2001";
        }
        return "31-08-2020";
    }
}
