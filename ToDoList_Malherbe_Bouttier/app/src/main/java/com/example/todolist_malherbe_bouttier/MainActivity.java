package com.example.todolist_malherbe_bouttier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    public void showMenu(View v) {
        PopupMenu menuAdd = new PopupMenu(this, v);
        menuAdd.setOnMenuItemClickListener(this);
        menuAdd.inflate(R.menu.menu_main_activity);
        menuAdd.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to_calendar:
                Toast.makeText(this, "Aller vers le calendrier", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return false;

        }
    }
}