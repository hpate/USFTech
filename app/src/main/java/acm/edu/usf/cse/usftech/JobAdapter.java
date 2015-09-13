package acm.edu.usf.cse.usftech;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class JobAdapter extends ArrayAdapter<Job> {
    private final Context mContext;
    private final ArrayList<Job> mJobs;

    private static class ViewHolder {
        private TextView itemView;
    }

    public JobAdapter(Context context, int textViewResourceId, ArrayList<Job> items) {
        super(context, textViewResourceId, items);
        this.mContext = context;
        this.mJobs = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.jobs_list_item, parent, false);

        final Job[] jobArray = mJobs.toArray(new Job[mJobs.size()]);

        TextView textName = (TextView) rowView.findViewById(R.id.txtName);
        TextView textCompany = (TextView) rowView.findViewById(R.id.txtCompany);
        TextView textType = (TextView) rowView.findViewById(R.id.txtType);
        TextView textHours = (TextView) rowView.findViewById(R.id.txtHours);
        TextView textDesc = (TextView) rowView.findViewById(R.id.txtDesc);

        textName.setText(jobArray[position].getPositionName());
        textCompany.setText(jobArray[position].getCompanyName());
        textType.setText(jobArray[position].getType());
        textHours.setText(jobArray[position].getHours());
        textDesc.setText(jobArray[position].getDesc());

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