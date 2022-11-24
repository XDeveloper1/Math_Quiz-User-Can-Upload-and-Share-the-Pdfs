package com.xdev.math_quiz;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class MainActivity extends AppCompatActivity {
    SliderView sliderView;
    int[] images = {
            R.drawable.img1,
            R.drawable.im2,
            R.drawable.im3,
            R.drawable.im4,
            R.drawable.im5,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sliderView = findViewById(R.id.imageSlider);
        slider_Adapter slider_adapter = new slider_Adapter(images);
        sliderView.setSliderAdapter(slider_adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.COLOR);
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEOUTROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(1);
        sliderView.setAutoCycle(true);
    }
}