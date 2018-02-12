package com.example.hh960.uxmlab.activityPackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.hh960.uxmlab.R;
import com.example.hh960.uxmlab.adapterPackage.myAdapter;
import com.example.hh960.uxmlab.object.Listing;

import java.util.ArrayList;

/**
 * Created by HoeYongJin on 2018-02-11.
 */


public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, BoardWriteActivity.class);
                startActivity(intent);
            }
        });
        ExpandableListView elv = (ExpandableListView) findViewById(R.id.elv);

        final ArrayList<Listing> listing = getData();

        //create and bind to adatper
        myAdapter adapter = new myAdapter(this, listing);
        elv.setAdapter(adapter);

        //set onclick listener
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(), listing.get(groupPosition).name.get(childPosition), Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }
    //add and get data for list
    private ArrayList<Listing> getData() {

        Listing p1 = new Listing("1주차");
        p1.name.add("A");
        p1.name.add("B");
        p1.name.add("박세웅");

        Listing p2 = new Listing("2주차");
        p2.name.add("강민호");
        p2.name.add("안중열");

        Listing p3 = new Listing("3주차");
        p3.name.add("문규현");
        p3.name.add("박종윤");

        Listing p4 = new Listing("4주차");
        p4.name.add("Jim Adduci");
        p4.name.add("손아섭");

        Listing p5 = new Listing("5주차");
        p5.name.add("주씨...");


        ArrayList<Listing> allposition = new ArrayList<>();
        allposition.add(p1);
        allposition.add(p2);
        allposition.add(p3);
        allposition.add(p4);
        allposition.add(p5);
        return allposition;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
