package com.example.footballfinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.footballfinder.databinding.ActivityMarkerEventsBinding;
import java.util.ArrayList;
import java.util.Objects;

public class MarkerEvents extends AppCompatActivity {

    public static RecyclerView recyclerView;
    public static MyListEventAdapter adapter;
    public static ArrayList<Event> viewableEvents;
    private ArrayList<Event> events;
    private LinearLayoutManager manager;
    private int markerId;
    private int userID;

    private boolean isScrolling;                        // boolean telling if the user is scrolling
    int currentItems, totalItems, scrollOutItems;       // variables used for getting the position of the screen in the recyclerview


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set binding
        com.example.footballfinder.databinding.ActivityMarkerEventsBinding binding = ActivityMarkerEventsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // add toolbar
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());
        // add ability to create new event
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddEvent.class);
            intent.putExtra("fieldId", markerId);
            intent.putExtra("userID", userID);
            startActivity(intent);
        });

        if (Internet.internetConnectionAvailable(this)){
            events = new ArrayList<>();
            viewableEvents = new ArrayList<>();
            if (events.size() > 20)
                viewableEvents = new ArrayList<>(events.subList(0, 20));
            else
                viewableEvents = new ArrayList<>(events.subList(0, events.size()));

            viewableEvents.add(new Event(1, 2, 15, 4, "This is description nr 1", 1423412, 23151325));

            recyclerView = findViewById(R.id.events);
            manager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(manager);
            adapter = new MyListEventAdapter(viewableEvents);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

            DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
            itemDecorator.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider)));
            recyclerView.addItemDecoration(itemDecorator);

            scrolling();
        }
    }

    /*
     * If back button is pressed, terminate activity
     */
    @Override
    public void onBackPressed() {
        finish();
    }

    /*
     * Load more events on scrolling
     */
    public void scrolling(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            // we tell the app that the user is scrolling
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                // if bottom of the list is reached, load 5 more events
                if (isScrolling && (currentItems + scrollOutItems == totalItems)){
                    isScrolling = false;
                    new Handler().postDelayed(() -> {
                        for (int i = 0; i < 5; i++){
                            if (viewableEvents.size() < events.size()-1){
                                viewableEvents.add(viewableEvents.size(), events.get(viewableEvents.size() + 1));
                                adapter.notifyItemInserted(viewableEvents.size());
                            }
                        }
                    }, 500);
                }
            }
        });
    }

}