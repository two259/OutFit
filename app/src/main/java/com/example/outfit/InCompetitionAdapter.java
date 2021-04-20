package com.example.outfit;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InCompetitionAdapter extends RecyclerView.Adapter<InCompetitionAdapter.ViewHolder>{

    Context context;
    List<UserCompetitionsObj> userCompetitionsList;

    public InCompetitionAdapter(Context context, List<UserCompetitionsObj> list) {
        this.userCompetitionsList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inCompView = LayoutInflater.from(context).inflate(R.layout.in_competition_recycler_item, parent, false);
        return new ViewHolder(inCompView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserCompetitionsObj userCompetitionsObj = userCompetitionsList.get(position);
        holder.bind(userCompetitionsObj);
    }

    @Override
    public int getItemCount() {
        return userCompetitionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView competitionName;
        TextView competitionDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            competitionName = itemView.findViewById(R.id.competitionName);
            competitionDate = itemView.findViewById(R.id.dateBox);
        }

        public void bind(UserCompetitionsObj uco) {
            competitionName.setText(uco.getCompetitionName());
            competitionDate.setText(uco.getCompetitionStartDate()+" - " +uco.getCompetitionEndDate());
        }
    }
}
