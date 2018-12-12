package com.example.android.kirpitch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.kirpitch.model.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ARVAdapter extends RecyclerView.Adapter<ARVAdapter.ApplicationViewHolder> {

    private Map<String, Task> mData;
    private MainActivity mContext;
    private List<String> keys;

    ARVAdapter(MainActivity context) {
        mContext = context;
        mData = new HashMap<>();
        keys = new ArrayList<>();
        setHasStableIds(true);
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
        Task item = mData.get(keys.get(position));
        holder.applicationName
                .setText(item.getTitle() + " at " + item.getCompanyName());

        holder.applicationLocation
                .setText(item.getLocation());

        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(item.getDate());
        holder.applicationDate
                .setText(
                        String.format(Locale.getDefault(), "%02d %s %d", currentDate.get(Calendar.DAY_OF_MONTH),
                                currentDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()),
                                currentDate.get(Calendar.YEAR)));
        holder.applicationStatus
                .setText("Stage: " + Utility.getStage(item.getStatus()));
        if (item.getStatus() == 6 || item.getStatus() == 1) {
            holder.container.setBackgroundColor(mContext.getResources().getColor(R.color.colorRed));
        } else if (item.getStatus() == 5) {
            holder.container.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreen));
        }
    }

    void addDataItem(String key, Task dataItem) {
        mData.put(key, dataItem);
        keys.add(key);
        notifyDataSetChanged();
    }

    private void addKey(String dataKey) {
        keys.add(dataKey);
    }

    void removeDataItem(String key) {
        mData.remove(key);
        keys.remove(key);
        notifyDataSetChanged();
    }

    private void removeKey(String dataKey) {
        keys.remove(dataKey);
    }

    void updateItem(String key, Task newTask){
        mData.put(key, newTask);
        notifyDataSetChanged();
    }

    int getDataCount(){
        return mData != null ? mData.size() : 0;
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
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
            mContext.onClick(keys.get(position));
        }
    }


}
