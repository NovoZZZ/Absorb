package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.absorb.utils.ApiUtil;
import edu.neu.absorb.utils.FileUtil;
import edu.neu.absorb.utils.MyApplication;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;


public class LeaderboardActivity extends AppCompatActivity {

    // Used to fulfill leaderboard recycler adapter
    private List<User> userList;
    // Leaderboard page recycler view
    private RecyclerView recyclerView;

    // Leaderboard page divider
    private View horiDivider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        userList = new ArrayList<>();
        recyclerView = findViewById(R.id.leaderboard_link_collector);
        horiDivider = findViewById(R.id.leaderboard_page_divider);
        horiDivider.setBackgroundColor(Color.BLUE);
        getLeaderboardInfo();
    }

    private void setAdapter() {
        LeaderboardRecyclerAdapter adapter = new LeaderboardRecyclerAdapter(userList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    /**
     * Get leaderboard info. and set up the recycler view
     */
    private void getLeaderboardInfo() {
            // path variables
            Map<String, String> pathVariables = new HashMap<>();
            pathVariables.put("total", "1");
            // build request
            Request request = ApiUtil.buildRequest(ApiUtil.LEADERBOARD_INFO_API, pathVariables, null);
            // call api
            new Thread(() -> {
                String responseBodyStr = null;
                try (Response response = ApiUtil.client.newCall(request).execute()) {
                    responseBodyStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String finalResponseBodyStr = responseBodyStr;
                Gson gson = new Gson();
                UserInfo user = gson.fromJson(finalResponseBodyStr, UserInfo.class);
                User[] users = user.getUserList().toArray(new User[0]);
                for (User a : users) userList.add(a);

                runOnUiThread(() -> {
                    LeaderboardRecyclerAdapter adapter = new LeaderboardRecyclerAdapter(userList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                });


            }).start();

    }

}