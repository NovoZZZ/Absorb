package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import edu.neu.absorb.utils.ApiUtil;
import okhttp3.Request;
import okhttp3.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    Button changepass;
    EditText username,oldpass,newpass;
    TextView errmess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        changepass=findViewById(R.id.btn_changepass);
        username=findViewById(R.id.et_changeusername);
        oldpass=findViewById(R.id.et_oldpassword);
        newpass=findViewById(R.id.et_newpassword);
        errmess=findViewById(R.id.tv_errmess);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // request body
                Map<String, Object> jsonBody = new HashMap<>();
                jsonBody.put("username", username.getText().toString());
                jsonBody.put("password", oldpass.getText().toString());


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
                    JSONObject object = new JSONObject(jsonObject);
                    JSONObject dataJSON = object.getJSONObject("data");
                    dataJSON.remove("username");
                    dataJSON.remove("nickname");
                    Log.d("dataJSON",dataJSON.toString());

                    if (jsonObject.get("message").toString().equals("Login failed")) {
                        runOnUiThread(() -> {
                            errmess.setText("Please check your username and password again");
                            errmess.setTextColor(Color.RED);
                        });
                    }



                    else{
                        runOnUiThread(() -> {
                            errmess.setText("Password Changed");
                            errmess.setTextColor(Color.GREEN);
                        });
                        Map<String, Object> jsonBody2 = new HashMap<>();
                        jsonBody2.put("userId", dataJSON.get("userId").toString());
                        jsonBody2.put("oldPassword", oldpass.getText().toString());
                        jsonBody2.put("newPassword", newpass.getText().toString());
                        jsonBody2.put("token", dataJSON.get("token").toString());
                        Request request2 = ApiUtil.buildRequest(ApiUtil.CHANGE_PASSWORD_API, null, jsonBody2);
                        new Thread(() -> {
                            String responseBodyStr2 = null;
                            try (Response response2 = ApiUtil.client.newCall(request2).execute()) {
                                responseBodyStr2 = response2.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }


                }).start();
            }
        });
    }
}