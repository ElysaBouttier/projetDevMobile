package com.example.todolist_malherbe_bouttier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import static com.example.todolist_malherbe_bouttier.R.menu.menu_main_activity;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }




    // ------------   MENU  --------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(menu_main_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.go_to_calendar:
                Toast.makeText(this, "Affichage du calendrier", Toast.LENGTH_SHORT).show();
                goToCalendar();
                return true;
            case R.id.go_to_about:
                Toast.makeText(this, "Affichage de l'A propos", Toast.LENGTH_SHORT).show();
                goToAbout();
                return true;
        }
        return false;
    }





    // ------------- FUNCTIONS ---------------
    private void goToCalendar() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
    private void goToAbout(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
    private void goToListe() {
        Intent intent = new Intent(this, ListeActivity.class);
        startActivity(intent);
    }



}
