package com.example.android.kirpitch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.kirpitch.model.Task;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ARVAdapter extends RecyclerView.Adapter<ARVAdapter.ApplicationViewHolder> {

    private List<Task> mData;
    MainActivity mContext;

    ARVAdapter(MainActivity context) {
        mContext = context;
    }


    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item_layout, parent, false);
        return new ApplicationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
                Task item = mData.get(position);
        holder.applicationName
                .setText(item.getTitle());

        holder.applicationLocation
                .setText(item.getLocation());

        holder.applicationDate
                .setText(item.getDate());
        holder.applicationStatus
                .setText("Stage: " +Utility.getStage(item.getStatus()));
        if (item.getStatus() == 6 || item.getStatus() == 1){
            holder.container.setBackgroundColor(mContext.getResources().getColor(R.color.colorRed));
        }
        else if (item.getStatus() == 5){
            holder.container.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreen));
        }
    }

    void setData(List<Task> data){
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData!=null?mData.size(): 0;
    }

    class ApplicationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView applicationName;
        TextView applicationLocation;
        TextView applicationDate;
        TextView applicationStatus;
        LinearLayout container;

        ApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            applicationName = itemView.findViewById(R.id.title);
            applicationLocation = itemView.findViewById(R.id.location);
            applicationDate = itemView.findViewById(R.id.date);
            applicationStatus = itemView.findViewById(R.id.application_status);
            container = itemView.findViewById(R.id.container);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mContext.onClick(position);
        }
    }


}
