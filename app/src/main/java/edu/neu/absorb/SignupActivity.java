package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import edu.neu.absorb.utils.ApiUtil;
import okhttp3.Request;

public class SignupActivity extends AppCompatActivity {
    EditText et_username,et_nickname,et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et_username.findViewById(R.id.et_registerusername);
        et_nickname.findViewById(R.id.et_registernickname);
        et_password.findViewById(R.id.et_registerpassword);

        findViewById(R.id.btn_registersignup).setOnClickListener(view -> {
            // request body
            Map<String, Object> jsonBody = new HashMap<>();
            jsonBody.put("username",et_username.getText().toString());
            jsonBody.put("nickname",et_nickname.getText().toString());
            jsonBody.put("password",et_password.getText().toString());
            // build request
            Request request = ApiUtil.buildRequest(ApiUtil.REGISTER_API, null, jsonBody);

        }

        );
    }
}