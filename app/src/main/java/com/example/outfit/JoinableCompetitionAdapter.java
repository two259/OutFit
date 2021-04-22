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
    ClickJoinItemListener clickJoinItemListener;

    public JoinableCompetitionAdapter(Context context, List<JoinableCompetitionObj> list, ClickJoinItemListener cjil) {
        this.joinableCompetitionList = list;
        this.context = context;
        this.clickJoinItemListener = cjil;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View searchCompView = LayoutInflater.from(context).inflate(R.layout.search_comp_recycler_item, parent, false);
        return new ViewHolder(searchCompView, clickJoinItemListener);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView competitionName;
        TextView competitionDate;
        ClickJoinItemListener clickJoinItemListener;

        public ViewHolder(@NonNull View itemView, ClickJoinItemListener cjil) {
            super(itemView);
            competitionName = itemView.findViewById(R.id.compNameSearch);
            competitionDate = itemView.findViewById(R.id.startfinishdate);
            this.clickJoinItemListener = cjil;
            itemView.setOnClickListener(this);
        }

        public void bind(JoinableCompetitionObj uco) {
            competitionName.setText(uco.getCompetitionName());
            competitionDate.setText(uco.getCompetitionStartDate()+" - " +uco.getCompetitionEndDate());
        }

        @Override
        public void onClick(View v) {
            clickJoinItemListener.clickedJoinItem(getAdapterPosition());
        }
    }

    public interface ClickJoinItemListener {
        void clickedJoinItem(int position);
    }

}
