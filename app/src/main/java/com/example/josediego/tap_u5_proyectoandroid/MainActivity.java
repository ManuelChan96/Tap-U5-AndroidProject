package com.example.josediego.tap_u5_proyectoandroid;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TableLayout;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.josediego.tap_u5_proyectoandroid.BaseDeDatos.BDPrestamos;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String[] items;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle drawerToggle;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tabla tabla = new Tabla(this, (TableLayout)findViewById(R.id.tabla));
        tabla.agregarCabecera(R.array.cabecera_tabla);
        for(int i = 0; i < 15; i++){
            ArrayList<String> elementos = new ArrayList<String>();
            elementos.add(Integer.toString(i));
            elementos.add("Casilla [" + i + ", 0]");
            elementos.add("Casilla [" + i + ", 1]");
            elementos.add("Casilla [" + i + ", 2]");
            elementos.add("Casilla [" + i + ", 3]");
            tabla.agregarFilaTabla(elementos);
        }

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
                            otraVista("prestamos");
                            break;
                        case 2:
                            otraVista("vencidos");
                            break;
                        case 3:
                            otraVista("entregados");
                            break;
                    }
                }catch(Exception e){
                }
            }
        });
        BDPrestamos bd = new BDPrestamos(this);
        drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, R.string.abierto, R.string.cerrado) {
            @Override
            public void onDrawerClosed(View draweView){
                super.onDrawerClosed(draweView);
                ActivityCompat.invalidateOptionsMenu(MainActivity.this);
            }
            @Override
            public void onDrawerOpened(View draweView){
                super.onDrawerOpened(draweView);
                ActivityCompat.invalidateOptionsMenu(MainActivity.this);
            }
        };
        mDrawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem searchItem= menu.findItem(R.id.menu_buscar);
        final SearchView searchView= (SearchView) MenuItemCompat.getActionView(searchItem);
        final MenuItem addItem= menu.findItem(R.id.new_Object_id);
        addItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                agregarNuevo();
                return true;
            }
        });
        return true;
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    public void agregarNuevo(){
        intent = new Intent(this, NewObject.class);
        startActivity(intent);
    }
}
