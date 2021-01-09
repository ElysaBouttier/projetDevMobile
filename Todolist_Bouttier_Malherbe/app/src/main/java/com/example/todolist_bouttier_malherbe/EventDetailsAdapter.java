package com.example.todolist_bouttier_malherbe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class EventDetailsAdapter extends ArrayAdapter<EventDetails> {
    //details est la liste des modèles à afficher
    public EventDetailsAdapter(Context context, List<EventDetails> details) {
        super(context, 0, details);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_eventdetails, parent, false);
        }

        EventDetailsAdapter.EventDetailsViewHolder viewHolder = (EventDetailsAdapter.EventDetailsViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new EventDetailsAdapter.EventDetailsViewHolder();
            viewHolder.firstname = (TextView) convertView.findViewById(R.id.firstname);
            viewHolder.information = (TextView) convertView.findViewById(R.id.information);
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            viewHolder.eventDetailOption = (ImageButton) convertView.findViewById(R.id.eventDetailOption);
            viewHolder.eventDetailDelete = (ImageButton) convertView.findViewById(R.id.eventDetailDelete);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<EventDetails> details
        EventDetails details = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.firstname.setText(details.getFirstname());
        viewHolder.information.setText(details.getInformation());
        viewHolder.checkbox.setChecked(details.getChecked().equals("True"));

        FirebaseDatabase database = getInstance();
        DatabaseReference detailsRef = database.getReference(details.getPath() + details.getId());

        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    detailsRef.child("/checked").setValue("True");
                }
                else
                {
                    detailsRef.child("/checked").setValue("False");
                }
            }
        });

        viewHolder.eventDetailOption.setOnClickListener((view) -> {
            Intent eventDetailOptionIntent = new Intent(this.getContext(), AddEventDetailActivity.class);
            eventDetailOptionIntent.putExtra("elementId", details.getId());
            eventDetailOptionIntent.putExtra("eventId", details.getPath().substring(7, 27));
            this.getContext().startActivity(eventDetailOptionIntent);
        });

        viewHolder.eventDetailDelete.setOnClickListener((view) -> {
            database.getReference(details.getPath() + details.getId()).removeValue();
        });

        return convertView;
    }

    private class EventDetailsViewHolder{
        public TextView firstname;
        public TextView information;
        public CheckBox checkbox;
        public ImageButton eventDetailOption;
        public ImageButton eventDetailDelete;
    }
}
