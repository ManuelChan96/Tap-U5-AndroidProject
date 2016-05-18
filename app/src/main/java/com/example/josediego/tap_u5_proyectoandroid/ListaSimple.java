package com.example.josediego.tap_u5_proyectoandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaSimple extends AppCompatActivity {
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);
        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
        for (int i=0;i<=30;i++){
            datos.add(new Lista_entrada("Objeto "+ i, "DATOS \n" +
                    "Lorem ipsum dolor sit am "+i));
        }
        lista=(ListView)findViewById(R.id.ListView_listado);
        lista.setAdapter(new Lista_adaptador(this,R.layout.entrada,datos) {
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada!=null){
                    TextView textoIni=(TextView)view.findViewById(R.id.textView_superior);
                    TextView textoFin=(TextView)view.findViewById(R.id.textView_inferior);
                    textoIni.setText(((Lista_entrada)entrada).get_textoEncima());
                    textoFin.setText(((Lista_entrada)entrada).get_textoDebajo());

                }
            }
        });


    }
    public void cambio(String s){
        Toast toast = Toast.makeText(getApplicationContext(), "Cambio desde "+s+ " :D " , Toast.LENGTH_SHORT);
        toast.show();
    }
}
