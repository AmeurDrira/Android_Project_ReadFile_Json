package com.example.ameur.lirejson.fragement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ameur.lirejson.R;
import com.example.ameur.lirejson.activity.MainActivity;

import java.util.Locale;


public class ConfigFragment extends DialogFragment {
    private RadioButton radiolangugeFR, radiolangugeEN, radioGenderButton = null;
    private RadioGroup radioGroup;
    private String reponse;

    private int selectRadio = 0;


    public static ConfigFragment newInstance() {
        ConfigFragment fragment = new ConfigFragment();

        return fragment;
    }

    public ConfigFragment() {
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {


        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = layoutInflater.inflate(R.layout.fragment_config, null);
        final MainActivity activity = (MainActivity) getActivity();

        radioGroup = (RadioGroup) dialogView.findViewById(R.id.radioGroup);
        String languesDef = Locale.getDefault().getLanguage();
       if(languesDef.equals("en"))
           radioGroup.check(R.id.radiolangugeEN);
        if(languesDef.equals("fr"))
            radioGroup.check(R.id.radiolangugeFr);



        radiolangugeFR = (RadioButton) dialogView.findViewById(R.id.radiolangugeFr);
        radiolangugeFR.setText("Français");

        radiolangugeEN = (RadioButton) dialogView.findViewById(R.id.radiolangugeEN);
        radiolangugeEN.setText("English");


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialog, int which) {

                        selectRadio = radioGroup.getCheckedRadioButtonId();
                        Log.v("selecRadio", String.valueOf(selectRadio));
                        radioGenderButton = (RadioButton) dialogView.findViewById(selectRadio);
                        reponse = radioGenderButton.getText().toString();
                        if (!reponse.isEmpty() && reponse.equals("English")) {
                            activity.changeLanguageSettings("en");
                        } else if (!reponse.isEmpty() && reponse.equals("Français")) {
                            activity.changeLanguageSettings("fr");
                        }

                    }
                }
        ).setNegativeButton(android.R.string.cancel, null).setTitle(R.string.titleconfig).setView(dialogView);

        return builder.create();

    }


}
