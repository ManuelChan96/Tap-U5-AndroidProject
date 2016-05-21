package com.example.josediego.tap_u5_proyectoandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.text.InputType;
import android.view.Gravity;
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
    private EditText text_tipo_objeto, text_descripcion, text_cantidad, text_nombre_persona, text_fecha_devolucion, text_fecha_prestamo;


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
        } else {
            mFechaEntrega.show();
        }
    }
    public void guardarOnClick(View view){
        String fechaPres = fechaPrestamo.getText().toString();
        String fechaEnt = fechaEntrega.getText().toString();
        String[] prestado = fechaPres.split("-");
        String[] entregado = fechaEnt.split("-");
        String persona_nombre="";
        String objeto_nombre="";
        int cantidad=0;
        String fecha_prestamo="";
        String fecha_devolucion="";
        String descripcion="";
        try {
            persona_nombre = text_nombre_persona.getText().toString();
            objeto_nombre = text_tipo_objeto.getText().toString();
            cantidad = Integer.parseInt(text_cantidad.getText().toString());
            fecha_prestamo = text_fecha_prestamo.getText().toString();
            fecha_devolucion = text_fecha_devolucion.getText().toString();
            descripcion = text_descripcion.getText().toString();
            if (persona_nombre.isEmpty() || objeto_nombre.isEmpty() || fecha_prestamo.isEmpty() || fecha_devolucion.isEmpty() || descripcion.isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(), "No puede haber campos vacios", Toast.LENGTH_SHORT);
                toast.show();
            }
            if (cantidad < 1) {
                Toast toast = Toast.makeText(getApplicationContext(), "Cantidad inválida", Toast.LENGTH_SHORT);
                toast.show();
            }else {
                if (Integer.parseInt(prestado[2]) == Integer.parseInt(entregado[2])) {
                    if (Integer.parseInt(prestado[1]) == Integer.parseInt(entregado[1])) {
                        if (Integer.parseInt(prestado[0]) <= Integer.parseInt(entregado[0])) {
                            //sentencia guardar en BD
                            guardarBD(persona_nombre, objeto_nombre, cantidad, fecha_prestamo, fecha_devolucion, descripcion);
                            finish();
                            Toast toast = Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Fecha inválida", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    } else if (Integer.parseInt(prestado[1]) < Integer.parseInt(entregado[1])) {
                        //sentencia guardar en BD
                        guardarBD(persona_nombre, objeto_nombre, cantidad, fecha_prestamo, fecha_devolucion, descripcion);
                        finish();
                        Toast toast = Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Fecha inválida", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else if (Integer.parseInt(prestado[2]) < Integer.parseInt(entregado[2])) {
                    //sentencia guardar en BD
                    guardarBD(persona_nombre, objeto_nombre, cantidad, fecha_prestamo, fecha_devolucion, descripcion);
                    finish();
                    Toast toast = Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Fecha inválida", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }catch(Exception e){
            Toast toast = Toast.makeText(getApplicationContext(), "No puede haber campos vacios", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void guardarBD(String cliente_nombre, String objeto_nombre, int cantidad, String fecha_prestamo, String fecha_devolucion, String descripcion){
        BDPrestamos db = new BDPrestamos(this);
        db.insertarPrestamos(new Prestamos(objeto_nombre, cliente_nombre, cantidad, fecha_devolucion, fecha_prestamo, descripcion));
    }

    public void descartarOnClick(View view){
        finish();
    }
}
