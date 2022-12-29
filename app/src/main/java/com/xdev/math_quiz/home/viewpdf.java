package com.xdev.math_quiz.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.xdev.math_quiz.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class viewpdf extends AppCompatActivity {
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpdf);
        pdfView = findViewById(R.id.pdfView);


        String filename = getIntent().getStringExtra("filename");
        String fileurl = getIntent().getStringExtra("url");
        Log.d(filename, "onCreate: ");
        Log.d(fileurl, "onCreate: ");
        new Retrivepdfstream().execute(fileurl);


    }

    class Retrivepdfstream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());


                }

            } catch (IOException e) {
                return null;

            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {

            pdfView.fromStream(inputStream).load();

        }

    }


}