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
public class User {
    int id;
    String username;
    String password;

    /*
     * Constructor
     */
    public User(int id, String name, String password){
        this.id = id;
        this.username = name;
        this.password = password;
    }

    public static void userLogin(Context con, String username, String password, Consumer<User> consumer, Consumer<VolleyError> errorHandler){
        httpHelper http = new httpHelper(con);
        JSONObject body = new JSONObject();
        try{
            body.put("case", "userlogin");
            body.put("username", username);
            body.put("password", password);
        }catch(Exception e){
        }

        http.postRequest(body, data -> {
            try{
                JSONObject user_data = (JSONObject) data.get("data");
                user_data = (JSONObject) user_data.get("user");

                User user = getUserFromJSON(user_data);

                consumer.accept(user);
            }catch (Exception e){
                consumer.accept(null);
            }

        }, errorHandler::accept);

    }

    public static void createUser(Context con, String username, String password, Consumer<User> consumer, Consumer<VolleyError> errorHandler){
        httpHelper http = new httpHelper(con);
        JSONObject body = new JSONObject();
        try{
            body.put("case", "newuser");
            body.put("username", username);
            body.put("password", password);
        }catch(Exception e){
        }

        http.postRequest(body, data -> {
            try{
                JSONObject user_data = (JSONObject) data.get("data");
                user_data = (JSONObject) user_data.get("user");

                User user = getUserFromJSON(user_data);

                consumer.accept(user);
            }catch (Exception e){
                consumer.accept(null);
            }

        }, errorHandler::accept);

    }

    public static User getUserFromJSON(JSONObject user_json) throws  Exception{
        User user = new User(
                user_json.getInt("id"),
                user_json.getString("username"),
                user_json.getString("password"));
        return user;
    }

}