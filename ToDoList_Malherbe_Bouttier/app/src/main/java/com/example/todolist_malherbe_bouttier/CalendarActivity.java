package com.example.todolist_malherbe_bouttier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity {

    public EditText eventToAdd;
    public Button btnToAdd;
    public Button btnToDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // DECLARE VARIABLES
        eventToAdd = (EditText) findViewById(R.id.txt_event_name_to_add);

        btnToAdd = (Button) findViewById(R.id.btn_add_to_calendar);
        btnToDelete = (Button) findViewById(R.id.btn_delete_from_calendar);

        // ---------- EVENT ----------
        btnToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!eventToAdd.getText().toString().isEmpty()){
                    // -----------------
                    //  Mettre infos dans database
                    // -----------------
                } else {
                    Toast.makeText(CalendarActivity.this,
                            "Veuillez remplir le champs 'Nom du cadeaux' ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}