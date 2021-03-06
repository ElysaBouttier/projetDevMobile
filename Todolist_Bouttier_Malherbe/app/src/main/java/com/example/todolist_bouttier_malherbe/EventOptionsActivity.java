package com.example.todolist_bouttier_malherbe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class EventOptionsActivity extends AppCompatActivity {
    private EditText textEdit;
    private CalendarView calendarView;
    private Button addButton;
    private Button deleteButton;
    private String path = "";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_options);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        textEdit = findViewById(R.id.textEdit);
        calendarView = findViewById(R.id.calendarView);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Set default date on calendar to now
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        selectedDate = df.format(c);
        try {
            calendarView.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(String.valueOf(Calendar.getInstance().getTime())).getTime(), true, true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Get date of calendar
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                //selectedDate = String.valueOf(dayOfMonth);
                String stringDay;
                String stringMonth;

                if(month+1 < 10) {
                    stringMonth = "-0" + (month + 1);
                } else {
                    stringMonth = "-" + (month + 1);
                }
                if (dayOfMonth < 10){
                    stringDay = "-0" + dayOfMonth;
                } else {
                    stringDay = "-" + dayOfMonth;
                }
                selectedDate = stringDay + "-" + stringMonth + "-" + year;
            }
        });

        // Checks if the event exists already
        if(getIntent().getStringExtra("id") != null){
            addButton.setText("Modifier");
            path = "events/" + getIntent().getStringExtra("id");
            getDataFromDb(path);
            // Add button action
            addButton.setOnClickListener((view) -> {
                DatabaseReference eventRef = database.getReference(path);
                DatabaseReference eventNameRef = eventRef.child("name");
                DatabaseReference eventDateRef = eventRef.child("date");
                eventNameRef.setValue(textEdit.getText().toString());
                eventDateRef.setValue(selectedDate);
                // Return to MainActivity
                returnToMainActivity();
            });
            // Delete button action
            deleteButton.setOnClickListener((view) -> {
                DatabaseReference eventRef = database.getReference(path);
                eventRef.removeValue();
                // Return to MainActivity
                returnToMainActivity();
            });
        } else {
            deleteButton.setText("Annuler");
            // Add button action
            addButton.setOnClickListener((view) -> {
                DatabaseReference eventsRef = database.getReference("events/");
                DatabaseReference newEvent = eventsRef.push();
                String newEventId = newEvent.getKey();
                newEvent.setValue(new Event(textEdit.getText().toString(), selectedDate, newEventId));
                DatabaseReference newEventElements = database.getReference("events/" + newEventId + "/elements");
                newEventElements.child("length").setValue(0);

                // eventsRef.child(newEventId).child("elements").setValue("");
                // Return to MainActivity
                returnToMainActivity();
            });
            // Delete button action
            deleteButton.setOnClickListener((view) -> {
                // Return to MainActivity
                returnToMainActivity();
            });
        }
    }

    private void getDataFromDb(String path){
        // Connect to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(path);
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            private static final String TAG = "OptActivity-DB Access";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                assert value != null;
                textEdit.setText((String) value.get("name"));
                selectedDate = (String) value.get("date");
                try {
                    calendarView.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(selectedDate).getTime(), true, true);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void returnToMainActivity(){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        returnToMainActivity();
        return true;
    }
}