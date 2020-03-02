package com.example.walk_walk_revolution;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeamItemAdapter extends RecyclerView.Adapter<TeamItemAdapter.ViewHolder>{
    private ArrayList<String> teamNames;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView initialsView;
        public TextView nameView;
        public ViewHolder(TextView itemView) {
            super(itemView);

            this.initialsView = (TextView) itemView.findViewById(R.id.initials_view);
            this.nameView = (TextView) itemView.findViewById(R.id.name_view);
        }
    }
}
