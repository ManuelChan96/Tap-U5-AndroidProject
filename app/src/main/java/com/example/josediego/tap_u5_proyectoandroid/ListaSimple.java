package com.example.josediego.tap_u5_proyectoandroid;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.josediego.tap_u5_proyectoandroid.BaseDeDatos.BDPrestamos;
import com.example.josediego.tap_u5_proyectoandroid.BaseDeDatos.Prestamos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ListaSimple extends AppCompatActivity {
    private ListView lista;
    final ArrayList<Prestamos> datos = new ArrayList<Prestamos>();
    private SimpleDateFormat formatoFecha;
    private boolean controlBoolean;
    private String fechaEntregaR;
    BDPrestamos bd;
    Prestamos temp;
    TextView fechaRealE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);
        seleccionarFecha();
        datos.clear();
        //datos.addAll(bd.obtenerTodos());
        for(int j=0;j<=30;j++){
            datos.add(new Prestamos("Objeto prestado "+j, "Nombre persona "+j, j, "22-02-1990", "22-02-1999", j+ " Esta es la descripcion del objeto, es una parte muy interesnate de lo que debemos hacer."));
        }
        datos.add(new Prestamos());

        lista = (ListView) findViewById(R.id.ListView_listado);

        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, datos) {
            @Override
            public void onEntrada(final Object entrada, View view) {
                TextView textoObjeto = (TextView) view.findViewById(R.id.textView_superior);
                TextView textoNombre = (TextView) view.findViewById(R.id.textView_inferior);
                TextView textoFechaIni = (TextView) view.findViewById(R.id.textView_fecha_inicio);
                TextView textoFechaFin = (TextView) view.findViewById(R.id.textView_fecha_fin);
                final TextView textoFechaReal=(TextView) view.findViewById(R.id.textView_fechaReal);
                final CheckBox check=(CheckBox) view.findViewById(R.id.checked);
                textoObjeto.setText(((Prestamos) entrada).getObjeto_nombre());
                textoNombre.setText(((Prestamos) entrada).getCliente_nombre());
                textoFechaIni.setText(((Prestamos) entrada).getFecha_prestamo());
                textoFechaFin.setText(((Prestamos) entrada).getFecha_devolucion());
                fechaRealE=(TextView) textoFechaReal;
                fechaRealE.setText(((Prestamos) entrada).getFecha_real_devolucion());
                formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
                check.setChecked(((Prestamos) entrada).isEstado());
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        controlBoolean=((Prestamos) entrada).isEstado();
                        if(((Prestamos) entrada).isEstado()){
                            temp=(Prestamos)entrada;
                            controlBoolean=false;
                            ((Prestamos) entrada).setEstado(controlBoolean);
                            ((Prestamos) entrada).setFecha_real_devolucion("##-##-####");
                            textoFechaReal.setText(((Prestamos) entrada).getFecha_real_devolucion());
                        }else{
                            temp=(Prestamos)entrada;
                            mFechaPrestamo.show();
                            ((Prestamos) entrada).setEstado(controlBoolean);
                        }
                        mFechaPrestamo.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                ((Prestamos) entrada).setEstado(controlBoolean);
                                check.setChecked(((Prestamos) entrada).isEstado());
                                Toast.makeText(ListaSimple.this, "Hecho "+ check.isChecked(), Toast.LENGTH_SHORT).show();
                                textoFechaReal.setText(((Prestamos) entrada).getFecha_real_devolucion());
                                Toast.makeText(ListaSimple.this, "Dimisseado "+ check.isChecked(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Prestamos elegido = (Prestamos) pariente.getItemAtPosition(posicion);
                CharSequence texto = "Seleccionado: " + elegido.getObjeto_nombre();

                AlertDialog.Builder builder = new AlertDialog.Builder(ListaSimple.this);
                builder.setMessage(elegido.getDescripcion())
                        .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(ListaSimple.this, "Salir", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Eliminar Objeto", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(ListaSimple.this);
                                builder2.setMessage("¿Está seguro que desea eliminar el objeto? Se perderá el registro")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Toast.makeText(ListaSimple.this, "Eliminar", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // User cancelled the dialog
                                                Toast.makeText(ListaSimple.this, "Cancelar", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                // Create the AlertDialog object and return it
                                builder2.create();
                                builder2.show();
                                Toast.makeText(ListaSimple.this, "Cancelar", Toast.LENGTH_SHORT).show();
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();
            }
        });
    }
    private DatePickerDialog mFechaPrestamo;

    private void seleccionarFecha() {
        Calendar calendario = Calendar.getInstance();
        mFechaPrestamo = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int anio, int mes, int dia) {
                Calendar fecha = Calendar.getInstance();
                fecha.set(anio, mes, dia);
                Toast.makeText(ListaSimple.this, fecha.toString(), Toast.LENGTH_SHORT).show();
                fechaEntregaR=formatoFecha.format(fecha.getTime());
                view.getDayOfMonth();
                view.getMonth();
                view.getYear();
                fechaEntregaR= view.getDayOfMonth()+"-"+view.getMonth()+"-"+view.getYear();

            }
        }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
        mFechaPrestamo.setButton(DialogInterface.BUTTON_NEGATIVE, "cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    setControlBoolean(false);
                    temp.setEstado(controlBoolean);

                }
            }
        });
        mFechaPrestamo.setButton(DialogInterface.BUTTON_POSITIVE, "aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    if(mFechaPrestamo.getDatePicker().getMonth()+1<=9){
                        fechaEntregaR=mFechaPrestamo.getDatePicker().getDayOfMonth()+"-0"+(mFechaPrestamo.getDatePicker().getMonth()+1)+"-"+mFechaPrestamo.getDatePicker().getYear();
                    }else{
                        fechaEntregaR=mFechaPrestamo.getDatePicker().getDayOfMonth()+"-"+(mFechaPrestamo.getDatePicker().getMonth()+1)+"-"+mFechaPrestamo.getDatePicker().getYear();
                    }
                    Toast.makeText(ListaSimple.this,fechaEntregaR, Toast.LENGTH_SHORT).show();
                    setControlBoolean(true);
                    paraFechaReal();
                }
            }
        });
    }
    private void paraFechaReal(){
        ( temp).setEstado(controlBoolean);
        ( temp).setFecha_real_devolucion(fechaEntregaR);
    }
    private void setControlBoolean(boolean bool){
        controlBoolean=bool;
    }

}