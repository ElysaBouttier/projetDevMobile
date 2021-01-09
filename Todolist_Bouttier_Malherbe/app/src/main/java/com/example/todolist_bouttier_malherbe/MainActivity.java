package com.example.todolist_bouttier_malherbe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private EventAdapter adapter;
    private ImageButton addEventButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listView);
        getDataFromDb();

        addEventButton = findViewById(R.id.addEvent);
        addEventButton.setOnClickListener((view) -> {
            Intent optionIntent = new Intent(this, EventOptionsActivity.class);
            this.startActivity(optionIntent);
        });
    }
    // ------------------------------------------------------------------------
    //                               CREATE MENU
    // ------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_on_top, menu);
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.about_choice:
                Toast.makeText(this, "Redirection vers la page de présentation", Toast.LENGTH_SHORT).show();
                goToAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ------------------------------------------------------------------------
    //                         ACCESS TO FIREBASE
    // ------------------------------------------------------------------------
    private void getDataFromDb(){
        // Connect to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("events");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            private static final String TAG = "MainActivity-DB Access";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + map);
                List<Event> events = new ArrayList<Event>();
                for(Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    HashMap value = (HashMap)entry.getValue();
                    events.add(new Event((String) value.get("name"), (String) value.get("date"), key));
                }
                adapter = new EventAdapter(MainActivity.this, events);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    // ------------------------------------------------------------------------
    // ----------------------------METHODS-------------------------------------
    // ------------------------------------------------------------------------

    // Redirection to About page
    public void goToAbout(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    // Confirmation before exit the app
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Voulez vous quitter ToDoList?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        finish();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}