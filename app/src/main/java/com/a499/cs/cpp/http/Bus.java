package com.a499.cs.cpp.http;

/**
 * Created by Joshua R. Viray.
 */
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;


//@JsonIgnoreProperties(ignoreUnknown = true)
public class Bus {

//    @JsonProperty("ID")
    private String ID;
//    @JsonProperty("RouteId")
    private String RouteId;
//    @JsonProperty("Latitude")
    private double Latitude;
//    @JsonProperty("Longitude")
    private double Longitude;
//    @JsonProperty("Speed")
    private int Speed;
//    @JsonProperty("Heading")
    private String Heading;

    public String getId() {
        return ID;
    }

    public void setId(String id) {
        this.ID = ID;
    }

    public String getRouteId() {
        return RouteId;
    }

    public void setRouteId(String routeId) {
        this.RouteId = routeId;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        this.Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        this.Longitude = longitude;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        this.Speed = speed;
    }

    public String getHeading() {
        return Heading;
    }

    public void setHeading(String heading) {
        this.Heading = heading;
    }

    public String toString() {
        return "Id: " + ID + ", " + "Route: " + RouteId + ", " + "Lat: " + Latitude + ", Lon: " + Longitude;
    }
}

