package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.neu.absorb.utils.TimeUtil;

public class FocusResultActivity extends AppCompatActivity {

    private TextView tvResultTime;
    private TextView tvResultDuration;
    private TextView tvResultDate;
    private TextView tvResultDescription;
    private Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_result);

        tvResultTime = findViewById(R.id.tv_focus_result_time);
        tvResultDuration = findViewById(R.id.tv_focus_result_duration);
        tvResultDate = findViewById(R.id.tv_focus_result_date);
        tvResultDescription = findViewById(R.id.tv_focus_result_description);

        btnComplete = findViewById(R.id.btn_focus_result_complete);
        btnComplete.setOnClickListener(view -> {
            // finish this activity
            finish();
        });

        // show result time and duration
        initResultInfo();
    }

    /**
     * show result time and duration
     */
    private void initResultInfo() {
        // get data from last activity
        Bundle extras = getIntent().getExtras();
        // start time
        Date startTime = (Date) extras.get("startTime");
        // end time
        Date endTime = (Date) extras.get("endTime");
        // duration
        int duration = (int) extras.get("duration");
        // description
        String description = (String) extras.get("description");

        // update ui
        // date format
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String dateResult = dateFormat.format(startTime);
        tvResultDate.setText(dateResult);

        // time format
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        String resultTime = timeFormat.format(startTime) + " -- " + timeFormat.format(endTime);
        // update result time
        tvResultTime.setText(resultTime);

        // duration
        String durationResult = TimeUtil.convertSecondsToResultFormat(duration);
        tvResultDuration.setText(durationResult);

        // description
        if (description.trim().equals("")) {
            description = "Regular focus task";
        }
        tvResultDescription.setText(description);
    }
}