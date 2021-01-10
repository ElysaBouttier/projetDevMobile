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

    // DECLARE PRIVATE VARIABLES
    private String eventId;
    private Button OKButton;
    private Button cancelButton;
    private EditText firstnameInput;
    private EditText informationInput;
    private Intent intent;
    private FirebaseDatabase database;
    private DatabaseReference eventElementsRef;
    private DatabaseReference eventDetailRef;

    // ------------------------------------------------------------------------
    // ----------------------------onCreate-------------------------------------
    // ------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_detail);

        // Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // INITIATE VARIABLES
        OKButton = (Button)findViewById(R.id.addEventDetailConfirm);
        cancelButton = (Button)findViewById(R.id.addEventDetailCancel);
        firstnameInput = (EditText)findViewById(R.id.firstameInput);
        informationInput = (EditText)findViewById(R.id.informationInput);
        intent = getIntent();
        eventId = intent.getStringExtra("eventId");
        String path = "events/" + eventId + "/elements/";
        database = FirebaseDatabase.getInstance();
        eventElementsRef = database.getReference(path);

        if(intent.getStringExtra("elementId") != null){
            // In fireBase
            String elementId = intent.getStringExtra("elementId");
            eventDetailRef = eventElementsRef.child(elementId);
            eventDetailRef.addValueEventListener(new ValueEventListener() {
                private static final String TAG = "AddEventDetail-DBAccess";


                // Read Database
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

            // Add value in Database
            OKButton.setOnClickListener((view) -> {
                eventDetailRef.child("firstname").setValue(firstnameInput.getText().toString());
                eventDetailRef.child("information").setValue(informationInput.getText().toString());

                returnToEventDetailActivity();
            });
        } else {
            OKButton.setOnClickListener((view) -> {
                DatabaseReference newElement = eventElementsRef.push();
                String elementId = newElement.getKey();
                newElement.setValue(new EventDetails(firstnameInput.getText().toString(),
                        informationInput.getText().toString(),
                        elementId, "False", path));

                returnToEventDetailActivity();
            });
        }

        // Database isn't change
        cancelButton.setOnClickListener((view) -> {
            returnToEventDetailActivity();
        });
    }

    // ------------------------------------------------------------------------
    // ----------------------------METHODS-------------------------------------
    // ------------------------------------------------------------------------

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