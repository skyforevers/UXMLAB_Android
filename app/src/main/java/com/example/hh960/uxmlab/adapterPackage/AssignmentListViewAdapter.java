package com.example.hh960.uxmlab.adapterPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hh960.uxmlab.R;
import com.example.hh960.uxmlab.object.AssignmentListViewItem;

import java.util.ArrayList;

/**
 * Created by seungyeonlee on 2018. 2. 7..
 */

public class AssignmentListViewAdapter extends BaseAdapter {
    private ArrayList<AssignmentListViewItem> asListViewItemList = new ArrayList<AssignmentListViewItem>();
    private String asContent = new String();

    public AssignmentListViewAdapter() {

    }

    @Override
    public int getCount() {
        return asListViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return asListViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2);


        AssignmentListViewItem listViewItem = asListViewItemList.get(position);

        titleTextView.setText(listViewItem.getAsList_title());
        descTextView.setText(listViewItem.getAsList_content());


        return convertView;
    }

    public void addAsList(String title, String listContent) {
        AssignmentListViewItem item = new AssignmentListViewItem();

        item.setAsList_title(title);
        item.setAsList_content(listContent);
        asListViewItemList.add(item);
    }


}
