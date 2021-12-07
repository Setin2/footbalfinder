package com.example.footballfinder;

/*
 * Class representing users
 */
public class Event {
    private int id;
    private int fieldID;
    private int maxParticipant;
    private int ownerID;
    private String description;
    private long start_time;
    private long end_time;

    /*
     * Constructor
     */
    public Event(int id, int fieldID, int maxParticipant, int ownerID, String description, long start_time, long end_time){
        this.id = id;
        this.fieldID = fieldID;
        this.maxParticipant = maxParticipant;
        this.ownerID = ownerID;
        this.description = description;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getMaxParticipant() {
        return maxParticipant;
    }

    public void setMaxParticipant(int maxParticipant) {
        this.maxParticipant = maxParticipant;
    }

    public int getFieldID() {
        return fieldID;
    }

    public void setFieldID(int fieldID) {
        this.fieldID = fieldID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}