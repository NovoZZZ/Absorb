package edu.neu.absorb;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        private CircleImageView historyAwardPic;

        public MyViewHolder(final View view) {
            super(view);
            historyID = view.findViewById(R.id.history_id);
            historyDescription = view.findViewById(R.id.history_description);
            startTime = view.findViewById(R.id.history_start_time);
            endTime = view.findViewById(R.id.history_end_time);
            horizontalDivider = view.findViewById(R.id.history_horizontal_divider);
            historyAwardPic = view.findViewById(R.id.history_award_pic);
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
        String start = focusHistoryList.get(position).getStartTime();
        holder.startTime.setText("Start Time: " + start);
        String end = focusHistoryList.get(position).getEndTime();
        holder.endTime.setText("End Time: " + focusHistoryList.get(position).getEndTime());
        int startDate = Integer.valueOf(start.substring(8, 10));
        int startHour = Integer.valueOf(start.substring(11, 13));
        int startMin = Integer.valueOf(start.substring(14, 16));
        int endDate = Integer.valueOf(end.substring(8, 10));
        int endHour = Integer.valueOf(end.substring(11, 13));
        int endMin = Integer.valueOf(end.substring(14, 16));
        long startSec = startDate * 24 * 60 * 60 + startHour * 60 * 60 + startMin * 60;
        long endSec = endDate * 24 * 60 * 60 + endHour * 60 * 60 + endMin * 60;
        if (endSec - startSec >= 1200) {
            holder.historyAwardPic.setImageResource(R.drawable.bubbletree_nobg);
        } else if (endSec - startSec >= 600) {
            holder.historyAwardPic.setImageResource(R.drawable.pinetree_nobg);
        } else {
            holder.historyAwardPic.setImageResource(R.drawable.leave_nobg);
        }
  //      Time start = new Time(focusHistoryList.get(position).getStartTime());
        
   //     holder.historyAwardPic.setImageResource(R.drawable.gold_medal);
      //  holder.horizontalDivider.setBackgroundColor(Color.BLACK);
       // holder.createTime.setText("Create Time: " + focusHistoryList.get(position).getCreateTime());
      //  holder.updateTime.setText("Update Time: " + focusHistoryList.get(position).getUpdateTime());
    }

    @Override
    public int getItemCount() {
        return focusHistoryList.size();
    }
}
