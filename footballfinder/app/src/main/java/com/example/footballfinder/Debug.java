package com.example.footballfinder;

import android.content.Context;
import android.content.Intent;

public class Debug {

    public static void testActivity(Context con, Class c){
        Intent intent = new Intent(con, c);
        User.userLogin(con, "matti", "matti", data -> {

        }, error -> {

        });
        intent.putExtra("fieldID", 11);
        intent.putExtra("eventID", 120);
        con.startActivity(intent);

    }
}
