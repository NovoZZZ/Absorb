package edu.neu.absorb;

import static org.json.JSONObject.NULL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import edu.neu.absorb.utils.ApiUtil;
import edu.neu.absorb.utils.FileUtil;
import edu.neu.absorb.utils.MyApplication;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    EditText et_loginuser,et_loginpass;
    Button btnlogin,btnsignup;
    TextView errview,changepassword;

    private Context context= MyApplication.getAppContext();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        errview=findViewById(R.id.tv_error);
        btnsignup=findViewById(R.id.btn_signup);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }

        });


        et_loginuser=findViewById(R.id.et_loginusername);
        et_loginpass=findViewById(R.id.et_loginpassword);
        changepassword=findViewById(R.id.tv_changepassword);
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        btnlogin=findViewById(R.id.btn_login);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // request body
                Map<String, Object> jsonBody = new HashMap<>();
                jsonBody.put("username", et_loginuser.getText().toString());
                jsonBody.put("password", et_loginpass.getText().toString());
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


                    if (jsonObject.get("message").toString().equals("Login failed")) {
                        runOnUiThread(() -> {
                            errview.setText("Please check your username and password again");
                            errview.setTextColor(Color.RED);
                        });
                    }

                    else{
                        //edit json
                        JSONObject object = new JSONObject(jsonObject);
                        JSONObject dataJSON = object.getJSONObject("data");
                        dataJSON.remove("username");
                        dataJSON.remove("nickname");
                        Log.d("dataJSON",dataJSON.toString());

                        // save json to local file
                        FileUtil.writeJson(context,dataJSON.toString(), "token",false);
                        //read json from local file
                        Log.d("read json",FileUtil.readJson(context,"token").toString());
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                    }


                }).start();
                //check code of jsonObject

            }
        });
    }
}