package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

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

public class HistoryActivity extends AppCompatActivity {

    // Used to fulfill history recycler addapter
    public static List<FocusHistoryDetail> focusHistoryDetailList;
    // Focus history page recycler view
    private RecyclerView recyclerView;

    private TextView userName;

    private de.hdodenhof.circleimageview.CircleImageView profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Initialize history recycler view
        focusHistoryDetailList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_profile_history);

        profile_pic = findViewById(R.id.iv_profile_avatar);
        profile_pic.setImageResource(R.drawable.flowers);

        userName = findViewById(R.id.tv_profile_nickname);
        userName.setText("User 1");

        // Set focus history adapter
        setAdapter();
    }


    /**
     * Set focus history page recycler adapter
     */
    private void setAdapter() {
        Context context= MyApplication.getAppContext();
        List<String> resArr = FileUtil.readJson(context, "token");
        String strRes = resArr.size() != 0 ? resArr.get(0) : "";

        // Deserialize json
        Gson gson = new Gson();
        LoginInfo loginInfo = gson.fromJson(strRes, LoginInfo.class);

        // FIXME: 4/25/22
        Integer userId = loginInfo.getUserId();
        String token = loginInfo.getToken();

        /**
         * Test purpose only
        Integer userId = 9;
        String token = "0788ad5e-c5b0-4a43-a744-547353d763b4";
         **/

        // Get histiory list request
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("user_id", String.valueOf(userId));
        pathVariables.put("token", token);
        // build request
        Request request = ApiUtil.buildRequest(ApiUtil.GET_HISTORY_LIST_API, pathVariables, null);
        // call api
        new Thread(() -> {
            String responseBodyStr = null;
            try (Response response = ApiUtil.client.newCall(request).execute()) {
                responseBodyStr = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String finalResponseBodyStr = responseBodyStr;
            FocusHistory focusHistory = gson.fromJson(finalResponseBodyStr, FocusHistory.class);
            for (FocusHistoryDetail detail : focusHistory.getFocusHistoryDetailList()) focusHistoryDetailList.add(detail);
            // Start new thread to set up the adapter
                runOnUiThread(() -> {
                HistoryRecyclerAdapter adapter = new HistoryRecyclerAdapter(focusHistoryDetailList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            });

        }).start();
    }
}