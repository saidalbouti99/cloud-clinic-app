package com.example.cloudclinic_said;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    int dots[] = {R.id.dot1, R.id.dot2, R.id.dot3};
    List<ImageView> imgdots;
    ImageButton backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        viewPager2 = findViewById(R.id.vp);

        int [] idArr = {0,1,2};

        viewPager2.setAdapter(new ViewPagerAdapter(this, idArr , viewPager2));
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setUserInputEnabled(false);

        imgdots = new ArrayList<>();

        for(int i=0; i<dots.length; i++){
            ImageView img = findViewById(dots[i]);
            imgdots.add(img);
        }

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                switch(position){
                    case 0:
                        imgdots.get(0).setImageDrawable(getDrawable(R.drawable.ic_baseline_lens_24));
                        imgdots.get(1).setImageDrawable(getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24));
                        imgdots.get(2).setImageDrawable(getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24));
                        break;
                    case 1:
                        imgdots.get(1).setImageDrawable(getDrawable(R.drawable.ic_baseline_lens_24));
                        imgdots.get(0).setImageDrawable(getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24));
                        imgdots.get(2).setImageDrawable(getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24));
                        break;
                    case 2:
                        imgdots.get(2).setImageDrawable(getDrawable(R.drawable.ic_baseline_lens_24));
                        imgdots.get(1).setImageDrawable(getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24));
                        imgdots.get(0).setImageDrawable(getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24));
                        break;
                    default:
                        break;
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2){
            Intent j = new Intent(this, PaymentSuccessActivity.class);
            startActivity(j);
        }
    }
}

