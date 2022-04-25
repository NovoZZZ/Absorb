package edu.neu.absorb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    //actionbar
    private ActionBar actionBar;

    //UI Views
    private ViewPager viewPager;

    // History/ profile page
    private Button historyPage;

    private ArrayList<MenuModel> modelArrayList;
    private MenuAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //init actionbar
        actionBar = getSupportActionBar();

        //init UI views
        viewPager = findViewById(R.id.viewPager);
        loadCards();

        // init history page
        historyPage = findViewById(R.id.btn_history);
        historyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistoryPage();
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

    public void openHistoryPage() {
        Intent intent = new Intent(this, HistoryAndProfileActivity.class);
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
        viewPager.setPadding(100,0,100,0);


    }
}