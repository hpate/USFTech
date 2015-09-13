package acm.edu.usf.cse.usftech;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OrganizationAdapter extends ArrayAdapter<Organization> {
    private final Context mContext;
    private final ArrayList<Organization> mOrgs;

    private static class ViewHolder {
        private TextView itemView;
    }

    public OrganizationAdapter(Context context, int textViewResourceId, ArrayList<Organization> items) {
        super(context, textViewResourceId, items);
        this.mContext = context;
        this.mOrgs = items;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.orgs_list_item, parent, false);

        final Organization[] orgArray = mOrgs.toArray(new Organization[mOrgs.size()]);

        TextView textShortNm = (TextView) rowView.findViewById(R.id.txtOrgShort);
        TextView textLongNm = (TextView) rowView.findViewById(R.id.txtOrgLong);
        TextView textMeetings = (TextView) rowView.findViewById(R.id.txtMeetings);
        TextView textDesc = (TextView) rowView.findViewById(R.id.txtDesc);

        textShortNm.setText(orgArray[position].getAcronym());
        textLongNm.setText(orgArray[position].getFullName());
        textMeetings.setText(orgArray[position].getMeetings());
        textDesc.setText(orgArray[position].getDesc());

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