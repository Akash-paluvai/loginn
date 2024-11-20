
// HelpActivity.java
package com.example.loginn;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        expandableListView = findViewById(R.id.expandableListView);
        prepareListData();

        HelpExpandableListAdapter adapter = new HelpExpandableListAdapter(this,
                listDataHeader,
                listDataChild);
        expandableListView.setAdapter(adapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listDataHeader.add("Teacher Login Help");
        listDataHeader.add("Admin Login Help");

        List<String> teacherHelp = new ArrayList<>();
        teacherHelp.add("1. Use your official @vnrvjiet.in email");
        teacherHelp.add("2. Click on Google Sign-in button");
        teacherHelp.add("3. Select your institutional account");
        teacherHelp.add("4. Follow Google sign-in prompts");

        List<String> adminHelp = new ArrayList<>();
        adminHelp.add("1. Enter your registered phone number");
        adminHelp.add("2. Enter the OTP received");
        adminHelp.add("3. Complete Google sign-in");
        adminHelp.add("4. Contact support if issues persist");

        listDataChild.put(listDataHeader.get(0), teacherHelp);
        listDataChild.put(listDataHeader.get(1), adminHelp);
    }
}