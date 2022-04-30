package edu.neu.absorb;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.MyViewHolder>{
    private List<FocusHistoryDetail> focusHistoryList;

    public HistoryRecyclerAdapter(List<FocusHistoryDetail> userList) {
        this.focusHistoryList = userList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // The followings are textview that used in history item list
        private TextView historyID;
        private TextView historyDescription;
        private TextView startTime;
        private TextView endTime;
        private TextView createTime;
        private TextView updateTime;
        private View horizontalDivider;

        public MyViewHolder(final View view) {
            super(view);
            historyID = view.findViewById(R.id.history_id);
            historyDescription = view.findViewById(R.id.history_description);
            startTime = view.findViewById(R.id.history_start_time);
            endTime = view.findViewById(R.id.history_end_time);
            horizontalDivider = view.findViewById(R.id.history_horizontal_divider);
          //  createTime = view.findViewById(R.id.history_create_time);
         //   updateTime = view.findViewById(R.id.history_update_time);
        }
    }

    @NonNull
    @Override
    public HistoryRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item,
                parent, false);
        return new HistoryRecyclerAdapter.MyViewHolder(itemView);

    }

    /**
     * Implement history list item content
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerAdapter.MyViewHolder holder, int position) {
        holder.historyID.setText("History ID: " + (position + 1));
        holder.historyID.setTextColor(Color.BLUE);
        holder.historyDescription.setText("Detail: " + (focusHistoryList.get(position).getDescription().equals("") ?
                "NULL" : focusHistoryList.get(position).getDescription()));
        holder.startTime.setText("Start Time: " + focusHistoryList.get(position).getStartTime());
        holder.endTime.setText("End Time: " + focusHistoryList.get(position).getEndTime());
      //  holder.horizontalDivider.setBackgroundColor(Color.BLACK);
       // holder.createTime.setText("Create Time: " + focusHistoryList.get(position).getCreateTime());
      //  holder.updateTime.setText("Update Time: " + focusHistoryList.get(position).getUpdateTime());
    }

    @Override
    public int getItemCount() {
        return focusHistoryList.size();
    }
}
