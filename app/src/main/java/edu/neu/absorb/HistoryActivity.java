package edu.neu.absorb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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
    private TextView tvNickname;
    private TextView tvCreateTime;
    private TextView tvFocusHours;
    private TextView tvFocusTaskCount;
    private TextView tvScore;
    private de.hdodenhof.circleimageview.CircleImageView ivAvatar;
    private ImageButton ibChangeNickname;
    private Button btnLogout;
    private Button btnSecurity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Initialize history recycler view
        focusHistoryDetailList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_profile_history);

        ivAvatar = findViewById(R.id.iv_profile_avatar);

        tvNickname = findViewById(R.id.tv_profile_nickname);
        tvCreateTime = findViewById(R.id.tv_profile_create_time);
        tvFocusHours = findViewById(R.id.tv_profile_total_hours);
        tvFocusTaskCount = findViewById(R.id.tv_profile_focus_count);
        tvScore = findViewById(R.id.tv_profile_score);
        ibChangeNickname = findViewById(R.id.btn_profile_change_nickname);
        // Change nickname
        ibChangeNickname.setOnClickListener(view -> {
            // build dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Nickname");
            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newNickname = input.getText().toString();
                    // change nickname
                    changeNickname(newNickname);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        });

        btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(view -> {
            // log out
            FileUtil.deleteJson(this,"token");

            // restart app
            PackageManager packageManager = this.getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(this.getPackageName());
            ComponentName componentName = intent.getComponent();
            Intent mainIntent = Intent.makeRestartActivityTask(componentName);
            this.startActivity(mainIntent);
            Runtime.getRuntime().exit(0);
        });

        btnSecurity = findViewById(R.id.btn_profile_security);
        btnSecurity.setOnClickListener(view -> {
            // change password
            Intent intent = new Intent(this, ChangePasswordActivity.class);
            startActivity(intent);

        });

        // init user info
        initUserInfo();

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

    private void initUserInfo() {
        // get login info
        LoginInfo loginInfo = FileUtil.getLoginInfo();
        // get user info
        if (loginInfo == null) {
            throw new RuntimeException("NO USER LOGIN");
        }

        // path variables
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", Integer.toString(loginInfo.getUserId()));
        pathVariables.put("token", loginInfo.getToken());
        // build request
        Request request = ApiUtil.buildRequest(ApiUtil.USER_INFO_API, pathVariables, null);
        // call api
        new Thread(() -> {
            String responseBodyStr = null;
            try (Response response = ApiUtil.client.newCall(request).execute()) {
                responseBodyStr = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // parse response
            JSONObject jsonResponse = JSONUtil.parseObj(responseBodyStr);
            JSONObject data = jsonResponse.getJSONObject("data");
            if (data == null) {
                throw new RuntimeException("ERROR -- unable to get user info");
            }
            runOnUiThread(() -> {
                // nickname
                tvNickname.setText(data.getStr("nickname"));
                // create time
                Date createTime = data.getDate("createTime");
                DateFormat dateInstance = SimpleDateFormat.getDateInstance();
                tvCreateTime.setText("Since: " + dateInstance.format(createTime));
                // focus hours
                tvFocusHours.setText("Total Focus Hours: " + data.getInt("totalHours"));
                // focus count
                tvFocusTaskCount.setText(data.getInt("focusCount") + " Tasks");
                // score
                tvScore.setText("Score: " + data.getInt("score"));
            });
        }).start();

    }

    private void changeNickname(String nickname) {
        // get login info
        LoginInfo loginInfo = FileUtil.getLoginInfo();
        if (loginInfo == null) {
            throw new RuntimeException("NO USER LOGIN");
        }
        // request body
        Map<String, Object> requestBody = new HashMap<>();
        // user id
        requestBody.put("userId", loginInfo.getUserId());
        // token
        requestBody.put("token", loginInfo.getToken());
        // nickname
        requestBody.put("nickname", nickname);
        // build request
        Request request = ApiUtil.buildRequest(ApiUtil.CHANGE_NICKNAME_API, null, requestBody);

        // call api
        new Thread(() -> {
            String responseBodyStr = null;
            try (Response response = ApiUtil.client.newCall(request).execute()) {
                responseBodyStr = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // parse response to Object
            JSONObject jsonObject = JSONUtil.parseObj(responseBodyStr);
            Log.d("Focus activity", jsonObject.toString());
            // refresh user info
            initUserInfo();
        }).start();
    }
}