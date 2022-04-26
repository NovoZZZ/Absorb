package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // FIXME: 4/24/22 Test purpose only
    Button leaderboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        leaderboard = findViewById(R.id.btn_leaderboard);
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLeaderboard();
            }
        });

        Button btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenuActivity();
            }
        });

        // test -- delete in the future
        findViewById(R.id.btn_test).setOnClickListener(view -> {
            startActivity(new Intent(this, TestActivity.class));
        });

        findViewById(R.id.btn_test_focus).setOnClickListener(view -> {
            startActivity(new Intent(this, FocusActivity.class));
        });

    }
    public void openMenuActivity() {
        Intent intent =  new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    // FIXME: 4/24/22 Test purpose only 
    public void openLeaderboard() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);

    }
}