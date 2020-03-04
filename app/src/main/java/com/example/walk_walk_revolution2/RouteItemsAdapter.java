//TODO we used https://guides.codepath.com/android/using-the-recyclerview for a tutorial
package com.example.walk_walk_revolution2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

public class RouteItemsAdapter extends
        RecyclerView.Adapter<RouteItemsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView startPointTextView;
        public TextView stepCountTextView;
        public TextView distanceTextView;
        public TextView timeTextView;
        public Button viewRouteButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            this.nameTextView = (TextView) itemView.findViewById(R.id.name);
            this.startPointTextView = (TextView) itemView.findViewById(R.id.startPoint);
            this.stepCountTextView = (TextView) itemView.findViewById(R.id.stepCount);
            this.distanceTextView = (TextView) itemView.findViewById(R.id.distance);
            this.timeTextView = (TextView)itemView.findViewById(R.id.timeDisplay);
            this.viewRouteButton = (Button) itemView.findViewById(R.id.viewDetailsButton);



            this.viewRouteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            RouteItem routeItem = routeItemsList.get(position);
            routeItem.launchRouteDetails();
        }
    }
    // Store a member variable for the contacts
    private List<RouteItem> routeItemsList;

    // Pass in the contact array into the constructor
    public RouteItemsAdapter(List<RouteItem> routeItemList) {
        this.routeItemsList = routeItemList;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public RouteItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View routeItemView = inflater.inflate(R.layout.route_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(routeItemView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RouteItemsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        RouteItem routeItem = this.routeItemsList.get(position);

        // Set item views based on your views and data model
        TextView textViewName = viewHolder.nameTextView;
        textViewName.setText(routeItem.getName());

        TextView textViewStartPoint = viewHolder.startPointTextView;
        textViewStartPoint.setText(routeItem.getStartPoint());

        TextView textViewStepCount = viewHolder.stepCountTextView;
        textViewStepCount.setText(Integer.toString(routeItem.getStepCount()));

        TextView textViewDistance = viewHolder.distanceTextView;
//        double textDistance = Math.round(routeItem.getDistance() * 10) / 10.0;

        DecimalFormat df = new DecimalFormat("#.##");
        double result = Double.valueOf(df.format(routeItem.getDistance()));

//        System.out.println(textDistance);
        textViewDistance.setText(Double.toString(result));

        TextView textViewTime = viewHolder.timeTextView;
        textViewTime.setText(routeItem.getTime());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return this.routeItemsList.size();
    }

}
