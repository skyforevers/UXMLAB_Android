package com.example.hh960.uxmlab.adapterPackage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hh960.uxmlab.R;
import com.example.hh960.uxmlab.object.Listing;

import java.util.ArrayList;

/**
 * Created by HoeYongJin on 2018-02-11.
 */


public class myAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<Listing> listing;
    private LayoutInflater inflater;

    //class Constructor
    public myAdapter(Context mContext, ArrayList<Listing> listing) {

        this.mContext = mContext;
        this.listing = listing;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return listing.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listing.get(groupPosition).name.size();
    }

    //get listing
    @Override
    public Object getGroup(int groupPosition) {
        return listing.get(groupPosition);
    }

    //this is where we get the information of player
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listing.get(groupPosition).name.get(childPosition);
    }

    //listing ID
    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    //where to get player's id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //get parent row
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.parent_list, null);
        }

        //get listing
        Listing listing = (Listing) getGroup(groupPosition);

        //set positionName
        String positionName = listing.week;

        TextView textView = (TextView) convertView.findViewById(R.id.position_tv);
        textView.setText(positionName);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.indicator);
        if (isExpanded) {
            imageView.setImageResource(android.R.drawable.arrow_up_float);
        } else {
            imageView.setImageResource(android.R.drawable.arrow_down_float);
        }

        convertView.setBackgroundColor(Color.LTGRAY);
        return convertView;
    }

    //get child_list.xml (View)
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        //inflate the layout
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_list, null);
        }

        String child = (String) getChild(groupPosition, childPosition);

        //set the child name
        TextView name = (TextView) convertView.findViewById(R.id.name_tv);
        //get the imageView
        ImageView img = (ImageView) convertView.findViewById(R.id.playerpic);

        name.setText(child);

        //get listing name
        String positionName = (String) getGroup(groupPosition).toString();
        if (positionName == "pitcher") {
            if (child == "고원준") {
                img.setImageResource(R.mipmap.ic_launcher);
            } else if (child == "Brooks Raley") {
                img.setImageResource(R.mipmap.ic_launcher);
            } else if (child == "박세웅") {
                img.setImageResource(R.mipmap.ic_launcher);
            }
        } else if (positionName == "infield") {
            if (child == "문규현") {
                img.setImageResource(R.mipmap.ic_launcher);
            } else if (child == "박종윤") {
                img.setImageResource(R.mipmap.ic_launcher);
            }
        } else if (positionName == "catcher") {
            if (child == "강민호") {
                img.setImageResource(R.mipmap.ic_launcher);
            } else if (child == "안중열") {
                img.setImageResource(R.mipmap.ic_launcher);
            }
        } else if (positionName == "outfield") {
            if (child == "Jim Adduci") {
                img.setImageResource(R.mipmap.ic_launcher);
            } else if (child == "손아섭") {
                img.setImageResource(R.mipmap.ic_launcher);
            }
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
