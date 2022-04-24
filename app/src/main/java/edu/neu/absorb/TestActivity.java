package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import edu.neu.absorb.utils.ApiUtil;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {

    private TextView tvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvResponse = findViewById(R.id.tv_test_response);

        // post request
        findViewById(R.id.btn_test_login).setOnClickListener(view -> {
            // request body
            Map<String, Object> jsonBody = new HashMap<>();
            jsonBody.put("username", "admin");
            jsonBody.put("password", "admin");
            // build request
            Request request = ApiUtil.buildRequest(ApiUtil.LOGIN_API, null, jsonBody);

            new Thread(() -> {
                String responseBodyStr = null;
                try (Response response = ApiUtil.client.newCall(request).execute()) {
                    responseBodyStr = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // parse response to Object
                JSONObject jsonObject = JSONUtil.parseObj(responseBodyStr);
                Log.d("Test activity", jsonObject.toString());

                // or show in ui
                String finalResponseBodyStr = responseBodyStr;
                runOnUiThread(() -> {
                    this.tvResponse.setText(finalResponseBodyStr);
                });

            }).start();

        });

        // get request
        findViewById(R.id.btn_test_user_info).setOnClickListener(view -> {
            // path variables
            Map<String, String> pathVariables = new HashMap<>();
            pathVariables.put("id", "1");
            pathVariables.put("token", "b5f4564d-28d4-4510-b12c-c412a48a40ae");
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
                String finalResponseBodyStr = responseBodyStr;
                runOnUiThread(() -> {
                    this.tvResponse.setText(finalResponseBodyStr);
                });
            }).start();
        });

        // get leaderboard info test
        findViewById(R.id.btn_test_leaderboardinfo).setOnClickListener(view -> {
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
                runOnUiThread(() -> {
                    this.tvResponse.setText(finalResponseBodyStr);
                });
            }).start();
        });
    }
}