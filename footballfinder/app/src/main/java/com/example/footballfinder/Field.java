package com.example.footballfinder;

/*
 * Class representing fields
 */
public class Field {
    private int id;
    private String description;
    private String location;
    private float lat;
    private float lon;
    private String type;
    private String owner;

    /*
     * Constructor
     */
    public Field(int id, String description, String location, float lat, float lon, String type, String owner){
        this.id = id;
        this.description = description;
        this.location = location;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}