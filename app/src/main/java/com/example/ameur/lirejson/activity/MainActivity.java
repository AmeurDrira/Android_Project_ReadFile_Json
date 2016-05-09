package com.example.ameur.lirejson.activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ameur.lirejson.R;
import com.example.ameur.lirejson.core.Quiz;
import com.example.ameur.lirejson.fragement.ConfigFragment;
import com.example.ameur.lirejson.fragement.LastFragment;
import com.example.ameur.lirejson.fragement.QuestionFragment;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mResume;
    private Button mBfinish;
    private Button mConfig;
    private ImageView mImage;


    private Button mButtonFile;
    private FloatingActionButton mFab;
    private CoordinatorLayout coordinatorLayout;
    ArrayList<Quiz> mquizs = new ArrayList<Quiz>();
    private int numeroQuestion = 0;
    public int score = 70;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());


        mResume = (Button) findViewById(R.id.mResume);
        mResume.setOnClickListener(this);
        mConfig = (Button) findViewById(R.id.Config);
        mConfig.setOnClickListener(this);
        mButtonFile = (Button) findViewById(R.id.mButtonFile);
        mButtonFile.setOnClickListener(this);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(this);
        mFab.setVisibility(View.INVISIBLE);
        mBfinish = (Button) findViewById(R.id.mBfinish);
        mBfinish.setOnClickListener(this);
        mImage = (ImageView) findViewById(R.id.mImage);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mButtonFile:
                readAssetFile();
                toFragementQuestion();
                mButtonFile.setVisibility(View.INVISIBLE);
                mFab.setVisibility(View.VISIBLE);
                mResume.setVisibility(View.INVISIBLE);
                mBfinish.setVisibility(View.INVISIBLE);
                mImage.setVisibility(View.INVISIBLE);
                mConfig.setVisibility(View.INVISIBLE);
                break;

            case R.id.fab:
                if (numeroQuestion >= mquizs.size()) {
                    toLastFragement();
                    mBfinish.setVisibility(View.VISIBLE);
                    mFab.setVisibility(View.INVISIBLE);
                } else {
                    toFragementQuestion();
                    numeroQuestion++;
                    mButtonFile.setVisibility(View.INVISIBLE);

                }
                break;
            case R.id.mBfinish:
                finish();
                break;
            case R.id.Config:
                launchConfig();
                break;


        }
    }

    private void launchConfig() {
        ConfigFragment configFragment = ConfigFragment.newInstance();
        configFragment.setCancelable(false);
        configFragment.show(getSupportFragmentManager(), "");
    }

    private void toLastFragement() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_layout, LastFragment.newInstance(score))
                .commit();
    }

    private void toFragementQuestion() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_layout, QuestionFragment.newInstance(mquizs.get(numeroQuestion)))
                .commit();
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("quiz");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void readAssetFile() {

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("questions");


            for (int i = 0; i < m_jArry.length(); i++) {
                Quiz mQuiz = new Quiz();
                JSONObject jsonObj = m_jArry.getJSONObject(i);

                mQuiz.setQuestion(jsonObj.getString("question"));
                mQuiz.setReponseUn(jsonObj.getString("reponseUn"));
                mQuiz.setReponseDeux(jsonObj.getString("reponseDeux"));
                mQuiz.setReponseTrois(jsonObj.getString("reponseTrois"));
                mQuiz.setReponseQuatre(jsonObj.getString("reponseQuatre"));
                mQuiz.setReponseCorrect(jsonObj.getString("reponseCorrect"));

                mquizs.add(mQuiz);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void changeLanguageSettings(String lang) {


        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());

        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}

