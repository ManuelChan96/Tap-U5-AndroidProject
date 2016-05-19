package com.example.josediego.tap_u5_proyectoandroid;

import java.util.GregorianCalendar;

/**
 * Created by manuel on 16/05/16.
 */
public class Lista_entrada {
    private String objeto;
    private String persona;
    private String fechaPrestamo;
    private String fechaDevolucion;
    private int id;
    private boolean entregado;

    public Lista_entrada (String textoEncima, String textoDebajo, int id, boolean entregado,String fechaPrestamo ,String fechaDevolucion) {
        this.objeto = textoEncima;
        this.persona = textoDebajo;
        this.id=id;
        this.entregado=entregado;
        this.fechaPrestamo=fechaPrestamo;
        this.fechaDevolucion=fechaDevolucion;
    }

    public String get_textoEncima() {
        return objeto;
    }
    public String get_textoDebajo() {
        return persona;
    }
    public int get_id() {
        return id;
    }
    public boolean getEntregado(){
        return entregado;
    }
    public void setEntregado(boolean v){
        entregado = v;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }
}

