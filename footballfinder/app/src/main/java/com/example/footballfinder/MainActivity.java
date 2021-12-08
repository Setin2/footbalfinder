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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        loginListener();
        signupListener();
    }

    private void loginListener() {

        final Button login = findViewById(R.id.login);
        login.setOnClickListener((View v) -> {
            if (Internet.internetConnectionAvailable(this)){
                User.userLogin(this, getUsername(), getPassword(), user -> {
                    openMapActivity(user.id);
                }, error -> {
                    if(error.networkResponse.statusCode == 401){
                        Snackbar.make(findViewById(R.id.mainActivity), "Invalid login", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Snackbar.make(findViewById(R.id.mainActivity), "Server error", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }else{
                Snackbar.make(findViewById(R.id.mainActivity), "No internet connection", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void openMapActivity(int userID){
        if(MapsActivity.active == false){
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            intent.putExtra("userID", userID); // pus some random id for now
            startActivity(intent);
        }

    }

    /*
     * Go to SignUp activity
     */
    private void signupListener() {
        final Button signup = findViewById(R.id.signUp);
        signup.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    /*
     * Take the username from the input
     */
    private String getUsername(){
        EditText name = findViewById(R.id.user_name);
        return name.getText().toString().trim();
    }

    /*
     * Take the password from the input
     */
    private String getPassword(){
        // once the confirm id button is clicked, the current text in the edit-text is stored as the userId
        EditText pass = findViewById(R.id.user_password);
        return pass.getText().toString().trim();
    }
}