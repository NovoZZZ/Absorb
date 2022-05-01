package edu.neu.absorb;

import android.graphics.Color;
import android.util.Log;
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
import java.util.Locale;
import java.util.TimeZone;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.neu.absorb.utils.TimeUtil;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.MyViewHolder>{
    private List<FocusHistoryDetail> focusHistoryList;

    public HistoryRecyclerAdapter(List<FocusHistoryDetail> userList) {
        this.focusHistoryList = userList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // The followings are textview that used in history item list
        private TextView historyDescription;
        private TextView startTime;
        private TextView endTime;
        private TextView createTime;
        private TextView updateTime;
        private View horizontalDivider;
        private CircleImageView historyAwardPic;

        public MyViewHolder(final View view) {
            super(view);
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
        holder.historyDescription.setText("Focus Task: " + (focusHistoryList.get(position).getDescription().equals("") ?
                "Normal Focus Task" : focusHistoryList.get(position).getDescription()));

        DateTime start = DateUtil.parse(focusHistoryList.get(position).getStartTime());
        holder.startTime.setText("Start Time: " + start.toStringDefaultTimeZone());
        DateTime end = DateUtil.parse(focusHistoryList.get(position).getEndTime());
        holder.endTime.setText("End Time: " + end.toStringDefaultTimeZone());

        Integer intervalMinutes = TimeUtil.getIntervalMinutes(start, end);

        if (intervalMinutes >= 10) {
            holder.historyAwardPic.setImageResource(R.drawable.bubbletree_nobg);
        } else if (intervalMinutes >= 5) {
            holder.historyAwardPic.setImageResource(R.drawable.pinetree_nobg);
        } else {
            holder.historyAwardPic.setImageResource(R.drawable.leave_nobg);
        }
        //      Time start = new Time(focusHistoryList.get(position).getStartTime());

        //     holder.historyAwardPic.setImageResource(R.drawable.gold_medal);
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
