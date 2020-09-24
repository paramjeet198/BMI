package com.techbull.bmi.WalkThrough.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.pixplicity.easyprefs.library.Prefs;
import com.shawnlin.numberpicker.NumberPicker;
import com.techbull.bmi.Helper.Keys;
import com.techbull.bmi.R;
import com.techbull.bmi.WalkThrough.ModelData;
import com.techbull.bmi.WalkThrough.WalkThrough;

public class FragmentSelectAge extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private NumberPicker numberPicker;
    private int age = 21;
    private Button nextBtn;
    private TextView year;
    private String mParam1;
    private String mParam2;
    private ModelData data;
    private LinearLayout ageHolder;
    //    private SharedPreferences preferences;
//    private PreferenceHelper preferenceHelper;

    public FragmentSelectAge() {
        // Required empty public constructor
    }

    public static FragmentSelectAge newInstance(String param1, String param2) {
        FragmentSelectAge fragment = new FragmentSelectAge();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_age, container, false);

        data = new ModelData();
        ageHolder = view.findViewById(R.id.ageHolder);
        nextBtn = view.findViewById(R.id.nextBtn);
        numberPicker = view.findViewById(R.id.number_picker);
        year = view.findViewById(R.id.year);

        numberPicker.setValue(age);
        year.setText(String.valueOf(age));

        enableNextBtn();
        selectAge();
        updatePosition();
        return view;
    }

    void updatePosition() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.putInt(Keys.KEY_AGE, age);
//                preferenceHelper.saveToPref("age", age);
                ((WalkThrough) getActivity()).updatePosition(3);
            }
        });
    }

    private int selectAge() {
        age = numberPicker.getValue();
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                ageHolder.setVisibility(View.VISIBLE);
                age = newVal;
                year.setText(String.valueOf(age));
            }
        });
        return age;
    }

    private void enableNextBtn() {
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
    }

    private void disableNextBtn() {
        nextBtn.setEnabled(false);
        nextBtn.setAlpha(.5f);
    }
}