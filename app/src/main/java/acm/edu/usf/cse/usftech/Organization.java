package acm.edu.usf.cse.usftech;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Organization {
    private String mAcronym;
    private String mFullName;
    private String mMeetings;
    private String mDescription;
    private String mEmail;
    private URI mWebsite;

    public Organization(Node n)
    {
        NodeList attributes = n.getChildNodes();

        try {
            mAcronym = n.getAttributes().getNamedItem("acronym").getNodeValue();
            mFullName = attributes.item(0).getTextContent();
            mMeetings = attributes.item(1).getTextContent();
            mDescription = attributes.item(2).getTextContent();
            mWebsite = new URI(attributes.item(3).getTextContent());
            mEmail = attributes.item(4).getTextContent();
        }
        catch (Exception ex) { }
    }

    public String getAcronym() {
        return mAcronym;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getDesc() {
        return mDescription;
    }

    public String getMeetings() {
        return mMeetings;
    }

    public String getEmail() {
        return mEmail;
    }

    public URI getWebsite() {
        return mWebsite;
    }

}
