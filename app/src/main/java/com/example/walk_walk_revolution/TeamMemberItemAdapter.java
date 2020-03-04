package com.example.walk_walk_revolution;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TeamMemberItemAdapter extends RecyclerView.Adapter<TeamMemberItemAdapter.ViewHolder>{

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView initialsView;
        public TextView nameView;
        public ViewHolder(View itemView) {
            super(itemView);

            this.initialsView = (TextView) itemView.findViewById(R.id.initials_view);
            this.nameView = (TextView) itemView.findViewById(R.id.name_view);
        }
    }
    private ArrayList<TeamMemberItem> teamMemberItemList;

    // Pass in the contact array into the constructor
    public TeamMemberItemAdapter(ArrayList<TeamMemberItem> teamMemberItemList) {
        this.teamMemberItemList = teamMemberItemList;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public TeamMemberItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View teamMemberItemView = inflater.inflate(R.layout.team_member_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(teamMemberItemView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TeamMemberItemAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        TeamMemberItem teamMemberItem = teamMemberItemList.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameView;
        textView.setText(teamMemberItem.getName());
        TextView initialsView = viewHolder.initialsView;
        initialsView.setText(teamMemberItem.getInitials());
        initialsView.setBackgroundColor(teamMemberItem.getColor());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return teamMemberItemList.size();
    }
}
