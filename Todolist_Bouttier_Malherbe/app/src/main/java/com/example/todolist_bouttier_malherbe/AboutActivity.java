package com.example.todolist_bouttier_malherbe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    // DECLARE PRIVATE VARIABLES
    private TextView textAbout;

    // ------------------------------------------------------------------------
    // ----------------------------onCreate-------------------------------------
    // ------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        textAbout = (TextView)findViewById(R.id.txt_about);

        // Back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Show the text from Ressources
        String text = getResources().getString(R.string.about_text);
        textAbout.setText(text);
    }


    // Back Button
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}