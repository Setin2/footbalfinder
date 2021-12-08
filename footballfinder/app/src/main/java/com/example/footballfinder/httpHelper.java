package com.example.footballfinder;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.core.util.Consumer;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;



public class httpHelper {

    private static String url = "https://football-finder.vercel.app/footballfinder";
    private static RequestQueue queue;

    public httpHelper(Context con){
        queue = Volley.newRequestQueue(con);
    }

    public void postRequest(JSONObject body, Consumer<JSONObject> responseHandler, Consumer<VolleyError> errorHandler){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, body,
                responseHandler::accept
                ,errorHandler::accept);
        // Add the request to the RequestQueue.
        queue.add(request);
    }
}
