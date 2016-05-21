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
        valores.put(Prestamos.FECHA_REAL_DEVOLUCION, prestamo.getFecha_real_devolucion());
        valores.put(Prestamos.ESTADO, prestamo.isEstado());
        valores.put(Prestamos.DESCRIPCION, prestamo.getDescripcion());

        db.insert(Prestamos.TABLA_NOMBRE, null, valores);
        return true;
    }

    public ArrayList<Prestamos> obtenerTodos(){
        ArrayList<Prestamos> prestamos = new ArrayList<>();

        String columnas[] = {Prestamos.ID, Prestamos.CLIENTE_NOMBRE, Prestamos.OBJETO_NOMBRE, Prestamos.CANTIDAD, Prestamos.FECHA_PRESTAMO
        , Prestamos.FECHA_DEVOLUCION, Prestamos.FECHA_REAL_DEVOLUCION, Prestamos.ESTADO, Prestamos.DESCRIPCION};

        Cursor c = db.query(Prestamos.TABLA_NOMBRE, columnas,null,null,null,null,null);

        if(c.moveToFirst()){
            do{
                Prestamos p = new Prestamos();
                p.setId(c.getInt(0));
                p.setCliente_nombre(c.getString(1));
                p.setObjeto_nombre(c.getString(2));
                p.setCantidad(c.getInt(3));
                p.setFecha_prestamo(c.getString(4));
                p.setFecha_devolucion(c.getString(5));
                p.setFecha_real_devolucion(c.getString(6));
                p.setEstado(Boolean.parseBoolean(c.getString(7)));
                p.setDescripcion(c.getString(8));
                prestamos.add(p);
            }while(c.moveToNext());
        }
        return prestamos;
    }

    public void actualizarPrestamo(Prestamos prestamo){
        prestamo.setEstado(!prestamo.getFecha_real_devolucion().equals("##-##-####"));

        ContentValues valores = new ContentValues();

        valores.put(Prestamos.CLIENTE_NOMBRE, prestamo.getCliente_nombre());
        valores.put(Prestamos.OBJETO_NOMBRE, prestamo.getObjeto_nombre());
        valores.put(Prestamos.CANTIDAD, prestamo.getCantidad());
        valores.put(Prestamos.FECHA_PRESTAMO, prestamo.getFecha_prestamo());
        valores.put(Prestamos.FECHA_DEVOLUCION, prestamo.getFecha_devolucion());
        valores.put(Prestamos.FECHA_REAL_DEVOLUCION, prestamo.getFecha_real_devolucion());
        valores.put(Prestamos.ESTADO, prestamo.isEstado());
        valores.put(Prestamos.DESCRIPCION, prestamo.getDescripcion());

        String whereClause = String.format("%s=?", Prestamos.ID);
        String[] whereArgs = {prestamo.getId()+""};

        db.update(Prestamos.TABLA_NOMBRE, valores, whereClause, whereArgs);
    }

    public boolean eliminarPrestamo(String id) {

        String whereClause = Prestamos.ID + "=?";
        String[] whereArgs = {id};

        int resultado = db.delete(Prestamos.TABLA_NOMBRE, whereClause, whereArgs);

        return resultado > 0;
    }

    public ArrayList<Prestamos> obtenerVencidos(){
        ArrayList<Prestamos> prestamos = new ArrayList<>();

        String sql = "SELECT * FROM "+Prestamos.TABLA_NOMBRE;

        Cursor c = db.rawQuery(sql,null);

        if(c.moveToFirst()){
            do{
                Prestamos p = new Prestamos();
                p.setId(c.getInt(0));
                p.setCliente_nombre(c.getString(1));
                p.setObjeto_nombre(c.getString(2));
                p.setCantidad(c.getInt(3));
                p.setFecha_prestamo(c.getString(4));
                p.setFecha_devolucion(c.getString(5));
                p.setFecha_real_devolucion(c.getString(6));
                p.setEstado(Boolean.parseBoolean(c.getString(7)));
                p.setDescripcion(c.getString(8));

                if(p.estaVencido()){ prestamos.add(p); }

            }while(c.moveToNext());
        }
        return prestamos;
    }

    public ArrayList<Prestamos> obtenerEntregados(){
        ArrayList<Prestamos> prestamos = new ArrayList<>();

        String sql = "SELECT * FROM "+Prestamos.TABLA_NOMBRE+" WHERE "+Prestamos.ESTADO+" = 1";

        Cursor c = db.rawQuery(sql,null);

        return obtenerArray(c);
    }

    public ArrayList<Prestamos> buscar(String nombre){

        String sql = "SELECT* FROM "+Prestamos.TABLA_NOMBRE+" WHERE "+Prestamos.CLIENTE_NOMBRE+" LIKE  '%"+nombre
                     +"%' OR "+Prestamos.OBJETO_NOMBRE+" LIKE '%"+nombre+"%'";

        Cursor c = db.rawQuery(sql,null);

        return obtenerArray(c);
    }

    public ArrayList<Prestamos> obtenerArray(Cursor c){
        ArrayList<Prestamos> prestamos = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                Prestamos p = new Prestamos();
                p.setId(c.getInt(0));
                p.setCliente_nombre(c.getString(1));
                p.setObjeto_nombre(c.getString(2));
                p.setCantidad(c.getInt(3));
                p.setFecha_prestamo(c.getString(4));
                p.setFecha_devolucion(c.getString(5));
                p.setFecha_real_devolucion(c.getString(6));
                p.setEstado(Boolean.parseBoolean(c.getString(7)));
                p.setDescripcion(c.getString(8));
                prestamos.add(p);
            }while(c.moveToNext());
        }
        return prestamos;
    }

}
