package com.example.hh960.uxmlab.object;

import java.util.ArrayList;

/**
 * Created by HoeYongJin on 2018-02-11.
 */

public class Listing {

    //Properties of Listing
    public String week;
    public String image;
    public ArrayList<String> name = new ArrayList<String>();

    public Listing(String week){
        this.week = week;
    }

    public String toString () {
        return week;
    }

}
