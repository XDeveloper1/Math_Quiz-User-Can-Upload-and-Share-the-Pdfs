package com.xdev.math_quiz.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xdev.math_quiz.R;
import com.xdev.math_quiz.account.Account_Frag;
import com.xdev.math_quiz.dashboard.Dashboard_frag;

public class Home_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home_frag()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {

        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            selectedFragment = new Home_frag();
        } else if (itemId == R.id.dashboard ) {
            selectedFragment = new Dashboard_frag();
        } else if (itemId == R.id.account) {
            selectedFragment = new Account_Frag();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_page_container, selectedFragment).commit();
        }
        return true;
    };
}
