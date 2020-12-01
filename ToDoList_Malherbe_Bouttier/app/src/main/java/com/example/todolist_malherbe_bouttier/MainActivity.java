package com.example.todolist_malherbe_bouttier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    // DECLARE VARIABLES
    private Button btnEvent1;
    private Button btnOptionEvent1;
    private Button btnEvent2;
    private Button btnOptionEvent2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INIT VARIABLES
        btnEvent1 = (Button) findViewById(R.id.btn_ev);
        btnEvent2 = (Button) findViewById(R.id.btn_event2);


        // --------------- EVENT LISTENER -------------------
        // Change Activity

        // Go to ListeActivity
        btnEvent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity();
            }
        });

    }
    // Go to Calendar Activity
    public void showMenu(View v){
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu_main_activity);
        popupMenu.show();
    }

    //Menu for calendar access
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to_calendar:

                // when clic on calendar button : show this message
                Toast.makeText(this, "Aller vers le calendrier", Toast.LENGTH_SHORT).show();
                goToCalendar();
                return true;
            default:
                return false;

        }
    }


    //------------------- FUNCTIONS ----------------------
    // Change Activity
    private void changeActivity(){
        Intent intent = new Intent(MainActivity.this, ListeActivity.class);
        startActivity(intent);
    }
    //
    private void goToCalendar(){
        Intent calendrar = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(calendrar);
    }
}