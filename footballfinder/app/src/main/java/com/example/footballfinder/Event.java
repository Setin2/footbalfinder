package com.example.footballfinder;

import android.content.Context;

import androidx.core.util.Consumer;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Class representing users
 */
public class Event {
    int id;
    int fieldID;
    int maxParticipant;
    int ownerID;
    String description;
    String start_time;
    String end_time;
    String owner_username;

    /*
     * Constructor
     */
    public Event(int id, int fieldID, int maxParticipant, int ownerID, String description, String start_time, String end_time, String owner_username){
        this.id = id;
        this.fieldID = fieldID;
        this.maxParticipant = maxParticipant;
        this.ownerID = ownerID;
        this.description = description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.owner_username = owner_username;
    }

    public static void getEventsOnFieldToday(Context con, int fieldID, Consumer<ArrayList<Event>> consumer, Consumer<VolleyError> errorHandler){
        httpHelper http = new httpHelper(con);
        ArrayList<Event> events = new ArrayList<>();
        JSONObject body = new JSONObject();
        try{
            body.put("case", "geteventsonfieldtoday");
            body.put("field_id", fieldID);
        }catch(Exception e){

        }

        http.postRequest(body, data -> {
            try{
                JSONArray events_data = (JSONArray) data.get("data");
                for(int i = 0; i < events_data.length(); i++){
                    JSONObject e = (JSONObject) events_data.get(i);
                    Event event = eventFromJSON(e);
                    events.add(event);
                }
                consumer.accept(events);
            }catch (Exception e){
                Logger.log("WTFFERROR?");
            }

        }, errorHandler::accept);

    }

    public static void addEvent(Context con, Event event, Consumer<Event> consumer, Consumer<VolleyError> errorHandler){
        httpHelper http = new httpHelper(con);
        JSONObject body = new JSONObject();
        try{
            body.put("case", "newevent");
            body.put("field_id", event.fieldID);
            body.put("max_particpants", event.maxParticipant);
            body.put("owner", event.ownerID);
            body.put("desc", event.description);
            body.put("start", event.start_time);
            body.put("end", event.end_time);

        }catch(Exception e){

        }

        http.postRequest(body, data -> {
            try{
                JSONObject event_data = (JSONObject) data.get("data");
                event_data = (JSONObject) event_data.get("event");
                Event e = eventFromJSON(event_data);

                consumer.accept(e);
            }catch (Exception e){
            }

        }, errorHandler::accept);

    }

    public static Event eventFromJSON(JSONObject event_json) throws Exception{
        Event event = new Event(
                event_json.getInt("id"),
                event_json.getInt("field"),
                event_json.getInt("max_participants"),
                event_json.getInt("owner"),
                event_json.getString("description"),
                event_json.getString("start_time"),
                event_json.getString("end_time"),
                event_json.getString("owner_username"));

        return event;
    }


}