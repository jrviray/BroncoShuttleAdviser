package com.a499.cs.cpp.http;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by king on 5/3/17.
 */

public class JSONParser {
    private InputStream is = null;
    private String json = "";
    //private Context context;

    // constructor
    public JSONParser() {
    }

    /**
     * Helper Method
     * Gets JSON file from assets folder.
     * @param context
     * @return
     */
    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("locations.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Builds an array list of links.
     * @param rte
     * @param location
     * @param context
     * @return
     */
    public ArrayList buildLinks(String rte, String location, Context context){
        ArrayList<String> links = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAsset(context));
            JSONArray locationArray = jsonObject.getJSONArray("Locations");
            for (int i = 0; i < locationArray.length(); i++){
                JSONObject entry = locationArray.getJSONObject(i);
                String name = entry.getString("Name");
                if (name.equals(location)){
                    String a = entry.getString("A");
                    String b = entry.getString("B");
                    String b2 = entry.getString("B2");
                    String c = entry.getString("C");
                    switch (rte){
                        case "Route A":
                            links.add(a);
                            break;
                        case "Route B":
                            links.add(b);
                            break;
                        case "Route B2":
                            links.add(b2);
                            break;
                        case "Route C":
                            links.add(c);
                            break;
                        case "All Routes":
                            if(!a.equals("null")){
                                links.add(a);
                            }
                            if(!b.equals("null")){
                                links.add(b);
                            }
                            if(!b2.equals("null")){
                               links.add(b2);
                            }
                            if(!c.equals("null")){
                                links.add(c);
                            }
                    }
                }
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return links;
    }

    /**
     * Helper Method
     * Saves the JSON data received from the link as a string.
     * @param urlString
     * @return
     */
    public String getJSONFromUrl(String urlString) {
        HttpURLConnection urlConnection;
        StringBuilder sb = new StringBuilder();
        // Making HTTP request
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            json = sb.toString();
            is.close();
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        json = sb.toString();
        return json;
    }

    /**
     * Builds an array list of JSON strings that needs to be parsed to get the arrival information.
     * @param links
     * @return
     */
    public ArrayList buildJSONStrings(ArrayList links) {
        ArrayList<String> JSONStrings = new ArrayList<>();
        for (int i = 0; i < links.size(); i++){
            JSONStrings.add(getJSONFromUrl(links.get(i).toString()));
        }
        return JSONStrings;
    }

    /**
     * Builds an arrival time message.
     * @param result
     * @return
     */
    public String getArrivalMessages(String result) {
        JSONObject jsonObject = null;
        JSONArray jArray = null;
        String routeName, minutes;
        String message = "";
        try {
            jsonObject = new JSONObject(result);
            jArray = jsonObject.getJSONArray("Predictions");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObj = jArray.getJSONObject(i);
                // START DEBUGGING HERE
                routeName = jObj.getString("RouteName");
                minutes = jObj.getString("Minutes");
                //arriveTime = jObj.getString("ArriveTime");
                message = "A " + routeName + " bus will arrive in " + minutes + " minutes.";
                //arrivalTimes.add(message);

            }
        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        }
        return message;
    }

    /**
     * Builds an array list of arrival time messages.
     */
    public ArrayList buildArrivalTimes(ArrayList links) {
        ArrayList<String> arrivalTimes = new ArrayList<>();
        ArrayList<String> JSONStrings = buildJSONStrings(links);
        for (int i = 0; i < JSONStrings.size(); i++){
            arrivalTimes.add(getArrivalMessages(JSONStrings.get(i).toString()));
        }
        return arrivalTimes;
    }

    public ArrayList getArrivalTimes(String rte, String location, Context context) {
        ArrayList<String> links = new ArrayList<>();
        ArrayList<String> arrivalTimes;

        links = buildLinks(rte, location, context);
        arrivalTimes = buildArrivalTimes(links);

        return arrivalTimes;
    }

}
