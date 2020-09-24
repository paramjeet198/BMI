package com.techbull.bmi.WalkThrough.fragments;

import android.annotation.SuppressLint;
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
import com.techbull.bmi.R;
import com.techbull.bmi.WalkThrough.WalkThrough;

public class FragmentChooseWeight extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private NumberPicker numberPicker, number_picker2;
    private int w1, w2, defaultWeight = 80;
    private Button nextBtn;
    private TextView weight1, weight2, unit, textKg, textLbs;
    private CardView cardKg, cardLbs;
    private LinearLayout displayWeight;
    private String weightUnit = "kg";

    public FragmentChooseWeight() {
        // Required empty public constructor
    }

    public static FragmentChooseWeight newInstance(String param1, String param2) {
        FragmentChooseWeight fragment = new FragmentChooseWeight();
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
        View view = inflater.inflate(R.layout.fragment_weight, container, false);

        nextBtn = view.findViewById(R.id.nextBtn);
        numberPicker = view.findViewById(R.id.number_picker);
        number_picker2 = view.findViewById(R.id.number_picker2);
        weight1 = view.findViewById(R.id.w1);
        weight2 = view.findViewById(R.id.w2);
        textKg = view.findViewById(R.id.textKg);
        textLbs = view.findViewById(R.id.textLbs);
        unit = view.findViewById(R.id.unit);
        cardKg = view.findViewById(R.id.cardKg);
        cardLbs = view.findViewById(R.id.cardLbs);
        displayWeight = view.findViewById(R.id.displayWeight);
//        displayWeight.setVisibility(View.INVISIBLE);

        numberPicker.setValue(defaultWeight);
        number_picker2.setValue(0);
        weight1.setText(String.valueOf(defaultWeight));
        weight2.setText(String.valueOf(0));

        enableNextBtn();
        selectUnit();
        selectWeight();
        selectWeight2();
        updatePosition();
        return view;
    }

    void selectUnit() {
        cardKg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightUnit = "kg";
                numberPicker.setMaxValue(180);
                defaultWeight = (int) (defaultWeight / 2.205);
                numberPicker.setValue(defaultWeight);
                weight1.setText(String.valueOf(defaultWeight));

                cardKg.setCardBackgroundColor(getResources().getColor(R.color.cardSelectedColor));
                cardLbs.setCardBackgroundColor(getResources().getColor(R.color.walk_through_cardcolor));
                unit.setText("kg");
            }
        });
        cardLbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightUnit = "lbs";
                numberPicker.setMaxValue(300);
                defaultWeight = (int) (defaultWeight * 2.205);
                numberPicker.setValue(defaultWeight);
                weight1.setText(String.valueOf(defaultWeight));

                cardLbs.setCardBackgroundColor(getResources().getColor(R.color.cardSelectedColor));
                cardKg.setCardBackgroundColor(getResources().getColor(R.color.walk_through_cardcolor));
                unit.setText("lbs");
            }
        });
    }

    private int selectWeight() {
        w1 = numberPicker.getValue();
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                defaultWeight = newVal;
                displayWeight.setVisibility(View.VISIBLE);
                w1 = newVal;
                weight1.setText(String.valueOf(newVal));
            }
        });
        return w1;
    }

    private int selectWeight2() {
        w2 = number_picker2.getValue();
        number_picker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                displayWeight.setVisibility(View.VISIBLE);
                w2 = newVal;
                weight2.setText(String.valueOf(newVal));
            }
        });
        return w2;
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
        Prefs.putString(Keys.KEY_WEIGHT_UNIT, weightUnit);
        int w1 = selectWeight();
        int w2 = selectWeight2();
        float weight = Float.parseFloat(w1 + "." + w2);
        Prefs.putDouble(Keys.KEY_WEIGHT, weight);
    }

    void updatePosition() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
                ((WalkThrough) getActivity()).updatePosition(4);
            }
        });
    }
}