package acm.edu.usf.cse.usftech;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Job {
    private String mPositionName;
    private String mCompanyName;
    private URI mCompanySite;
    private String mType;
    private String mHours;
    private String mHourly;
    private String mSalary;
    private String mDescription;
    private String mEmail;
    private String mPhone;
    private URI mApplyLink;
    private ArrayList<String> mQualifications;

    public Job(Node n)
    {
        NodeList attributes = n.getChildNodes();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm a");

        try {
            mPositionName = attributes.item(0).getTextContent();

            mCompanyName = attributes.item(1).getFirstChild().getTextContent();
            mCompanySite = new URI(attributes.item(1).getLastChild().getTextContent());

            mType = attributes.item(2).getTextContent();
            mDescription = attributes.item(3).getTextContent();
            mHours = attributes.item(5).getTextContent();
            mHourly = attributes.item(6).getTextContent();
            mSalary = attributes.item(7).getTextContent();

            NodeList quals = attributes.item(6).getChildNodes();
            mQualifications = new ArrayList<String>();
            for(int i = 0; i < quals.getLength(); i++) {
                mQualifications.add(quals.item(i).getTextContent());
            }

            mEmail = attributes.item(8).getFirstChild().getTextContent();
            mPhone = attributes.item(8).getChildNodes().item(1).getTextContent();
            mApplyLink = new URI(attributes.item(8).getLastChild().getTextContent());
        }
        catch (Exception ex) { }
    }

    public String getPositionName() {
        return mPositionName;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public URI getCompanySite() {
        return mCompanySite;
    }

    public String getQualifications() {
        String[] quals = new String[mQualifications.size()];
        quals = mQualifications.toArray(quals);

        String retVal = quals[0];

        for(int i = 1; i < mQualifications.size(); i++) {
            retVal += ", "+ quals[i];
        }

        return retVal;
    }

    public String getDesc() {
        return mDescription;
    }

    public String getType() {
        return mType;
    }

    public String getHours() {
        return mHours;
    }

    public String getHourly() {
        return mHourly;
    }

    public String getSalary() {
        return mSalary;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPhone() {
        return mPhone;
    }

    public URI getApplyLink() {
        return mApplyLink;
    }

}
