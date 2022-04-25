package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Date;

import edu.neu.absorb.utils.ApiUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FocusActivity extends AppCompatActivity {

    private TextView tvFocusTime;
    private EditText etFocusDescription;
    private Button btnFinish;

    // start time of this focus task
    private Date startTime;
    // end time of this focus task
    private Date endTime;

    // seconds that last since the activity starts
    private int seconds;

    // mark the focus task is finished or not. it's false by default
    private boolean isFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);

        tvFocusTime = findViewById(R.id.tv_focus_time);
        etFocusDescription = findViewById(R.id.et_focus_description);
        btnFinish = findViewById(R.id.btn_finish_focus);

        // click listener of finish button
        btnFinish.setOnClickListener(view -> {
            // mark focus task finished
            isFinished = true;
            // TODO: call add focus api
            // TODO: redirect to result page
        });

        startCounting();

    }

    /**
     * Start counting time. It will change the focus time.
     */
    private void startCounting() {
        // init start time
        startTime = new Date();
        // start a thread to do counting, which will also change the time
        new Thread(() -> {
            while (!isFinished) {
                // update seconds
                seconds += 1;
                // convert seconds to time
                String time = convertSecondsToTime();
                // update ui
                runOnUiThread(() -> tvFocusTime.setText(time));
                // sleep 1 second
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String convertSecondsToTime() {
        StringBuilder result = new StringBuilder();
        if (seconds < 10) {
            result.append("00:0").append(seconds);
        } else if (seconds < 60) {
            result.append("00:").append(seconds);
        } else {
            int hours = seconds / 60;
            if (hours < 10) {
                result.append("0");
            }
            result.append(hours).append(":");
            int newSeconds = seconds % 60;
            if (newSeconds < 10) {
                result.append("0");
            }
            result.append(newSeconds);
        }
        return result.toString();
    }
}