package acm.edu.usf.cse.usftech;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    static private final String contactEmail = "hpate@mail.usf.edu";

    static private final String versionlink = "http://www.cse.usf.edu/acm/USFTechApp/newestversion.xml";
    static private final String orgslink = "http://www.cse.usf.edu/acm/USFTechApp/organizations.xml";
    static private final String[] eventlinks = {"http://www.cse.usf.edu/acm/USFTechApp/events.xml"};
    static private final String[] joblinks = {"http://www.cse.usf.edu/acm/USFTechApp/jobs.xml"};

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


        /* Get Newest Version Number, prompt if update needed */
        new needUpdate().execute(versionlink);
        /* --- */
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position)
        {
            case 0: //Events Calendar
                fragmentManager.beginTransaction()
                        .replace(R.id.container, EventsFragment.newInstance(position + 1))
                        .commit();
                break;
            case 1: //Job Postings
                fragmentManager.beginTransaction()
                        .replace(R.id.container, JobsFragment.newInstance(position + 1))
                        .commit();
                break;
            case 2: //Organizations
                fragmentManager.beginTransaction()
                        .replace(R.id.container, OrgsFragment.newInstance(position + 1))
                        .commit();
                break;
        }
    }

    public void onSectionAttached(int number) {
        String[] menuitems = getResources().getStringArray(R.array.menu);
        /*  1 - Events Calendar
            2 - Job Postings
            3 - Organizations */

        mTitle = menuitems[number-1];
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.

            if(mTitle.toString().equals("Events Calendar")) { getMenuInflater().inflate(R.menu.events_calendar, menu); }
            else if(mTitle.toString().equals("Job Postings")) { getMenuInflater().inflate(R.menu.job_postings, menu); }
            else if(mTitle.toString().equals("Organizations")) { getMenuInflater().inflate(R.menu.organizations, menu); }

            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/
        if (id == R.id.action_report) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{contactEmail});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "USFTech Bug");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Version Num: " + BuildConfig.VERSION_CODE + "\nProblem\\\\Comment: ");
            emailIntent.putExtra(Intent.EXTRA_TITLE, "2Send Bug Report or Comment2");

            try {
                startActivity(Intent.createChooser(emailIntent, "Send Bug Report or Comment..."));
                finish();
                //Log.i("Finished sending email", "");
            }
            catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_LONG).show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDialog(Context context, int layoutResouceID, String title) {
        /* Create the dialog */
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(layoutResouceID);
        dialog.setTitle(title);
        /* --- */

        /* Set the Button Click Listeners */
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Launch Intent to download new update */
                Uri updateLink = Uri.parse("http://www.cse.usf.edu/acm/UpdateApp.html");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, updateLink);
                startActivity(launchBrowser);
                /* --- */
            }
        });

        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        /* --- */

        dialog.show();
    }

    /* Recursive function to remove all text nodes from a given node tree */
    public static Node removeTextNodes(Node root) {
        NodeList children = root.getChildNodes();

                /*  If there is only one child and it is a text node
                 *  Then this root holds data and must be skipped
                 */
        if(children.getLength() == 1 && children.item(0).getNodeType() == Node.TEXT_NODE) { return root; }
                /* --- */

                /*  Loop through root's children
                *   Remove any text nodes
                *   Recursively call removeTextNodes on any non-text nodes
                */
        for(int i = 0; i < children.getLength(); i++) {
            if(children.item(i).getNodeType() == Node.TEXT_NODE) {
                root.removeChild(children.item(i));
            }
            else {
                root.replaceChild(removeTextNodes(children.item(i)), children.item(i));
            }
        }
                /* --- */

                /*  Sort by Date */
        //sortList()
                /* --- */

        return root;
    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragments_placeholder, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    /**
     * Events Calendar
     */
    public static class EventsFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static ArrayList<Event> events = new ArrayList<Event>();
        private static boolean gotEvents = false;


        public static EventsFragment newInstance(int sectionNumber) {
            EventsFragment fragment = new EventsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            return fragment;
        }

        public EventsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_events_calendar, container, false);

            if(!gotEvents) {    //Events not retrieved, this if statement ensures against duplication
                /* For each url in eventlinks, get list of events and add them to the listview */
                for(int i = 0; i < eventlinks.length; i++) { new getEvents(getContext(), rootView).execute(eventlinks[i]); }
                /* --- */
            }
            else {
                attachEvents(getContext(), rootView, events);
            }
            gotEvents = true;

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        protected void attachEvents(Context context, View rootView, ArrayList<Event> listEvents) {
            /* Remove events that occured before the current datetime */
            ArrayList<Event> list = removeOldEvents(listEvents);
            /* --- */

            /* Attach the ArrayList of events to the ListView using a custom adapter */
            ListView eventList = (ListView) rootView.findViewById(R.id.listEvents);
            EventAdapter adapter = new EventAdapter(context, R.layout.events_list_item, list);
            eventList.setAdapter(adapter);
             /* --- */
        }

        private ArrayList<Event> sortList(ArrayList<Event> unsorted) {
            Event[] list = new Event[unsorted.size()];
            list = unsorted.toArray(list);

            for (int i = 1; i < list.length; i++) {
                Event next = list[i];

                    /* Find the insertion location while moving all larger element up */
                int j = i;
                while (j > 0 && list[j - 1].after(next)) {
                    list[j] = list[j - 1];
                    j--;
                }
                    /* --- */

                list[j] = next; // insert the element
            }

            return new ArrayList<Event>(Arrays.asList(list));
        }

        private ArrayList<Event> removeOldEvents(ArrayList<Event> eventList) {
            Event[] list = new Event[eventList.size()];
            list = eventList.toArray(list);

            ArrayList<Event> retEvents = new ArrayList<Event>();
            Date sysdate = new Date();

            for (int i = 0; i < eventList.size(); i++) {
                if(list[i].after(sysdate)) {
                    retEvents.add(list[i]);
                }
            }

            return retEvents;
        }


        private class getEvents extends AsyncTask<String, Void, Document> {
            private Context mContext;
            private View rootView;

            public getEvents(Context context, View rootView){
                this.mContext=context;
                this.rootView=rootView;
            }

            @Override
            protected Document doInBackground(String... urls) {

                String url = urls[0].toString();

                try {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    factory.setNamespaceAware(true);
                    DocumentBuilder dBuilder = factory.newDocumentBuilder();
                    Document doc = dBuilder.parse(new URL(url).openStream());
                    doc.getDocumentElement().normalize();
                    return doc;
                }
                catch (Exception ex) {
                    String s = ex.toString();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Document result) {
                super.onPostExecute(result);

                Node rootNode = result.getFirstChild(); //Get root node from Document

                if(!rootNode.hasChildNodes()){return;}  //If there are no events, end

                rootNode = MainActivity.removeTextNodes(rootNode); //Remove all text nodes from node tree

                /* Create an Event object for each child */
                NodeList nodes = rootNode.getChildNodes();
                Node eventNode = null;
                Event event = null;
                for(int i = 0; i < nodes.getLength(); i++) {
                    eventNode = nodes.item(i);
                    event = new Event(eventNode);
                    events.add(event);
                }
                /* --- */

                /* Sort events, then attach to ListView */
                attachEvents(mContext, rootView, sortList(events));
                /* --- */
            }
        }
    }

    /**
     * Job Postings
     */
    public static class JobsFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static ArrayList<Job> jobs = new ArrayList<Job>();
        private static boolean gotJobs = false;

        public static JobsFragment newInstance(int sectionNumber) {
            JobsFragment fragment = new JobsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public JobsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragments_job_postings, container, false);


            if(!gotJobs) {    //Events not retrieved, this if statement ensures against duplication
                /* For each url in joblinks, get list of events and add them to the listview */
                for(int i = 0; i < joblinks.length; i++) { new getJobs(getContext(), rootView).execute(joblinks[i]); }
                /* --- */
            }
            else {
                attachJobs(getContext(), rootView, jobs);
            }
            gotJobs = true;

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        protected void attachJobs(Context context, View rootView, ArrayList<Job> listJobs) {
            /* Attach the ArrayList of events to the ListView using a custom adapter */
            ListView jobList = (ListView) rootView.findViewById(R.id.listJobs);
            JobAdapter adapter = new JobAdapter(context,R.layout.jobs_list_item, listJobs);
            jobList.setAdapter(adapter);
             /* --- */
        }

        private class getJobs extends AsyncTask<String, Void, Document> {
            private Context mContext;
            private View rootView;

            public getJobs(Context context, View rootView){
                this.mContext=context;
                this.rootView=rootView;
            }

            @Override
            protected Document doInBackground(String... urls) {

                String url = urls[0].toString();

                try {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    factory.setNamespaceAware(true);
                    DocumentBuilder dBuilder = factory.newDocumentBuilder();
                    Document doc = dBuilder.parse(new URL(url).openStream());
                    doc.getDocumentElement().normalize();
                    return doc;
                }
                catch (Exception ex) {
                    String s = ex.toString();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Document result) {
                super.onPostExecute(result);

                Node rootNode = result.getFirstChild(); //Get root node from Document

                if(!rootNode.hasChildNodes()){return;}  //If there are no events, end

                rootNode = MainActivity.removeTextNodes(rootNode); //Remove all text nodes from node tree

                /* Create a Job object for each child */
                NodeList nodes = rootNode.getChildNodes();
                Node jobNode = null;
                Job job = null;
                for(int i = 0; i < nodes.getLength(); i++) {
                    jobNode = nodes.item(i);
                    job = new Job(jobNode);
                    jobs.add(job);
                }
                /* --- */

                attachJobs(mContext, rootView, jobs);
            }

        }
    }

    /**
     * Organizations
     */
    public static class OrgsFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static ArrayList<Organization> orgs = new ArrayList<Organization>();
        private static boolean gotOrgs = false;


        public static OrgsFragment newInstance(int sectionNumber) {
            OrgsFragment fragment = new OrgsFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public OrgsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragments_organizations, container, false);

            if(!gotOrgs) {    //Orgs not retrieved, this if statement ensures against duplication
                new getOrgs(getContext(), rootView).execute(orgslink);
            }
            else {
                attachOrgs(getContext(), rootView, orgs);
            }
            gotOrgs = true;

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        protected void attachOrgs(Context context, View rootView, ArrayList<Organization> listOrgs) {
            /* Attach the ArrayList of events to the ListView using a custom adapter */
            ListView orgList = (ListView) rootView.findViewById(R.id.listOrgs);
            OrganizationAdapter adapter = new OrganizationAdapter(context, R.layout.orgs_list_item, listOrgs);
            orgList.setAdapter(adapter);
             /* --- */
        }

        private class getOrgs extends AsyncTask<String, Void, Document> {
            private Context mContext;
            private View rootView;

            public getOrgs(Context context, View rootView){
                this.mContext=context;
                this.rootView=rootView;
            }

            @Override
            protected Document doInBackground(String... urls) {

                String url = urls[0].toString();

                try {
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    factory.setNamespaceAware(true);
                    DocumentBuilder dBuilder = factory.newDocumentBuilder();
                    Document doc = dBuilder.parse(new URL(url).openStream());
                    doc.getDocumentElement().normalize();
                    return doc;
                }
                catch (Exception ex) {
                    String s = ex.toString();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Document result) {
                super.onPostExecute(result);

                Node rootNode = result.getFirstChild(); //Get root node from Document

                if(!rootNode.hasChildNodes()){return;}  //If there are no events, end

                rootNode = MainActivity.removeTextNodes(rootNode); //Remove all text nodes from node tree

                /* Create an Organization object for each child */
                NodeList nodes = rootNode.getChildNodes();
                Node orgNode = null;
                Organization org = null;
                for(int i = 0; i < nodes.getLength(); i++) {
                    orgNode = nodes.item(i);
                    org = new Organization(orgNode);
                    orgs.add(org);
                }
                /* --- */

                attachOrgs(mContext, rootView, orgs);
            }

        }
    }



    private class needUpdate extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... urls) {

            String url = urls[0].toString();

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                DocumentBuilder dBuilder = factory.newDocumentBuilder();
                return dBuilder.parse(new URL(url).openStream());
            }
            catch (Exception ex) {
                String s = ex.toString();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Document result) {
            super.onPostExecute(result);

            NodeList nodes = result.getElementsByTagName("newestversion");
            int newsestCode = Integer.parseInt(nodes.item(0).getTextContent());

            //String versionName = BuildConfig.VERSION_NAME;
            int versionCode = BuildConfig.VERSION_CODE;

            if(newsestCode > versionCode) { //Update Needed
                showDialog(MainActivity.this, R.layout.dialog_update, "Update Avaliable");
                //Toast.makeText(getApplicationContext(), "Update Needed", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Update Not Needed", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
