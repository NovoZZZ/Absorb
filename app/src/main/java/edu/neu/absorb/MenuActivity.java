package edu.neu.absorb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import edu.neu.absorb.utils.ApiUtil;
import edu.neu.absorb.utils.FileUtil;
import edu.neu.absorb.utils.MyApplication;
import okhttp3.Request;
import okhttp3.Response;

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

    //set nickname, score to textview
    TextView tv_nickname;
    TextView tv_menu_score;

    private EditText etFocusTask;

    public String menu_score;
    public String tasks;

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

        //set nickname,score to tv
        tv_nickname = findViewById(R.id.tv_nickname);
        tv_menu_score = findViewById(R.id.tv_menu_score);
        setNickName();//set nickname and score


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

        //get id and token from local cache
        Context context= MyApplication.getAppContext();
        List<String> resArr = FileUtil.readJson(context, "token");
        String strRes = resArr.size() != 0 ? resArr.get(0) : "";

        // Deserialize json
        Gson gson = new Gson();
        LoginInfo loginInfo = gson.fromJson(strRes, LoginInfo.class);

        Integer userId = loginInfo.getUserId(); //== null ? 1:loginInfo.getUserId();
        String token = loginInfo.getToken(); //== null ? "c8af4c6c-a512-418f-ad93-ae17e3ac668d" : loginInfo.getToken();

        //use api call to get user info
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", String.valueOf(userId));
        pathVariables.put("token", token);
        // build request
        Request request = ApiUtil.buildRequest(ApiUtil.USER_INFO_API, pathVariables, null);

        // call api
        new Thread(() -> {
            //String responseBodyStr = "no user";
            JSONObject jsonResponse = null;
            try (Response response = ApiUtil.client.newCall(request).execute()) {
                //responseBodyStr = response.body().string();

                //JSONObject jsonObject = JSONUtil.parseObj(responseBodyStr);
                jsonResponse = JSONUtil.parseObj(response.body().string());
                Log.d("Test activity", jsonResponse.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject data = jsonResponse.getJSONObject("data");
            String nickname = (String) data.get("nickname") == null ? "nullname": (String)data.get("nickname");
            int score = (Integer) data.get("score") ==null ? 0: (Integer) data.get("score");
            int focuscounts= (Integer) data.get("score") ==null ? 0: (Integer) data.get("focusCount");
            menu_score = String.valueOf(score);
            tasks = String.valueOf(focuscounts);


            Log.d("Test activity",nickname);
            Log.d("Test activity",String.valueOf(score));

            //runOnUiThread(() -> {
              //  this.tv_nickname.setText(nickname);
            //});
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_nickname.setText(nickname);
                    tv_menu_score.setText("Score: " + score);
                }
            });

        }).start();

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
                "Tutorial",
                "Swipe to find out...",
                R.drawable.cordyline));
        modelArrayList.add(new MenuModel(
                "Plant a Tree",
                "Whenever you want to absorb, plant trees.",
                R.drawable.plantatree_menu));
        modelArrayList.add(new MenuModel(
                "Be Absorbed",
                "The tree will grow while you work.",
                R.drawable.beabsorbed_menu));

        modelArrayList.add(new MenuModel(
                "Place your phone upside down",
                "Leave your phone upside down, tree will grow faster.",
                R.drawable.upsidedown_menu));
        modelArrayList.add(new MenuModel(
                "Unlock more Styles",
                "Unlock more trees as focus score grows.",
                R.drawable.alltrees_menu));
        modelArrayList.add(new MenuModel(
                "Now let's get started",
                "You have completed " + tasks + " tasks, and have scored " + menu_score+"."+ "Press Start Button to Absorb...",
                R.drawable.comic_trees));

        //setup adapter
        myAdapter = new MenuAdapter(this, modelArrayList);
        //set adapter to viewpager
        viewPager.setAdapter(myAdapter);
        //set default padding from left/right
        viewPager.setPadding(100, 0, 100, 0);


    }
}