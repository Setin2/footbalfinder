package com.example.footballfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.snackbar.Snackbar;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp();
    }

    private void signUp() {
        final Button login = (Button) findViewById(R.id.signUp);
        login.setOnClickListener((View v) -> {
            getUserName();
            getUserPassword();
            if (!username.isEmpty() && !password.isEmpty()){
                if (Internet.internetConnectionAvailable(this)){
                    User user = new User(0, username, password);
                    Snackbar.make(findViewById(R.id.signUpActivity), "User created successfully", Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(findViewById(R.id.signUpActivity), "Please fill in the text", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /*
     * Get the username from the input
     */
    private void getUserName(){
        EditText name = findViewById(R.id.new_user_name);
        username = name.getText().toString().trim();
    }

    /*
     * Get the password from the input
     */
    private void getUserPassword(){
        // once the confirm id button is clicked, the current text in the edit-text is stored as the userId
        EditText pass = findViewById(R.id.new_user_password);
        password = pass.getText().toString().trim();
    }

    /*
     * If back button is pressed, terminate activity
     */
    @Override
    public void onBackPressed() {
        finish();
    }

}