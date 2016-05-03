package com.example.ameur.lirejson.fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ameur.lirejson.R;


public class LastFragment extends Fragment {

    private TextView mfinish;
    private static int mScore;


    public LastFragment() {

    }

    public static LastFragment newInstance(int score) {
        LastFragment lastFragment = new LastFragment();
        mScore = score;
        return lastFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_last, container, false);
        mfinish = (TextView) view.findViewById(R.id.mfinish);
        mfinish.setVisibility(View.VISIBLE);
        mfinish.setText("Félicitation fin de  quizz");
        return view;
    }

}