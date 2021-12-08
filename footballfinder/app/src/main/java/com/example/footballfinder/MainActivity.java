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
        getSupportActionBar().hide();

        logIn();
        createAccount();
    }

    private void logIn() {
        final Button login = findViewById(R.id.login);
        login.setOnClickListener((View v) -> {
            if (Internet.internetConnectionAvailable(this)){
                getUserName();
                getUserPassword();
                // if user exists
                if (true){
                    Intent intent = new Intent(this, MapsActivity.class);
                    intent.putExtra("userID", 0); // pus some random id for now
                    startActivity(intent);
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
            Intent intent = new Intent(this, SignUpActivity.class);
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
}