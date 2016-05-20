package com.example.josediego.tap_u5_proyectoandroid.BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BDPrestamos extends SQLiteOpenHelper{

    private static final String DB_NAME = "Base_Datos_Prestamos";
    private static final int SCHEME_VERSION = 1;
    private SQLiteDatabase db;

    public BDPrestamos(Context context) {
        super(context, DB_NAME, null, SCHEME_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Prestamos.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertarPrestamos(Prestamos prestamo) {

        ContentValues valores = new ContentValues();

        valores.put(Prestamos.CLIENTE_NOMBRE, prestamo.getCliente_nombre());
        valores.put(Prestamos.OBJETO_NOMBRE, prestamo.getObjeto_nombre());
        valores.put(Prestamos.CANTIDAD, prestamo.getCantidad());
        valores.put(Prestamos.FECHA_PRESTAMO, prestamo.getFecha_prestamo());
        valores.put(Prestamos.FECHA_DEVOLUCION, prestamo.getFecha_devolucion());
        valores.put(Prestamos.ESTADO, prestamo.isEstado());
        valores.put(Prestamos.DESCRIPCION, prestamo.getDescripcion());

        db.insert(Prestamos.TABLA_NOMBRE, null, valores);
        return true;
    }

    public ArrayList<Prestamos> obtenerTodos(){
        ArrayList<Prestamos> prestamos = new ArrayList<>();

        String columnas[] = {Prestamos.CLIENTE_NOMBRE, Prestamos.OBJETO_NOMBRE, Prestamos.CANTIDAD, Prestamos.FECHA_PRESTAMO
        ,Prestamos.FECHA_DEVOLUCION, Prestamos.ESTADO, Prestamos.DESCRIPCION};

        Cursor c = db.query(Prestamos.TABLA_NOMBRE, columnas,null,null,null,null,null);

        if(c.moveToFirst()){
            do{
                Prestamos p = new Prestamos();
                p.setCliente_nombre(c.getString(0));
                p.setObjeto_nombre(c.getString(1));
                p.setCantidad(c.getInt(2));
                p.setFecha_prestamo(c.getString(3));
                p.setFecha_devolucion(c.getString(4));
                p.setEstado(Boolean.parseBoolean(c.getString(5)));
                p.setDescripcion(c.getString(6));
                prestamos.add(p);
            }while(c.moveToNext());
        }
        return prestamos;
    }

   /* public void actualizarPrestamos(Prestamos prestamo){
        ContentValues valores = new ContentValues();

        valores.put(Prestamos.CLIENTE_NOMBRE, prestamo.getCliente_nombre());
        valores.put(Prestamos.OBJETO_NOMBRE, prestamo.getObjeto_nombre());
        valores.put(Prestamos.CANTIDAD, prestamo.getCantidad());
        valores.put(Prestamos.FECHA_PRESTAMO, prestamo.getFecha_prestamo());
        valores.put(Prestamos.FECHA_DEVOLUCION, prestamo.getFecha_devolucion());
        valores.put(Prestamos.ESTADO, prestamo.isEstado());
        valores.put(Prestamos.DESCRIPCION, prestamo.getDescripcion());

        String whereClause = String.format("%s=?", CabecerasPedido.ID_CABECERA_PEDIDO);
        String[] whereArgs = {pedidoNuevo.idCabeceraPedido};

        db.update(Prestamos.TABLA_NOMBRE, valores, whereClaus, whereArgs);
    }*/


}
