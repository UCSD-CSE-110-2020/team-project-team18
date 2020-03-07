package com.example.walk_walk_revolution;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProposedWalkAdapter extends RecyclerView.Adapter<ProposedWalkAdapter.ViewHolder> {
    //    private Context context;
    private List<String> MainImageUploadInfoList;

    ProposedWalkAdapter(Context context, List<String> TempList) {
        this.MainImageUploadInfoList = TempList;
//        this.context = context;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.proposed_walk_item, parent, false);
//        ViewHolder viewHolder = new ViewHolder(view);
//        return viewHolder;
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.proposed_walk_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String attendantDetail = MainImageUploadInfoList.get(position);
        holder.attendantEmail.setText(attendantDetail);
        //holder.attendantName.setText(attendantDetail.getName());
        //holder.attendantStatus.setText(attendantDetail.getStatus());
    }

    @Override
    public int getItemCount() {
        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView attendantEmail;
        //public TextView attendantStatus;

        ViewHolder(View itemView) {
            super(itemView);
            attendantEmail = (TextView) itemView.findViewById(R.id.emailTextView);
            //attendantStatus = (TextView) itemView.findViewById(R.id.statusTextView);
        }
    }

}

