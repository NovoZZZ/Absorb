package edu.neu.absorb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class MenuAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<MenuModel> modelArrayList;

    //constructor
    public MenuAdapter(Context context, ArrayList<MenuModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        //inflate layout card_item.xml
        View view = LayoutInflater.from(context).inflate(R.layout.menu_card_item,container,false);

        //init uid views from card_item.xml
        ImageView bannerIv = view.findViewById(R.id.bannerIv);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView descriptionTv = view.findViewById(R.id.descriptionTv);
        //TextView dateTv = view.findViewById(R.id.dateTv);

        //get data
        MenuModel model = modelArrayList.get(position);
        String title = model.getTitle();
        String description = model.getDescription();
        //String date = model.getDate();
        int image = model.getImage();

        //set data
        bannerIv.setImageResource(image);
        titleTv.setText(title);
        descriptionTv.setText(description);
        //dateTv.setText(date);

        //handle card click
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, title + "\n" + description + "\n", Toast.LENGTH_SHORT).show();
            }
        });

        //add view to container
        container.addView(view,position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
