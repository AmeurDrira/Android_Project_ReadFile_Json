package com.example.ameur.lirejson.fragement;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.ameur.lirejson.R;
import com.example.ameur.lirejson.core.Quiz;


public class QuestionFragment extends Fragment implements View.OnClickListener {
    private static Quiz mquiz;
    private RadioGroup radioGroup;
    private CoordinatorLayout coordinatorLayout;
    private RadioButton radioButton, radioButton2, radioButton3, radioButton4, radioGenderButton = null;
    private Button mButtonR;

    public QuestionFragment() {

    }

    public static QuestionFragment newInstance(Quiz quiz) {
        QuestionFragment questionFragment = new QuestionFragment();
        mquiz = quiz;
        return questionFragment;
    }

    private TextView mQuestion;
    Snackbar snackbar;
    int i = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        mQuestion = (TextView) view.findViewById(R.id.mQuestion);
        mQuestion.setText(mquiz.getQuestion());

        radioButton = (RadioButton) view.findViewById(R.id.radioButton);
        radioButton.setText(mquiz.getReponseUn());

        radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);
        radioButton2.setText(mquiz.getReponseDeux());

        radioButton3 = (RadioButton) view.findViewById(R.id.radioButton3);
        radioButton3.setText(mquiz.getReponseTrois());

        radioButton4 = (RadioButton) view.findViewById(R.id.radioButton4);
        radioButton4.setText(mquiz.getReponseCorrect());

        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id
                .coordinatorLayout);
        mButtonR = (Button) view.findViewById(R.id.button);
        mButtonR.setOnClickListener(this);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);

        return view;
    }


    @Override
    public void onClick(View v) {
        int selectRadio = 0;
        String reponse = "";

        switch (v.getId()) {
            case R.id.button:
                selectRadio = radioGroup.getCheckedRadioButtonId();
                radioGenderButton = (RadioButton) getView().findViewById(selectRadio);

                if (null == radioGenderButton) {

                    snackbar = Snackbar
                            .make(coordinatorLayout, "Aucun proposition est selectionner", Snackbar.LENGTH_LONG);

                    snackbar.show();
                } else {
                    reponse = (String) radioGenderButton.getText().toString();
                    if (mquiz.getReponseCorrect().equals(reponse)) {
                        snackbar = Snackbar
                                .make(coordinatorLayout, "Bravo", Snackbar.LENGTH_LONG);

                        snackbar.show();
                        radioGroup.clearCheck();


                    } else {
                        snackbar = Snackbar
                                .make(coordinatorLayout, "Reponse fausse", Snackbar.LENGTH_LONG);

                        snackbar.show();
                        radioGroup.clearCheck();
                    }

                }
        }
    }
}
