package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class HistoryAndProfileActivity extends AppCompatActivity {

    // History/ profile page avator
    private ImageView userAvator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_and_profile);

        userAvator = findViewById(R.id.profile_avator);
        userAvator.setImageResource(R.drawable.comic_trees);
    }
}