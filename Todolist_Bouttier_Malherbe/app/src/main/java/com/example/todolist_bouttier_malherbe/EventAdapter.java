package com.example.todolist_bouttier_malherbe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {

    //events est la liste des models à afficher
    public EventAdapter(Context context, List<Event> events) {
        super(context, 0, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_event,parent, false);
        }

        EventViewHolder viewHolder = (EventViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new EventViewHolder();
            viewHolder.name = (Button) convertView.findViewById(R.id.name);
            viewHolder.event_date = (TextView) convertView.findViewById(R.id.event_date);
            viewHolder.event_options = (Button) convertView.findViewById(R.id.event_options);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Event event = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.name.setText(event.getName());
        viewHolder.name.setOnClickListener((view) -> {
            //Envoyer vers l'activity event details avec le path
            Log.i("EventListView", "You clicked Item with path:" + event.getId());
            // Then you start a new Activity via Intent
            Intent intent = new Intent();
            intent.setClass(this.getContext(), EventDetailActivity.class);
            intent.putExtra("id", event.getId());
            this.getContext().startActivity(intent);
        });
        viewHolder.event_date.setText(event.getDate());
        viewHolder.event_options.setOnClickListener((view) -> {
            //Mettre un menu déroulant avec modifier et supprimer
            Log.i("EventListView", "You clicked Item Options with path:" + event.getId());
            // Then you start a new Activity via Intent
            Intent intent = new Intent();
            intent.setClass(this.getContext(), EventOptionsActivity.class);
            intent.putExtra("id", event.getId());
            this.getContext().startActivity(intent);
        });

        return convertView;
    }

    private class EventViewHolder{
        public Button name;
        public TextView event_date;
        public Button event_options;
    }
}
