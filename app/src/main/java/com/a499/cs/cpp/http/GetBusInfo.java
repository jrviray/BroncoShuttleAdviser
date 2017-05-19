package com.a499.cs.cpp.http;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by king on 5/3/17.
 */

public class GetBusInfo extends AsyncTask<Void, Void, ArrayList>{
    String rte;
    String location;
    Context temp;
    ArrayList<String> arrivalTimes = new ArrayList<>();

    GetBusInfo(String rte, String location, Context context) {
        super();
        this.rte = rte;
        this.location = location;
        temp = context;
    }

    @Override
    protected ArrayList doInBackground(Void... params) {
        JSONParser jParser = new JSONParser();
        arrivalTimes = jParser.getArrivalTimes(rte,location, temp);
        return arrivalTimes;
    }


}
