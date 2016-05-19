package com.example.josediego.tap_u5_proyectoandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class NewObject extends AppCompatActivity implements OnClickListener {
    private EditText fechaPrestamo;
    private EditText fechaEntrega;
    private DatePickerDialog mFechaPrestamo;
    private DatePickerDialog mFechaEntrega;
    private SimpleDateFormat formatoFecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_object);
        formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        fechaPrestamo = (EditText) findViewById(R.id.text_fecha_prestamo);
        fechaPrestamo.setInputType(InputType.TYPE_NULL);
        fechaEntrega = (EditText) findViewById(R.id.text_fecha_devolucion);
        fechaEntrega.setInputType(InputType.TYPE_NULL);
        seleccionarFecha();
    }

    private void seleccionarFecha() {
        fechaPrestamo.setOnClickListener(this);
        fechaEntrega.setOnClickListener(this);
        Calendar calendario = Calendar.getInstance();
        mFechaPrestamo = new DatePickerDialog(this, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int anio, int mes, int dia) {
                Calendar fecha = Calendar.getInstance();
                fecha.set(anio, mes, dia);
                fechaPrestamo.setText(formatoFecha.format(fecha.getTime()));
            }
        }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));

        mFechaEntrega = new DatePickerDialog(this, new OnDateSetListener() {
            public void onDateSet(DatePicker view, int anio, int mes, int dia) {
                Calendar fecha = Calendar.getInstance();
                fecha.set(anio, mes, dia);
                fechaEntrega.setText(formatoFecha.format(fecha.getTime()));
            }
        }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == fechaPrestamo) {
            mFechaPrestamo.show();
        } else if(view == fechaEntrega) {
            mFechaEntrega.show();
        }
    }
    public void guardarOnClick(View view){
        String fechaPres = fechaPrestamo.getText().toString();
        String fechaEnt = fechaEntrega.getText().toString();
        String[] prestado = fechaPres.split("-");
        String[] entregado = fechaEnt.split("-");
        if (Integer.parseInt(prestado[2])==Integer.parseInt(entregado[2])){
            if (Integer.parseInt(prestado[1])==Integer.parseInt(entregado[1])){
                if (Integer.parseInt(prestado[0])<=Integer.parseInt(entregado[0])){
                    //sentencia guardar en BD
                    Toast toast = Toast.makeText(getApplicationContext(), "Fecha valida", Toast.LENGTH_SHORT);
                    toast.show();
                    System.exit(0);
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Fecha de entrega no puede ser mayor que fecha de prestamo", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }else if (Integer.parseInt(prestado[1])<Integer.parseInt(entregado[1])){
                //sentencia guardar en BD
                Toast toast = Toast.makeText(getApplicationContext(), "Fecha valida", Toast.LENGTH_SHORT);
                toast.show();
                System.exit(0);
            }else {
                Toast toast = Toast.makeText(getApplicationContext(), "Fecha de entrega no puede ser mayor que fecha de prestamo", Toast.LENGTH_SHORT);
                toast.show();
            }
        }else if(Integer.parseInt(prestado[2])<Integer.parseInt(entregado[2])){
            //sentencia guardar en BD
            Toast toast = Toast.makeText(getApplicationContext(), "Fecha valida", Toast.LENGTH_SHORT);
            toast.show();
            System.exit(0);
        }else {
            Toast toast = Toast.makeText(getApplicationContext(), "Fecha de entrega no puede ser mayor que fecha de prestamo", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void descartarOnClick(View view){
        System.exit(0);
    }
}
