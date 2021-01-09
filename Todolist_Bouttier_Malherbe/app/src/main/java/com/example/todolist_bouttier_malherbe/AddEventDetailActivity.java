package com.example.todolist_bouttier_malherbe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class AddEventDetailActivity extends AppCompatActivity {
    private String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button OKButton = findViewById(R.id.addEventDetailConfirm);
        Button cancelButton = findViewById(R.id.addEventDetailCancel);

        EditText firstnameInput = findViewById(R.id.firstameInput);
        EditText informationInput = findViewById(R.id.informationInput);

        Intent intent = getIntent();
        eventId = intent.getStringExtra("eventId");
        String path = "events/" + eventId + "/elements/";

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventElementsref = database.getReference(path);

        if(intent.getStringExtra("elementId") != null){
            String elementId = intent.getStringExtra("elementId");

            DatabaseReference eventDetailRef = eventElementsref.child(elementId);

            eventDetailRef.addValueEventListener(new ValueEventListener() {
                private static final String TAG = "AddEventDetail-DBAccess";

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    firstnameInput.setText((String) map.get("firstname"));
                    informationInput.setText((String) map.get("information"));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    }
            });

            OKButton.setOnClickListener((view) -> {
                eventDetailRef.child("firstname").setValue(firstnameInput.getText().toString());
                eventDetailRef.child("information").setValue(informationInput.getText().toString());

                returnToEventDetailActivity();
            });
        } else {
            OKButton.setOnClickListener((view) -> {
                DatabaseReference newElement = eventElementsref.push();
                String elementId = newElement.getKey();
                newElement.setValue(new EventDetails(firstnameInput.getText().toString(),
                        informationInput.getText().toString(),
                        elementId, "False", path));
                returnToEventDetailActivity();
            });
        }

        cancelButton.setOnClickListener((view) -> {
            returnToEventDetailActivity();
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent eventDetailIntent = new Intent(getApplicationContext(), EventDetailActivity.class);
        eventDetailIntent.putExtra("id", eventId);
        startActivityForResult(eventDetailIntent, 0);
        return true;
    }

    public void returnToEventDetailActivity(){
        Intent eventIntent = new Intent(getApplicationContext(), EventDetailActivity.class);
        eventIntent.putExtra("id", eventId);
        startActivityForResult(eventIntent, 0);
    }
}