package acm.edu.usf.cse.usftech;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Event {
    private String mName;
    private Date mStart;
    private Date mEnd;
    private String mDescription;
    private String mLocation;
    private URI mURI;
    private ArrayList<String> mOrgs;

    public Event(Node n)
    {
        NodeList attributes = n.getChildNodes();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy hh:mm a");

        try {
            mName = attributes.item(0).getTextContent();
            mStart = formatter.parse(attributes.item(1).getTextContent());
            mEnd = formatter.parse(attributes.item(2).getTextContent());
            mLocation = attributes.item(3).getTextContent();
            mDescription = attributes.item(4).getTextContent();
            mURI = new URI(attributes.item(5).getTextContent());
        }
        catch (Exception ex) { }

        NodeList orgs = attributes.item(6).getChildNodes();
        mOrgs = new ArrayList<String>();
        for(int i = 0; i < orgs.getLength(); i++) {
            mOrgs.add(orgs.item(i).getTextContent());
        }
    }

    public String getName() {
        return mName;
    }

    public String getStartTime() {
        DateFormat formatter = new SimpleDateFormat("EEE, MMM dd h:mm a");
        return formatter.format(mStart);
    }

    public String getEndTime() {
        DateFormat equalityFormatter = new SimpleDateFormat("MMM dd");
        DateFormat formatter = new SimpleDateFormat("h:mm a");

        if(!equalityFormatter.format(mStart).equals(equalityFormatter.format(mEnd))) {  //Event starts and ends on different days
            formatter = new SimpleDateFormat("EEE, MMM dd h:mm a");
        }

        return formatter.format(mEnd);
    }

    public String getLocation() {
        return mLocation;
    }

    public URI getURI() {
        return mURI;
    }

    public String getURIString() {
        return mURI.toString();
    }

    public String getOrgs() {
        String[] orgs = new String[mOrgs.size()];
        orgs = mOrgs.toArray(orgs);

        String retVal = orgs[0];

        for(int i = 1; i < mOrgs.size(); i++) {
            retVal += ", "+ orgs[i];
        }

        return retVal;
    }

    public String getDesc() {
        return mDescription;
    }

}
