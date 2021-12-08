package com.example.footballfinder;

import android.util.Log;

public class Logger {
    public static void log(String tag, Object msg){
        Log.d(tag, String.valueOf(msg));
    }
}
