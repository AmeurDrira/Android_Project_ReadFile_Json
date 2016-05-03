package com.example.ameur.lirejson.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ameur.lirejson.R;
import com.example.ameur.lirejson.core.Quiz;
import com.example.ameur.lirejson.fragement.QuestionFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mButtonFile;
    private CoordinatorLayout coordinatorLayout;
    private Quiz mQuiz;
    ArrayList<Quiz> mquizs = new ArrayList<Quiz>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonFile = (Button) findViewById(R.id.mButtonFile);
        mButtonFile.setOnClickListener(this);

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
                break;


        }
    }

    private void toFragementQuestion() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_layout, QuestionFragment.newInstance(mquizs.get(0)))
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
                mQuiz = new Quiz();
                JSONObject jsonObj = m_jArry.getJSONObject(i);

                mQuiz.setQuestion(jsonObj.getString("question"));
                mQuiz.setReponseUn(jsonObj.getString("reponseUn"));
                mQuiz.setReponseDeux(jsonObj.getString("reponseDeux"));
                mQuiz.setReponseTrois(jsonObj.getString("reponseTrois"));
                mQuiz.setReponseCorrect(jsonObj.getString("reponseCorrect"));

                mquizs.add(mQuiz);


            }
            Log.v("Taille", mquizs.size() + "");
            Log.v("Valeur0", mquizs.get(0).getQuestion() + "");
            Log.v("Valeur1", mquizs.get(1).getQuestion() + "");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}

