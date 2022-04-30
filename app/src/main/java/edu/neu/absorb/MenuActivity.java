package edu.neu.absorb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import edu.neu.absorb.utils.FileUtil;
import edu.neu.absorb.utils.MyApplication;

public class MenuActivity extends AppCompatActivity {

    public static final String EXTRA_DESCRIPTION = "edu.neu.absorb.EXTRA_DESCRIPTION";

    //actionbar
    private ActionBar actionBar;

    //UI Views
    private ViewPager viewPager;

    // leaderboard page
    private Button btn_leaderBoard;

    // profile page
    private Button btn_profile;

    // focus page
    private Button btn_startFocus;

    private ArrayList<MenuModel> modelArrayList;
    private MenuAdapter myAdapter;

    //set userid to textview
    private TextView tv_nickname;

    private EditText etFocusTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //init actionbar
        actionBar = getSupportActionBar();

        //init UI views
        viewPager = findViewById(R.id.viewPager);
        loadCards();

        etFocusTask = findViewById(R.id.ed_description);

        //set nickname to et
        TextView tv_nickname = findViewById(R.id.tv_nickname);
        setNickName();

        // init leaderboard page
        btn_leaderBoard = findViewById(R.id.btn_leaderboard);
        btn_leaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLeaderBoardPage();
            }
        });

        // open focus page
        btn_startFocus = findViewById(R.id.btn_startFocus);
        btn_startFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFocusPage();
            }
        });

        btn_profile = findViewById(R.id.btn_profile);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfile();
            }
        });
        //set view pager change listener
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //just change title of actionbar
                String title = modelArrayList.get(position).getTitle();
                actionBar.setTitle(title);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void openFocusPage() {
        String focusDescription = etFocusTask.getText().toString();

        Intent intent = new Intent(this, FocusActivity.class);
        intent.putExtra(EXTRA_DESCRIPTION, focusDescription);
        startActivity(intent);
    }

    public void openProfile() {
        startActivity(new Intent(this, HistoryActivity.class));
    }
    private void setNickName() {

        //Todo: how to get username? 4/28
        //get username
        //String userName =
        //set username to tv
        //tv_nickname.setText(userName);

    }

    public void openLeaderBoardPage() {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    private void loadCards() {
        //init list
        modelArrayList = new ArrayList<>();

        //add items to list
        modelArrayList.add(new MenuModel(
                "Cordyline",
                "Description 02",
                "04/21/2022",
                R.drawable.cordyline));
        modelArrayList.add(new MenuModel(
                "Comic Tree",
                "Description 02",
                "04/21/2022",
                R.drawable.comic_trees));
        modelArrayList.add(new MenuModel(
                "Flowers",
                "Description 02",
                "04/21/2022",
                R.drawable.flowers));

        //setup adapter
        myAdapter = new MenuAdapter(this, modelArrayList);
        //set adapter to viewpager
        viewPager.setAdapter(myAdapter);
        //set default padding from left/right
        viewPager.setPadding(100, 0, 100, 0);


    }
}