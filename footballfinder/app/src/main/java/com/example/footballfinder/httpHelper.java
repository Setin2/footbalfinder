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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;



public class httpHelper {

    private String url = "https://test-backend-psi.vercel.app/footballfinder";
    private RequestQueue queue;

    public httpHelper(Context con){
        queue = Volley.newRequestQueue(con);
    }

    public void postRequest(Consumer<JSONObject> responseConsumer, JSONObject body){
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, body,
                response -> {
                    responseConsumer.accept(response);
                }
                , error -> {
            Log.d("http error","error: " + error.toString());

        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
