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

import com.example.josediego.tap_u5_proyectoandroid.BaseDeDatos.BDPrestamos;
import com.example.josediego.tap_u5_proyectoandroid.BaseDeDatos.Prestamos;

public class NewObject extends AppCompatActivity implements OnClickListener {
    private EditText fechaPrestamo;
    private EditText fechaEntrega;
    private DatePickerDialog mFechaPrestamo;
    private DatePickerDialog mFechaEntrega;
    private SimpleDateFormat formatoFecha;

    private EditText text_tipo_objeto, text_descripcion,
            text_cantidad, text_nombre_persona, text_fecha_devolucion, text_fecha_prestamo;

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

        text_tipo_objeto = (EditText) findViewById(R.id.text_tipo_objeto);
        text_descripcion = (EditText) findViewById(R.id.text_descripcion);
        text_nombre_persona = (EditText) findViewById(R.id.text_nombre_persona);
        text_fecha_devolucion = (EditText) findViewById(R.id.text_fecha_devolucion);
        text_fecha_prestamo = (EditText) findViewById(R.id.text_fecha_prestamo);
        text_cantidad = (EditText) findViewById(R.id.text_cantidad);
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
                    guardarBD();
                    Toast toast = Toast.makeText(getApplicationContext(), "Fecha valida", Toast.LENGTH_SHORT);
                    toast.show();
                    System.exit(0);
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Fecha de entrega no puede ser mayor que fecha de prestamo", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }else if (Integer.parseInt(prestado[1])<Integer.parseInt(entregado[1])){
                //sentencia guardar en BD
                guardarBD();
                Toast toast = Toast.makeText(getApplicationContext(), "Fecha valida", Toast.LENGTH_SHORT);
                toast.show();
                System.exit(0);
            }else {
                Toast toast = Toast.makeText(getApplicationContext(), "Fecha de entrega no puede ser mayor que fecha de prestamo", Toast.LENGTH_SHORT);
                toast.show();
            }
        }else if(Integer.parseInt(prestado[2])<Integer.parseInt(entregado[2])){
            //sentencia guardar en BD
            guardarBD();
            Toast toast = Toast.makeText(getApplicationContext(), "Fecha valida", Toast.LENGTH_SHORT);
            toast.show();
            System.exit(0);
        }else {
            Toast toast = Toast.makeText(getApplicationContext(), "Fecha de entrega no puede ser mayor que fecha de prestamo", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void guardarBD(){

        String cliente_nombre = text_nombre_persona.getText().toString();
        String objeto_nombre = text_tipo_objeto.getText().toString();
        int cantidad = Integer.parseInt(text_cantidad.getText().toString());
        String fecha_prestamo = text_fecha_prestamo.getText().toString();
        String fecha_devolucion = text_fecha_devolucion.getText().toString();
        String descripcion = text_descripcion.getText().toString();

        BDPrestamos db = new BDPrestamos(this);
        db.insertarPrestamos(new Prestamos(objeto_nombre, cliente_nombre, cantidad, fecha_devolucion, fecha_prestamo, descripcion));

        text_nombre_persona.setText("");
        text_tipo_objeto.setText("");
        text_cantidad.setText("");
        text_fecha_prestamo.setText("");
        text_fecha_devolucion.setText("");
        text_descripcion.setText("");

    }

    public void descartarOnClick(View view){
        System.exit(0);
    }
}
