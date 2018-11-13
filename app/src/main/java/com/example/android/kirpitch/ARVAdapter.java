package com.example.android.kirpitch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ARVAdapter extends RecyclerView.Adapter<ARVAdapter.ApplicationViewHolder> {

    private List<Application> applications;

    ARVAdapter(List<Application> applications) {
        this.applications = applications;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_item, parent, false);
        return new ApplicationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        holder.applicationName.setText(applications.get(position).application_name);
        holder.applicationStatus.setText(applications.get(position).application_status);
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    static class ApplicationViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView applicationName;
        TextView applicationStatus;

        ApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_item);
            applicationName = itemView.findViewById(R.id.application_nam);
            applicationStatus = itemView.findViewById(R.id.application_status);
        }
    }


}
