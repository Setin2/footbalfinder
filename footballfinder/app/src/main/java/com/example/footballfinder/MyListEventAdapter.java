package com.example.footballfinder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/*
 * This class represents the adapter for the recyclerView containing events
 */
public class MyListEventAdapter extends RecyclerView.Adapter<MyListEventAdapter.ViewHolder>{
    private ArrayList<Event> events;

    /*
     * Constructor
     */
    public MyListEventAdapter(ArrayList<Event> viewableEvents) {
        this.events = viewableEvents;
    }

    /*
     * Create new view
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_events, parent, false);
        return new ViewHolder(view);
    }

    /*
     * Replace the content of a view
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Event event = this.events.get(position);

        // gonna have to find owner username based on event owner id
        holder.eventOwner.setText(event.owner_username);

        holder.description.setText(event.description);
        holder.maxParticipants.setText(String.valueOf(event.maxParticipant));

        holder.event_time.setText(event.start_time + " - " + event.end_time);
    }

    /*
     * Set max number of posts to be displayed
     */
    @Override
    public int getItemCount() {
        return events.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /*
     * Provide reference to the view for each item
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eventOwner;
        public TextView maxParticipants;
        public TextView description;
        public TextView event_time;
        ViewHolder(View itemView) {
            super(itemView);
            this.eventOwner = itemView.findViewById(R.id.eventOwner);
            this.maxParticipants = itemView.findViewById(R.id.maxParticipants);
            this.description = itemView.findViewById(R.id.description);
            this.event_time = itemView.findViewById(R.id.event_time);
        }
    }
}