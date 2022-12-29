package com.xdev.math_quiz.home;

import static com.xdev.math_quiz.R.color.appcolortheme;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xdev.math_quiz.R;
import com.xdev.math_quiz.account.Account_Frag;

public class Home_page extends AppCompatActivity {
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(appcolortheme)));
        getSupportActionBar().setTitle("Quiz");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setItemIconTintList(null);
        String stringExtra = getIntent().getStringExtra("number");
        Bundle bundle2 = new Bundle();
        bundle2.putString("number", stringExtra);
        Home_frag home_frag = new Home_frag();
        home_frag.setArguments(bundle2);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, home_frag).commit();

    }


    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {

        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            selectedFragment = new Home_frag();
        } else if (itemId == R.id.account) {
            selectedFragment = new Account_Frag();
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_page_container, selectedFragment).commit();
        }
        return true;
    };
}
