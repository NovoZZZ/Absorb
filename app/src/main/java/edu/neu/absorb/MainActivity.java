package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }
    public void openMenuActivity() {
        Intent intent =  new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}