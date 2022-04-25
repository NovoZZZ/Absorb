package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

import edu.neu.absorb.utils.ApiUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FocusActivity extends AppCompatActivity {

    private TextView tvFocusTime;
    private EditText etFocusDescription;
    private Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);

        tvFocusTime = findViewById(R.id.tv_focus_time);
        etFocusDescription = findViewById(R.id.et_focus_description);
        btnFinish = findViewById(R.id.btn_finish_focus);
    }


}