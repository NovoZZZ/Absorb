package edu.neu.absorb;

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
        private ImageView userAvator;

        public MyViewHolder(final View view) {
            super(view);
            userName = view.findViewById(R.id.leaderboard_username);
            userScore = view.findViewById(R.id.leaderboard_userscore);
            userRank = view.findViewById(R.id.leaderboard_userrank);
            userAvator = view.findViewById(R.id.leaderboard_useravator);
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
        Collections.sort(userList, (a, b) -> (int)(b.getTotalHour() * 100) - (int)(a.getTotalHour() * 100));
        String name = userList.get(position).getUserName();
        int score = (int)Math.floor(userList.get(position).getTotalHour() * 60);
        int rank = position + 1;

        holder.userAvator.setImageResource(R.drawable.comic_trees);
        holder.userName.setText("Name: " + name);
        holder.userScore.setText("Score: " + String.valueOf(score));
        holder.userRank.setText("Rank: " + String.valueOf(rank));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
