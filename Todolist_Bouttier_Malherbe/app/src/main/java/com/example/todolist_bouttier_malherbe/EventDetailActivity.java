package com.example.todolist_bouttier_malherbe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
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

public class EventDetailActivity extends AppCompatActivity {
    private ListView mListView;
    private EventDetailsAdapter adapter;
    private String path;
    public String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = findViewById(R.id.listView);

        Intent intent = getIntent();
        eventId = intent.getStringExtra("id");
        path = "events/" + eventId + "/elements/";

        ImageButton addEventDetailButton = findViewById(R.id.addEventDetailButton);
        addEventDetailButton.setOnClickListener((view) -> {
            Intent addEventDetailIntent = new Intent(this, AddEventDetailActivity.class);
            addEventDetailIntent.putExtra("eventId", eventId);
            this.startActivity(addEventDetailIntent);
        });

        getDataFromDb();
    }

    private void getDataFromDb(){
        // Connect to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            private static final String TAG = "EDActivity - DB Access";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + map);
                List<EventDetails> details = new ArrayList<EventDetails>();
                for(Map.Entry<String, Object> entry : map.entrySet()) {
                    String key = entry.getKey();
                    HashMap value = (HashMap)entry.getValue();
                    details.add(new EventDetails((String) value.get("firstname"),
                            (String) value.get("information"),
                            key,
                            (String) value.get("checked"),
                            path));
                }
                adapter = new EventDetailsAdapter(EventDetailActivity.this, details);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(mainIntent, 0);
        return true;
    }
}