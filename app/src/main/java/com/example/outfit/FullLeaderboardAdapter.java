package com.example.outfit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FullLeaderboardAdapter extends RecyclerView.Adapter<FullLeaderboardAdapter.ViewHolder>{

    Context context;
    List<LeaderboardItem> leadList;

    public FullLeaderboardAdapter(Context context, List<LeaderboardItem> list){
        this.leadList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inCompView = LayoutInflater.from(context).inflate(R.layout.leaderboard_item, parent, false);
        return new ViewHolder(inCompView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderboardItem leaderboardItem = leadList.get(position);
        holder.bind(leaderboardItem);
    }

    @Override
    public int getItemCount() {
        return leadList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameScore = itemView.findViewById(R.id.leader_item);
        }

        public void bind(LeaderboardItem item) {
            nameScore.setText("User: "+item.getUsername()+" -- Score: "+item.getScore());
        }

    }
}
