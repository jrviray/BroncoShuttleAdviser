package com.a499.cs.cpp.http;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner routeList = (Spinner) findViewById(R.id.spinner_routeList);
        final Spinner stopList = (Spinner) findViewById(R.id.spinner_stopList);
        final Button refresh = (Button) findViewById(R.id.b_arrivalRefresh);
        final ListView lv_arrivalTimes = (ListView) findViewById(R.id.lv_arrivalTimes);

        populateRoutes(routeList);

        /**
         * Listen for route selection and populate the corresponding routes.
         */
        routeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rte = parent.getSelectedItem().toString();
                populateStops(rte, stopList);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /**
         * Listen for stop selection and generate arrival times.
         */
        stopList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> arrivalTimes = new ArrayList<>();
                try {
                    arrivalTimes = new GetBusInfo(routeList.getSelectedItem().toString(), parent.getSelectedItem().toString(), getBaseContext()).execute().get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                emptyCheck(arrivalTimes);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrivalTimes);
                lv_arrivalTimes.setAdapter(arrayAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /**
         * Listen for refresh press
         */
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arrivalTimes = new ArrayList<>();
                try {
                    arrivalTimes = new GetBusInfo(routeList.getSelectedItem().toString(), stopList.getSelectedItem().toString(), getBaseContext()).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                emptyCheck(arrivalTimes);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrivalTimes);
                lv_arrivalTimes.setAdapter(arrayAdapter);
            }
        });

    }

    public void emptyCheck(ArrayList<String> arrayList){
        if (arrayList.size() == 0){
            arrayList.add("There are currently no arrival times available.");
        }
    }

    /**
     * Populate the routes.
     * @param routeList
     * @return
     */
    public String populateRoutes(Spinner routeList){
        ArrayAdapter<CharSequence> routeAdapter = ArrayAdapter.createFromResource(this,R.array.route_lists, android.R.layout.simple_list_item_1);
        routeList.setAdapter(routeAdapter);
        return routeList.getSelectedItem().toString();
    }

    /**
     * Populate the stops.
     * @param rte
     * @param stopList
     */
    public void populateStops(String rte, Spinner stopList){
        // populate spinners
        ArrayAdapter<CharSequence> stopAdapter = null;
        switch (rte){
            case "Route A":
                stopAdapter = ArrayAdapter.createFromResource(this,R.array.rte_a, android.R.layout.simple_list_item_1);
                break;
            case "Route B":
                stopAdapter = ArrayAdapter.createFromResource(this,R.array.rte_b, android.R.layout.simple_list_item_1);
                break;
            case "Route B2":
                stopAdapter = ArrayAdapter.createFromResource(this,R.array.rte_b2, android.R.layout.simple_list_item_1);
                break;
            case "Route C":
                stopAdapter = ArrayAdapter.createFromResource(this,R.array.rte_c, android.R.layout.simple_list_item_1);
                break;
            case "All Routes":
                stopAdapter = ArrayAdapter.createFromResource(this,R.array.rte_all, android.R.layout.simple_list_item_1);
                break;
        }
        stopList.setAdapter(stopAdapter);
    }
}