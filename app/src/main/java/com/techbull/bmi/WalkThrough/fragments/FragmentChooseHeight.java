package com.techbull.bmi.WalkThrough.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.pixplicity.easyprefs.library.Prefs;
import com.shawnlin.numberpicker.NumberPicker;
import com.techbull.bmi.Helper.Keys;
import com.techbull.bmi.MainActivity;
import com.techbull.bmi.R;
import com.techbull.bmi.Splash;

public class FragmentChooseHeight extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private NumberPicker numberPicker, number_picker2;
    private Button nextBtn;
    private CardView cardCm, cardFt;
    private TextView heightInCm, heightFt, heightIn, unitCm, unitFt, unitIn, textCm, textFt;
    private int h, hIn, defaultHeight = 180;
    private LinearLayout displayHeight, unitFtIn;
    private View dot;
    private String heightUnit = "cm";

    public FragmentChooseHeight() {
        // Required empty public constructor
    }

    public static FragmentChooseHeight newInstance(String param1, String param2) {
        FragmentChooseHeight fragment = new FragmentChooseHeight();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_height, container, false);

        nextBtn = view.findViewById(R.id.nextBtn);
        dot = view.findViewById(R.id.dot);
        numberPicker = view.findViewById(R.id.number_picker);
        number_picker2 = view.findViewById(R.id.number_picker2);
        cardCm = view.findViewById(R.id.cardCm);
        cardFt = view.findViewById(R.id.cardFt);
        unitCm = view.findViewById(R.id.unitCm);
        heightInCm = view.findViewById(R.id.heightInCm);
        heightFt = view.findViewById(R.id.heightFt);
        heightIn = view.findViewById(R.id.heightIn);
        textCm = view.findViewById(R.id.textCm);
        textFt = view.findViewById(R.id.textFt);
        displayHeight = view.findViewById(R.id.displayHeight);
        unitFtIn = view.findViewById(R.id.unitFtIn);

        numberPicker.setValue(defaultHeight);
        number_picker2.setValue(0);
        heightInCm.setText(String.valueOf(defaultHeight));
        heightIn.setText(String.valueOf(0));

        enableNextBtn();
        selectUnit();
        selectHeight1();
        selectHeight2();
        updatePosition();

        return view;
    }


    void selectUnit() {
        cardCm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heightUnit = "cm";
                numberPicker.setMinValue(40);
                numberPicker.setMaxValue(250);

                defaultHeight = (int) (defaultHeight * 30.48);
                numberPicker.setValue(defaultHeight);
                heightInCm.setText(String.valueOf(defaultHeight));

                unitFtIn.setVisibility(View.GONE);
                unitCm.setVisibility(View.VISIBLE);
                heightInCm.setVisibility(View.VISIBLE);
                dot.setVisibility(View.GONE);
                number_picker2.setVisibility(View.GONE);

                cardCm.setCardBackgroundColor(getResources().getColor(R.color.cardSelectedColor));
                cardFt.setCardBackgroundColor(getResources().getColor(R.color.walk_through_cardcolor));
                unitCm.setText("cm");
            }
        });
        cardFt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heightUnit = "inch";
                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(8);

                defaultHeight = (int) (defaultHeight / 30.48);
                numberPicker.setValue(defaultHeight);
                heightFt.setText(String.valueOf(defaultHeight));

                unitFtIn.setVisibility(View.VISIBLE);
                unitCm.setVisibility(View.GONE);
                heightInCm.setVisibility(View.GONE);
                heightIn.setVisibility(View.VISIBLE);
                heightIn.setText("0");
                dot.setVisibility(View.VISIBLE);
                number_picker2.setVisibility(View.VISIBLE);
                numberPicker.setVisibility(View.VISIBLE);
                cardFt.setCardBackgroundColor(getResources().getColor(R.color.cardSelectedColor));
                cardCm.setCardBackgroundColor(getResources().getColor(R.color.walk_through_cardcolor));
            }
        });
    }

    private int selectHeight1() {
        h = numberPicker.getValue();
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                defaultHeight = newVal;
                h = newVal;
                heightInCm.setText(String.valueOf(newVal));
                heightFt.setText(String.valueOf(newVal));
            }
        });
        return h;
    }

    private int selectHeight2() {
        hIn = number_picker2.getValue();
        number_picker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                defaultHeight = newVal;
                hIn = newVal;
                heightIn.setText(String.valueOf(hIn));
            }
        });
        return hIn;
    }

    private void enableNextBtn() {
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
    }

    private void disableNextBtn() {
        nextBtn.setEnabled(false);
        nextBtn.setAlpha(.5f);
    }

    private void setData() {
        Prefs.putString(Keys.KEY_HEIGHT_UNIT, heightUnit);
        int h1 = selectHeight1();
        if (heightUnit.equals("cm")) {
            Prefs.putDouble(Keys.KEY_HEIGHT, h1);
        } else {
            float height = Float.parseFloat(h1 + "." + selectHeight2());
            Prefs.putDouble(Keys.KEY_HEIGHT, height * 12);
        }
    }

    void updatePosition() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.putBoolean(Keys.KEY_IS_FIRST_RUN, false);
                setData();
                Intent intent = new Intent(getContext(), Splash.class);
                getActivity().finish();
                getActivity().startActivity(intent);
            }
        });
    }
}