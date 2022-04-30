package edu.neu.absorb;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardRecyclerAdapter extends RecyclerView.Adapter<LeaderboardRecyclerAdapter.MyViewHolder> {

    private List<User> userList;

    public LeaderboardRecyclerAdapter(List<User> userList) {
        this.userList = userList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private TextView userScore;
        private TextView userRank;
        private ImageView medal;
        private de.hdodenhof.circleimageview.CircleImageView userAvator;

        public MyViewHolder(final View view) {
            super(view);
            userName = view.findViewById(R.id.leaderboard_username);
            userScore = view.findViewById(R.id.leaderboard_userscore);
            userRank = view.findViewById(R.id.leaderboard_userrank);
            userAvator = view.findViewById(R.id.leaderboard_useravator);
            medal = view.findViewById(R.id.leaderboard_medal_pic);
        }
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_list_item,
                parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardRecyclerAdapter.MyViewHolder holder, int position) {
        // Sort user's total online hour in descending order
        Collections.sort(userList, (a, b) -> b.getScore() - a.getScore());
        String name = userList.get(position).getUserName();
        int score = userList.get(position).getScore();
        int rank = position + 1;

        holder.userAvator.setImageResource(R.drawable.flowers);
        holder.userName.setText( name);
        holder.userName.setTextColor(Color.BLACK);
        holder.userScore.setText("Score: " + String.valueOf(score));
        holder.userScore.setTextColor(Color.BLUE);
        holder.userRank.setText("Rank: " + String.valueOf(rank));
        if (position == 0) holder.medal.setImageResource(R.drawable.gold_medal);
        else if (position == 1) holder.medal.setImageResource(R.drawable.silever_medal);
        else if (position == 2) holder.medal.setImageResource(R.drawable.bronze_medal);
        else holder.medal.setImageResource(R.drawable.white);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
