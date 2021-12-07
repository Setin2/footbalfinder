package com.example.footballfinder;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.footballfinder.databinding.ActivityAddEventBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AddEvent extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAddEventBinding binding;
    private int fieldId;
    private int userID;

    Button btnDatePicker, btnTimePicker;
    TextView txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get binding
        binding = ActivityAddEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        // add ability to create the event
        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(view -> {
            addEvent();
        });
        final View dialogView = View.inflate(AddEvent.this, R.layout.activity_add_event, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(AddEvent.this).create();

        pickDateTime();
        cal = Calendar.getInstance();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_event);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /*
     * If back button is pressed, terminate activity
     */
    @Override
    public void onBackPressed() {
        finish();
    }

    /*
     * Create a new event with given data
     */
    private void addEvent(){
        // some fields are left empty
        if (getMaxParticipants() == 0 || getDescription().isEmpty() || txtDate.getText().toString().isEmpty() || txtTime.getText().toString().isEmpty())
            Snackbar.make(findViewById(R.id.addEvent), "invalid details", Snackbar.LENGTH_SHORT).show();
        else {
            if (internetConnectionAvailable()){
                // get the start datetime
                Date startDate = cal.getTime();
                // get the end datetime
                cal.add(Calendar.HOUR, getPlayTime());
                Date endDate = cal.getTime();
                // create new event
                Event event = new Event(1, fieldId, getMaxParticipants(), userID, getDescription(), startDate.getTime(), endDate.getTime());
                // notify user
                Snackbar.make(findViewById(R.id.addEvent), "event added", Snackbar.LENGTH_SHORT).show();
                // add this event to the recyclerview
                new Handler().postDelayed(() -> {
                    MarkerEvents.viewableEvents.add(0, event);
                    MarkerEvents.adapter.notifyItemInserted(0);
                    ( (LinearLayoutManager) Objects.requireNonNull(MarkerEvents.recyclerView.getLayoutManager())).scrollToPositionWithOffset(0, 0);
                }, 500);
            }
            else
                Snackbar.make(findViewById(R.id.addEvent), "no internet connection", Snackbar.LENGTH_SHORT).show();
        }
    }

    /*
     * Get event description from input
     */
    private String getDescription(){
        EditText des = findViewById(R.id.descriptionInput);
        return des.getText().toString().trim();
    }

    /*
     * Get max participants from input
     */
    private int getMaxParticipants(){
        EditText max = findViewById(R.id.maxParticipantsInput);
        // input left empty
        if (max.getText().toString().trim().isEmpty())
            return 0;
        else
            return Integer.parseInt(max.getText().toString().trim());
    }

    /*
     * Get hours of playtime from input
     */
    private int getPlayTime(){
        EditText max = findViewById(R.id.playTime);
        return Integer.parseInt(max.getText().toString().trim());
    }

    /*
     * Add functionality to pick a date and time
     */
    private void pickDateTime(){
        // get buttons and textviews
        btnDatePicker = findViewById(R.id.btn_date);
        btnTimePicker = findViewById(R.id.btn_time);
        txtDate = findViewById(R.id.in_date);
        txtTime = findViewById(R.id.in_time);

        // date button onclicklistener
        btnDatePicker.setOnClickListener(view -> {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view1, year, monthOfYear, dayOfMonth) -> {

                        // set the text to the date chosen for visibility
                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        // time button onclicklistener
        btnTimePicker.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            @SuppressLint("SetTextI18n") TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view12, hourOfDay, minute) -> {

                        txtTime.setText(hourOfDay + ":" + minute);
                        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        });
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
            android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(AddEvent.this);
            dialog.setTitle( "ERROR" )
                    .setMessage("Task failed due to missing connection to internet")
                    .setPositiveButton("OK", (dialoginterface, i) -> {
                        dialoginterface.cancel();
                    }).show();
            return false;
        }
    }
}