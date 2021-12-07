package com.example.footballfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONObject;
import androidx.core.util.Consumer;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //private TextView view;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logIn();
        createAccount();
    }
/*
    private void test(){
        httpHelper h = new httpHelper(this);

        Consumer<JSONObject> responseHandler = response ->
        {
            view.setText(response.toString().substring(0,10));
            Log.d("hello", "Consumer says hi!");
        };

        h.getRequest(responseHandler);
    }
*/

    /*
     * Log in button onclicklistener
     */
    private void logIn() {
        final Button login = findViewById(R.id.login);
        login.setOnClickListener((View v) -> {
            if (internetConnectionAvailable()){
                getUserName();
                getUserPassword();
                // if user exists
                if (true){
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("userID", 0); // pus some random id for now
                    startActivity(intent);
                    finish();
                }
                else {
                    Snackbar.make(findViewById(R.id.mainActivity), "User does not exist", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
     * Go to SignUp activity
     */
    private void createAccount() {
        final Button login = findViewById(R.id.signUp);
        login.setOnClickListener((View v) -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        });
    }

    /*
     * Take the username from the input
     */
    private void getUserName(){
        EditText name = findViewById(R.id.user_name);
        username = name.getText().toString().trim();
    }

    /*
     * Take the password from the input
     */
    private void getUserPassword(){
        // once the confirm id button is clicked, the current text in the edit-text is stored as the userId
        EditText pass = findViewById(R.id.user_password);
        password = pass.getText().toString().trim();
    }

    /*
     * Return true if the device is connected to the internet
     */
    private boolean internetConnectionAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // we return true if connection is available
        assert connectivityManager != null;
        if (Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).getState() == NetworkInfo.State.CONNECTED ||
                Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        // else we alert the user that connection to internet is missing
        else {
            android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(MainActivity.this);
            dialog.setTitle( "ERROR" )
                    .setMessage("Task failed due to missing connection to internet")
                    .setPositiveButton("OK", (dialoginterface, i) -> {
                        dialoginterface.cancel();
                    }).show();
            return false;
        }
    }
}