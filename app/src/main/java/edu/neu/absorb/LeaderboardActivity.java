package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.absorb.utils.ApiUtil;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;


public class LeaderboardActivity extends AppCompatActivity {

    private List<User> userList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        userList = new ArrayList<>();
        recyclerView = findViewById(R.id.leaderboard_link_collector);
        getLeaderboardInfo();
       // setAdapter();
    }

    private void setAdapter() {
        LeaderboardRecyclerAdapter adapter = new LeaderboardRecyclerAdapter(userList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

/**
    private void getLeaderboardInfo() {
        LeaderboardInfoInterface leaderboardInfoInterface =
                LeaderboardInfoBuilder.getRetrofitInstance().create(LeaderboardInfoInterface.class);
        Call<JsonObject> call = leaderboardInfoInterface.getLeaderboardInfo();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("response", String.valueOf(response.body()));
                JsonObject res = response.body();
                JsonObject data = res.getAsJsonObject("data");
                // FIXME: 4/24/22
                userList.add(new User(String.valueOf(data), 1, 1));

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
            }
        });
    }
**/


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

              //  while (!finalResponseBodyStr.substring(index, index + 4).equals("data")) index++;
              //  for (int i = index; i < finalResponseBodyStr.length(); i++) {
              //      userList.add(new User(finalResponseBodyStr.substring(i, i + 1), 1, 1));
              //  }
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