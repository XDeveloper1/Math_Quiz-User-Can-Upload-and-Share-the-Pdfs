package com.xdev.math_quiz.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.xdev.math_quiz.R;
import com.xdev.math_quiz.login.MainActivity;

public class Home_frag extends Fragment {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView name, email;
    Button class1, signout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home_frag, container, false);

        class1 =view.findViewById(R.id.class1);
//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc = GoogleSignIn.getClient(getActivity(), gso);
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
//        if (account != null) {
//            String personname = account.getDisplayName();
//            String personemail = account.getEmail();
//            name.setText(personname);
//            email.setText(personemail);
        class1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment Mathmatics = new Mathmatics_frag();
                FragmentTransaction fn = getActivity().getSupportFragmentManager().beginTransaction();
                fn.replace(R.id.home_page_container,Mathmatics).commit();

            }
        });



        return view;
    }
}

//    void signoutmethod() {
//        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                try {
//                    closefrag();
//                    Intent i = new Intent(getActivity(), MainActivity.class);
//                    startActivity(i);
//                } catch (Throwable e) {
//                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
//
//    private void closefrag() {
//        getActivity().getFragmentManager().popBackStack();
//    }
//
//
//}