package com.example.footballfinder;

/*
 * Class representing users
 */
public class Event {
    int id;
    int fieldID;
    int maxParticipant;
    int ownerID;
    String description;
    long start_time;
    long end_time;

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

    public long getStart_time() {
        return start_time;
    }

    public String getDescription() {
        return description;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public int getMaxParticipant() {
        return maxParticipant;
    }

    public int getFieldID() {
        return fieldID;
    }

    public int getId() {
        return id;
    }
}