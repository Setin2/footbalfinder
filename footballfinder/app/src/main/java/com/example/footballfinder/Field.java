package com.example.footballfinder;

import android.content.Context;
import android.util.Log;

import androidx.core.util.Consumer;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * Class representing fields
 */
public class Field {
    int id;
    String description;
    String location;
    double lat;
    double lon;
    String type;
    String owner;

    /*
     * Constructor
     */
    public Field(int id, String description, String location, double lat, double lon, String type, String owner){
        this.id = id;
        this.description = description;
        this.location = location;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.owner = owner;
    }

    public static void getAllFields(Context con, Consumer<ArrayList<Field>> consumer, Consumer<VolleyError> errorHandler){
        httpHelper http = new httpHelper(con);
        ArrayList<Field> fields = new ArrayList<>();
        JSONObject body = new JSONObject();
        try{
            body.put("case", "getfields");
        }catch(Exception e){

        }

        http.postRequest(body, data -> {
            try{
                JSONArray fields_data = (JSONArray) data.get("data");
                for(int i = 0; i < fields_data.length(); i++){
                    JSONObject f = (JSONObject) fields_data.get(i);
                    Field field = new Field(
                            f.getInt("id"),
                            f.getString("description"),
                            f.getString("location"),
                            f.getDouble("lat"),
                            f.getDouble("lon"),
                            f.getString("type"),
                            f.getString("owner")
                    );
                    fields.add(field);
                }
                consumer.accept(fields);
            }catch (Exception e){
            }

        }, errorHandler::accept);

    }

}