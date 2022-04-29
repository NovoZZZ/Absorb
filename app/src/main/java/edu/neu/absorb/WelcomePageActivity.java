package edu.neu.absorb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import edu.neu.absorb.utils.FileUtil;
import edu.neu.absorb.utils.MyApplication;

public class WelcomePageActivity extends AppCompatActivity {
    private Context context= MyApplication.getAppContext();
    Button startbutton;
    ImageView firstimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        firstimage=findViewById(R.id.iv_welcomepage);

        int imageResource= getResources().getIdentifier("@drawable/seedling",null,this.getPackageName());
        firstimage.setImageResource(imageResource);

        startbutton=findViewById(R.id.btn_start);
        startbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("abc", String.valueOf(FileUtil.readJson(context,"token").toString().length()));
                if(FileUtil.readJson(context,"token").toString().length()>10){
                Intent intent = new Intent(WelcomePageActivity.this, MenuActivity.class);
                startActivity(intent);}

                else{
                    Intent intent = new Intent(WelcomePageActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}