package com.example.josediego.tap_u5_proyectoandroid;

/**
 * Created by manuel on 18/05/16.
 */
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
public class DrawerItemClickListener extends Activity implements ListView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        selectItem(position,view);
    }
    private void selectItem(int position, View view) {
        FragmentManager fragmentManager = getFragmentManager();
        switch (position) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            default:

                break;
        }
    }
}