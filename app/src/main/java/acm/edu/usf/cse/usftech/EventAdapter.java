package acm.edu.usf.cse.usftech;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<Event> {
    private final Context mContext;
    private final ArrayList<Event> mEvents;

    private static class ViewHolder {
        private TextView itemView;
    }

    public EventAdapter(Context context, int textViewResourceId, ArrayList<Event> items) {
        super(context, textViewResourceId, items);
        this.mContext = context;
        this.mEvents = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.events_list_item, parent, false);

        final Event[] eventArray = mEvents.toArray(new Event[mEvents.size()]);

        TextView textName = (TextView) rowView.findViewById(R.id.txtName);
        TextView textOrg = (TextView) rowView.findViewById(R.id.txtOrg);
        TextView textLoc = (TextView) rowView.findViewById(R.id.txtLocation);
        TextView textStart = (TextView) rowView.findViewById(R.id.txtStart);
        TextView textEnd = (TextView) rowView.findViewById(R.id.txtEnd);
        TextView textDesc = (TextView) rowView.findViewById(R.id.txtDesc);

        textName.setText(eventArray[position].getName());
        textOrg.setText(eventArray[position].getOrgs());
        textLoc.setText(eventArray[position].getLocation());
        textStart.setText(eventArray[position].getStartTime());
        textEnd.setText(eventArray[position].getEndTime());
        textDesc.setText(eventArray[position].getDesc());

        /*rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(eventArray[position].getURIString()));
                getContext().startActivity(browserIntent);
            }
        });*/

        return rowView;
    }
}