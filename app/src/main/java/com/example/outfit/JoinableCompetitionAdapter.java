package com.example.outfit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JoinableCompetitionAdapter extends RecyclerView.Adapter<JoinableCompetitionAdapter.ViewHolder>{

    Context context;
    List<JoinableCompetitionObj> joinableCompetitionList;

    public JoinableCompetitionAdapter(Context context, List<JoinableCompetitionObj> list) {
        this.joinableCompetitionList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View searchCompView = LayoutInflater.from(context).inflate(R.layout.search_comp_recycler_item, parent, false);
        return new ViewHolder(searchCompView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JoinableCompetitionObj joinableCompetitionObj = joinableCompetitionList.get(position);
        holder.bind(joinableCompetitionObj);
    }

    @Override
    public int getItemCount() {
        // Replace with correct list
        return joinableCompetitionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView competitionName;
        TextView competitionDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            competitionName = itemView.findViewById(R.id.compNameSearch);
            competitionDate = itemView.findViewById(R.id.startfinishdate);
        }

        public void bind(JoinableCompetitionObj uco) {
            competitionName.setText(uco.getCompetitionName());
            competitionDate.setText(uco.getCompetitionStartDate()+" - " +uco.getCompetitionEndDate());
        }
    }

}
