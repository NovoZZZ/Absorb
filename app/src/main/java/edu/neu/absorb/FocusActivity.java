package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import edu.neu.absorb.utils.ApiUtil;
import edu.neu.absorb.utils.TimeUtil;
import okhttp3.Request;
import okhttp3.Response;

public class FocusActivity extends AppCompatActivity implements SensorEventListener {

    private TextView tvFocusTime;
    private EditText etFocusDescription;
    private Button btnFinish;
    private TextView tvGrowSpeed;

    // start time of this focus task
    private Date startTime;
    // end time of this focus task
    private Date endTime;

    // seconds that last since the activity starts
    private int seconds;

    // mark the focus task is finished or not. it's false by default
    private boolean isFinished;

    // sensor manager
    private SensorManager sensorManager;
    // light sensor
    private Sensor lightSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);

        //todo: put description from menu page to etFocusDescription
        // but cannot settext to and putExtras from etFocusDescription at same time in onCreate 4/28
        Intent intent = getIntent();
        String descriptionFromMenu = intent.getStringExtra(MenuActivity.EXTRA_DESCRIPTION);


        tvFocusTime = findViewById(R.id.tv_focus_time);
        etFocusDescription = findViewById(R.id.et_focus_description);
        btnFinish = findViewById(R.id.btn_finish_focus);

        tvGrowSpeed = findViewById(R.id.tv_grow_speed);

        // click listener of finish button
        btnFinish.setOnClickListener(view -> {
            // mark focus task finished
            isFinished = true;
            // init end time
            endTime = new Date();
            // upload focus record to server
            uploadRecordToServer();
            // redirect to result page
            redirectToResultPage();
        });

        // start counting time
        startCounting();

        // init light sensor
        initLightSensor();
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
                String time = TimeUtil.convertSecondsToTime(seconds);
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

    /**
     * upload focus record to server
     */
    private void uploadRecordToServer() {
        // request body
        Map<String, Object> requestBody = new HashMap<>();
        // TODO: user id
        requestBody.put("userId", 9);
        // TODO: token
        requestBody.put("token", "0788ad5e-c5b0-4a43-a744-547353d763b4");
        // description
        requestBody.put("description", etFocusDescription.getText().toString());
        // startTime
        requestBody.put("startTime", startTime.getTime());
        // endTime
        requestBody.put("endTime", endTime.getTime());
        // build request
        Request request = ApiUtil.buildRequest(ApiUtil.ADD_FOCUS_RECORD_API, null, requestBody);

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

        }).start();
    }

    private void redirectToResultPage() {
        // create intent
        Intent intent = new Intent(this, FocusResultActivity.class);
        // put info
        // startTime
        intent.putExtra("startTime", startTime);
        // endTime
        intent.putExtra("endTime", endTime);
        // duration
        intent.putExtra("duration", seconds);
        // description
        intent.putExtra("description", etFocusDescription.getText().toString());
        startActivity(intent);
        // finish this activity
        finish();
    }

    /**
     * init light sensor
     */
    private void initLightSensor() {
        // get light sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        // register sensor event
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.values[0] < 2) {
            tvGrowSpeed.setText("Fast");
            tvGrowSpeed.setTextColor(ContextCompat.getColor(this, R.color.green_light));
        } else if (sensorEvent.values[0] > 10) {
            tvGrowSpeed.setText("Slow");
            tvGrowSpeed.setTextColor(ContextCompat.getColor(this, R.color.red));
        } else {
            tvGrowSpeed.setText("Normal");
            tvGrowSpeed.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
//        Toast.makeText(FocusActivity.this, "accuracy changed!", Toast.LENGTH_SHORT).show();
    }
}