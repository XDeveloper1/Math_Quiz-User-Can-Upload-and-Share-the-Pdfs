package com.xdev.math_quiz.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xdev.math_quiz.R;
import com.xdev.math_quiz.dashboard.Dashboard_frag;
import com.xdev.math_quiz.dashboard.Question_Activity;

public class Mathmatics_frag extends Fragment {

    TextView b1, b2, b3, b4;
    BottomNavigationView bottomNav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mathmatics_frag, container, false);

        b1 = view.findViewById(R.id.plus_button);
        b2 = view.findViewById(R.id.subtract_button);
        b3 = view.findViewById(R.id.multiply_button);
        b4 = view.findViewById(R.id.divide_button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment Dashboard = new Dashboard_frag();
                FragmentTransaction fn = getActivity().getSupportFragmentManager().beginTransaction();
                fn.replace(R.id.home_page_container,Dashboard).commit();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment Dashboard = new Dashboard_frag();
                FragmentTransaction fn = getActivity().getSupportFragmentManager().beginTransaction();
                fn.replace(R.id.home_page_container,Dashboard).commit();;
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment Dashboard = new Dashboard_frag();
                FragmentTransaction fn = getActivity().getSupportFragmentManager().beginTransaction();
                fn.replace(R.id.home_page_container,Dashboard).commit();

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment Dashboard = new Dashboard_frag();
                FragmentTransaction fn = getActivity().getSupportFragmentManager().beginTransaction();
                fn.replace(R.id.home_page_container,Dashboard).commit();
            }
        });
        return view;
    }

}