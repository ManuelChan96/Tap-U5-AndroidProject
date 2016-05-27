package com.example.josediego.tap_u5_proyectoandroid;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    ArrayList<Prestamos> datos = new ArrayList<Prestamos>();
    private SimpleDateFormat formatoFecha;
    private boolean controlBoolean;
    private String fechaEntregaR;
    private BDPrestamos bd;
    Prestamos temp;
    TextView fechaRealE;
    private String[] items;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle drawerToggle;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);

        String sel=(String)getIntent().getExtras().get("selected");
        seleccionarFecha();
        datos.clear();
        bd = new BDPrestamos(this);
        if(sel.equals("1")){
            setTitle("Prestamos");
            //mDrawerList.setItemChecked(1, true);
            datos.addAll(bd.obtenerTodos());
        }
        if(sel.equals("2")){
            setTitle("Vencidos");
            //mDrawerList.setItemChecked(2, true);
            datos.addAll(bd.obtenerVencidos());
        }
        if(sel.equals("3")){
            setTitle("Entregados");
            //mDrawerList.setItemChecked(3, true);
            datos.addAll(bd.obtenerEntregados());
        }
        if(sel.equals("busqueda")){
            setTitle("Resultados búsqueda");
            datos.addAll(bd.buscar((String)getIntent().getExtras().get("search")));
        }
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

                if(((Prestamos) entrada).getFecha_real_devolucion().equals("##-##-####")){
                    check.setChecked(false);
                    setControlBoolean(false);
                    ((Prestamos) entrada).setEstado(false);
                }else{
                    check.setChecked(true);
                    setControlBoolean(true);
                    ((Prestamos) entrada).setEstado(true);
                }

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
                            bd.actualizarPrestamo((Prestamos)entrada);
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
                                textoFechaReal.setText(((Prestamos) entrada).getFecha_real_devolucion());
                            }
                        });
                    }
                });
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                final Prestamos elegido = (Prestamos) pariente.getItemAtPosition(posicion);

                AlertDialog.Builder builder = new AlertDialog.Builder(ListaSimple.this);
                builder.setMessage("Cantidad: "+elegido.getCantidad()+"\n\n Descripción: \n\n "+elegido.getDescripcion())

                        .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                        .setNegativeButton("Eliminar Objeto", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(ListaSimple.this);
                                builder2.setMessage("¿Está seguro que desea eliminar el objeto? Se perderá el registro")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                datos.remove(elegido);
                                                bd.eliminarPrestamo(""+elegido.getId());
                                                ((Lista_adaptador)lista.getAdapter()).notifyDataSetChanged();
                                                Toast.makeText(ListaSimple.this, "Objeto Eliminado", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // User cancelled the dialog
                                            }
                                        });
                                // Create the AlertDialog object and return it
                                builder2.create();
                                builder2.show();
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();
            }
        });

        items = getResources().getStringArray(R.array.items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        View header= getLayoutInflater().inflate(R.layout.header, null);
        mDrawerList.addHeaderView(header);
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, items));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent, View view, int position, long id){
                try{
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            otraVista("1");
                            setTitle("Prestamos");
                            finish();
                            break;
                        case 2:
                            otraVista("2");
                            setTitle("Vencidos");
                            finish();
                            break;
                        case 3:
                            otraVista("3");
                            setTitle("Entregados");
                            finish();
                            break;
                    }
                }catch(Exception e){
                }
            }
        });
        String sel2=(String)getIntent().getExtras().get("selected");
        if(sel2.equals("1")){
            mDrawerList.setItemChecked(1, true);
        }
        if(sel2.equals("2")){
            mDrawerList.setItemChecked(2, true);
        }
        if(sel2.equals("3")){
            mDrawerList.setItemChecked(3, true);
        }
        BDPrestamos bd = new BDPrestamos(this);
        drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, R.string.abierto, R.string.cerrado) {
            @Override
            public void onDrawerClosed(View draweView){
                super.onDrawerClosed(draweView);
                ActivityCompat.invalidateOptionsMenu(ListaSimple.this);
            }
            @Override
            public void onDrawerOpened(View draweView){
                super.onDrawerOpened(draweView);
                ActivityCompat.invalidateOptionsMenu(ListaSimple.this);
            }
        };
        mDrawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    private DatePickerDialog mFechaPrestamo;

    private void seleccionarFecha() {
        Calendar calendario = Calendar.getInstance();
        mFechaPrestamo = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int anio, int mes, int dia) {
                Calendar fecha = Calendar.getInstance();
                fecha.set(anio, mes, dia);
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
                    temp.setFecha_real_devolucion("##-##-####");


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
                    paraFechaReal();
                    bd.actualizarPrestamo(temp);

                }
            }
        });
    }
    private void paraFechaReal(){
        if(validarFecha(temp.getFecha_prestamo())){
            setControlBoolean(true);
            (temp).setEstado(controlBoolean);
            (temp).setFecha_real_devolucion(fechaEntregaR);
        }else{
            setControlBoolean(false);
            (temp).setEstado(controlBoolean);
            (temp).setFecha_real_devolucion("##-##-####");
        }
    }
    public boolean validarFecha(String s){
        String[] fechaPrestamo = s.split("-");
        String[] fechaReal= fechaEntregaR.split("-");
        if (Integer.parseInt(fechaPrestamo[2]) == Integer.parseInt(fechaReal[2])) {
            if (Integer.parseInt(fechaPrestamo[1]) == Integer.parseInt(fechaReal[1])) {
                if (Integer.parseInt(fechaPrestamo[0]) <= Integer.parseInt(fechaReal[0])) {
                    //fecha correcta
                    Toast.makeText(getApplicationContext(), "primer if", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    Toast.makeText(getApplicationContext(), "Fecha inválida", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else if (Integer.parseInt(fechaPrestamo[1]) < Integer.parseInt(fechaReal[1])) {
                //fecha correcta
                Toast.makeText(getApplicationContext(), "segundo if", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "Fecha inválida", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (Integer.parseInt(fechaPrestamo[2]) < Integer.parseInt(fechaReal[2])) {
            //fecha correcta
            Toast.makeText(getApplicationContext(), "tercer if", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Fecha inválida", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void setControlBoolean(boolean bool){
        controlBoolean=bool;
    }


    public void otraVista(String s){
        intent = new Intent(this, ListaSimple.class);
        intent.putExtra("selected",s);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
}