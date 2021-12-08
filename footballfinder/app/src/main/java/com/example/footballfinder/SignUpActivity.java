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
        final Button signup = (Button) findViewById(R.id.signUp);
        signup.setOnClickListener((View v) -> {
            username = getUserName();
            password = getUserPassword();
            if (!username.isEmpty() && !password.isEmpty()){
                if (Internet.internetConnectionAvailable(this)){
                    User.createUser(this, username, password, user -> {
                        Snackbar snack = Snackbar.make(findViewById(R.id.signUpActivity), "User created successfully", Snackbar.LENGTH_SHORT);

                        snack.addCallback( new Snackbar.Callback() {

                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                finish();
                            }

                        });
                        snack.show();

                    }, error -> {
                        if(error.networkResponse.statusCode == 409){
                            Snackbar.make(findViewById(R.id.signUpActivity), "Username is already taken", Snackbar.LENGTH_SHORT).show();
                        }else{
                            Snackbar.make(findViewById(R.id.signUpActivity), "Server error", Snackbar.LENGTH_SHORT).show();
                        }
                    });

                }
            } else {
                Snackbar.make(findViewById(R.id.signUpActivity), "Please fill in the text", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    /*
     * Get the username from the input
     */
    private String getUserName(){
        EditText name = findViewById(R.id.new_user_name);
        return name.getText().toString().trim();
    }

    /*
     * Get the password from the input
     */
    private String getUserPassword(){
        // once the confirm id button is clicked, the current text in the edit-text is stored as the userId
        EditText pass = findViewById(R.id.new_user_password);
        return pass.getText().toString().trim();
    }

    /*
     * If back button is pressed, terminate activity
     */
    @Override
    public void onBackPressed() {
        finish();
    }

}