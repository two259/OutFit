package com.example.outfit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SocialScreenAdapter extends RecyclerView.Adapter<SocialScreenAdapter.ViewHolder> {
    Context context;
    List<User> users; //will need to change to user messaging

    public SocialScreenAdapter(Context context, List<User> list) {
        this.users = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View socialView = LayoutInflater.from(context).inflate(R.layout.message_view, parent, false);
        return new ViewHolder(socialView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView userName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
        }

        public void bind(User user) {
            userName.setText(user.userName);
        }
    }
}
