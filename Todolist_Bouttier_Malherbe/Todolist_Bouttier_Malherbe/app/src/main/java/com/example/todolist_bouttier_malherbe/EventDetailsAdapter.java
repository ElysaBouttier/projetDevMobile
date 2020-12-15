package com.example.todolist_bouttier_malherbe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

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
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<EventDetails> details
        EventDetails details = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.firstname.setText(details.getFirstname());
        viewHolder.information.setText(details.getInformation());
        viewHolder.checkbox.setChecked(details.getChecked().equals("True"));

        return convertView;
    }

    private class EventDetailsViewHolder{
        public TextView firstname;
        public TextView information;
        public CheckBox checkbox;
    }
}
