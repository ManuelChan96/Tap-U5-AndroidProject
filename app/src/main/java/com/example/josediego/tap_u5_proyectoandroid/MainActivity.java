package com.example.josediego.tap_u5_proyectoandroid;

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
import android.widget.TableLayout;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.josediego.tap_u5_proyectoandroid.BaseDeDatos.BDPrestamos;
import com.example.josediego.tap_u5_proyectoandroid.BaseDeDatos.Prestamos;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String[] items;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle drawerToggle;
    private Intent intent;
    private ArrayList<Prestamos> recientes= new ArrayList<Prestamos>();
    private BDPrestamos bd;
    Tabla tabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabla= new Tabla(this, (TableLayout) findViewById(R.id.tabla));
        tabla.agregarCabecera(R.array.cabecera_tabla);
        bd= new BDPrestamos(this);
        recientes.addAll(bd.obtenerRecientes());
        for (int i=0; i<recientes.size(); i++) {
            ArrayList<String> elementos = new ArrayList<String>();
            elementos.add(recientes.get(i).getCliente_nombre());
            elementos.add(recientes.get(i).getObjeto_nombre());
            elementos.add(recientes.get(i).getFecha_prestamo());
            elementos.add(recientes.get(i).getFecha_devolucion());
            tabla.agregarFilaTabla(elementos);
        }
        items = getResources().getStringArray(R.array.items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        View header = getLayoutInflater().inflate(R.layout.header, null);
        mDrawerList.addHeaderView(header);
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, items));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                try {
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            otraVista("1");
                            mDrawerList.setItemChecked(1, false);
                            mDrawerLayout.closeDrawer(mDrawerList);
                            break;
                        case 2:
                            otraVista("2");
                            mDrawerList.setItemChecked(2, false);
                            mDrawerLayout.closeDrawer(mDrawerList);
                            break;
                        case 3:
                            otraVista("3");
                            mDrawerList.setItemChecked(3, false);
                            mDrawerLayout.closeDrawer(mDrawerList);
                            break;
                    }
                } catch (Exception e) {
                }
            }
        });
        BDPrestamos bd = new BDPrestamos(this);
        drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, R.string.abierto, R.string.cerrado) {
            @Override
            public void onDrawerClosed(View draweView) {
                super.onDrawerClosed(draweView);
                ActivityCompat.invalidateOptionsMenu(MainActivity.this);
            }

            @Override
            public void onDrawerOpened(View draweView) {
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();

                intent = new Intent(MainActivity.this, ListaSimple.class);
                intent.putExtra("selected","busqueda");
                intent.putExtra("search",query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
