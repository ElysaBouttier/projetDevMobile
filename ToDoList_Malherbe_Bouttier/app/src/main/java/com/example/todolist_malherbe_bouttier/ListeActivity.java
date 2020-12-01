package com.example.todolist_malherbe_bouttier;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ListeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);
    }
}


// Ajouter une bdd
//        btn.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//       DatabaseReference myRef = database.getReference("message");
//        myRef.setValue(texte.getText().toString());
//        }
//        });

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
 //       myRef.addValueEventListener(new ValueEventListener() {
// @Override
// public void onDataChange(DataSnapshot dataSnapshot) {
//        String value = dataSnapshot.getValue(String.class);
  //      Log.d("APPX", "Value is: " + value);
//        texte.setText(value);
//        }
// @Override
// public void onCancelled(DatabaseError error) {
 //       Log.w("APPX", "Failed to read value.", error.toException());
 //       }