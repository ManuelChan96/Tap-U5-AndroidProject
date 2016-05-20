package com.example.josediego.tap_u5_proyectoandroid.BaseDeDatos;


public class Prestamos {
    public static final String TABLA_NOMBRE = "Prestamos";
    public static final String FECHA_PRESTAMO = "fecha_prestamo";
    public static final String FECHA_DEVOLUCION = "fecha_devolucion";
    public static final String CANTIDAD = "cantidad";
    public static final String CLIENTE_NOMBRE = "cliente_nombre";
    public static final String OBJETO_NOMBRE = "objeto_nombre";
    public static final String ESTADO = "estado";
    public static final String DESCRIPCION = "descripcion";
    public static final String CREATE_TABLE = "CREATE TABLE "+TABLA_NOMBRE+"( "+CLIENTE_NOMBRE+" TEXT NOT NULL, "+OBJETO_NOMBRE+" TEXT NOT NULL, "
                                                                   +CANTIDAD+" INT NOT NULL, "+FECHA_PRESTAMO+" TEXT NOT NULL, "+FECHA_DEVOLUCION+" TEXT NOT NULL, "+ESTADO+" BOOLEAN NOT NULL, "
                                                                    +DESCRIPCION+" TEXT NOT NULL)";

    private int id;
    private String fecha_prestamo;
    private String fecha_devolucion;
    private int cantidad;
    private String cliente_nombre;
    private String objeto_nombre;
    private String descripcion;
    private boolean estado;

    public Prestamos(String objeto_nombre, String cliente_nombre, int cantidad, String fecha_devolucion, String fecha_prestamo, String descripcion) {
        this.objeto_nombre = objeto_nombre;
        this.cliente_nombre = cliente_nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.fecha_devolucion = fecha_devolucion;
        this.fecha_prestamo = fecha_prestamo;
        estado = false;
    }

    public Prestamos() {
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObjeto_nombre() {
        return objeto_nombre;
    }

    public void setObjeto_nombre(String objeto_nombre) {
        this.objeto_nombre = objeto_nombre;
    }

    public String getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(String fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCliente_nombre() {
        return cliente_nombre;
    }

    public void setCliente_nombre(String cliente_nombre) {
        this.cliente_nombre = cliente_nombre;
    }

    public String getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(String fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }
}