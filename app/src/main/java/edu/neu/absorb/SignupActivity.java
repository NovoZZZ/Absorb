package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.neu.absorb.utils.ApiUtil;
import okhttp3.Request;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {
    EditText et_username,et_nickname,et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et_username=findViewById(R.id.et_registerusername);
        et_nickname=findViewById(R.id.et_registernickname);
        et_password=findViewById(R.id.et_registerpassword);

        findViewById(R.id.btn_registersignup).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View view){
                        // request body
                        Map<String, Object> jsonBody = new HashMap<>();
                        jsonBody.put("username", et_username.getText().toString());
                        jsonBody.put("nickname", et_nickname.getText().toString());
                        jsonBody.put("password", et_password.getText().toString());
                        Log.d("Test activity", jsonBody.toString());
                        // build request
                        Request request = ApiUtil.buildRequest(ApiUtil.REGISTER_API, null, jsonBody);
                        new Thread(() -> {
                            String responseBodyStr = null;
                            try (Response response = ApiUtil.client.newCall(request).execute()) {
                                responseBodyStr = response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }}).start();
                        //redirect
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
        );
    }
}