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
    ClickInCompItemListener clickInCompItemListener;

    public InCompetitionAdapter(Context context, List<UserCompetitionsObj> list, ClickInCompItemListener cicil) {
        this.userCompetitionsList = list;
        this.context = context;
        this.clickInCompItemListener = cicil;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inCompView = LayoutInflater.from(context).inflate(R.layout.in_competition_recycler_item, parent, false);
        return new ViewHolder(inCompView, clickInCompItemListener);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView competitionName;
        TextView competitionDate;
        ClickInCompItemListener clickInCompItemListener;

        public ViewHolder(@NonNull View itemView, ClickInCompItemListener cicil) {
            super(itemView);
            competitionName = itemView.findViewById(R.id.competitionName);
            competitionDate = itemView.findViewById(R.id.dateBox);
            this.clickInCompItemListener = cicil;
            itemView.setOnClickListener(this);
        }

        public void bind(UserCompetitionsObj uco) {
            competitionName.setText(uco.getCompetitionName());
            competitionDate.setText(uco.getCompetitionStartDate()+" - " +uco.getCompetitionEndDate());
        }

        @Override
        public void onClick(View v) {
            clickInCompItemListener.clickedInCompItem(getAdapterPosition());
        }
    }

    public interface ClickInCompItemListener{
        void clickedInCompItem(int position);
    }
}
