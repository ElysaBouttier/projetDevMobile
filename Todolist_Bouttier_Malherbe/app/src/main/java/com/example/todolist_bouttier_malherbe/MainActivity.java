package com.example.todolist_bouttier_malherbe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listView);
        getDataFromDb();

        Button addEventButton = findViewById(R.id.addEvent);
        addEventButton.setOnClickListener((view) -> {
            Intent optionIntent = new Intent(this, EventOptionsActivity.class);
            this.startActivity(optionIntent);
        });
    }

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
}