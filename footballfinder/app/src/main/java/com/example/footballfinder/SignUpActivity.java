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
                if (internetConnectionAvailable()){
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
            AlertDialog.Builder dialog = new AlertDialog.Builder(SignUpActivity.this);
            dialog.setTitle( "ERROR" )
                    .setMessage("Task failed due to missing connection to internet")
                    .setPositiveButton("OK", (dialoginterface, i) -> {
                        dialoginterface.cancel();
                    }).show();
            return false;
        }
    }
}