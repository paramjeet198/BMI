package com.techbull.bmi.WalkThrough.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.pixplicity.easyprefs.library.Prefs;
import com.techbull.bmi.Helper.Keys;
import com.techbull.bmi.R;
import com.techbull.bmi.WalkThrough.WalkThrough;

public class FragmentChooseGender extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button nextBtn;
    private CardView maleCard, femaleCard;
    private ImageView imgMale, imgFemale;
    private String gender = "Male";

    public FragmentChooseGender() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentChooseGender newInstance(String param1, String param2) {
        FragmentChooseGender fragment = new FragmentChooseGender();
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
        View view = inflater.inflate(R.layout.fragment_gender, container, false);

        nextBtn = view.findViewById(R.id.nextBtn);
        maleCard = view.findViewById(R.id.cardMale);
        femaleCard = view.findViewById(R.id.cardFemale);
        imgMale = view.findViewById(R.id.imgMale);
        imgFemale = view.findViewById(R.id.imgFemale);

        disableNextBtn();
        setColors();
        updatePosition();
        return view;
    }

    void updatePosition() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.putString(Keys.KEY_GENDER, gender);
//                preferenceHelper.saveToPref("gender", gender);
                ((WalkThrough) getActivity()).updatePosition(2);
            }
        });
    }

    void setColors() {
        maleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Male";
                enableNextBtn();
                imgMale.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.walk_through_cardcolor)));
                imgFemale.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.cardSelectedColor)));
                maleCard.setCardBackgroundColor(getResources().getColor(R.color.cardSelectedColor));
                femaleCard.setCardBackgroundColor(getResources().getColor(R.color.walk_through_cardcolor));
            }
        });

        femaleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Female";
                enableNextBtn();
                imgFemale.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.walk_through_cardcolor)));
                imgMale.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.cardSelectedColor)));
                femaleCard.setCardBackgroundColor(getResources().getColor(R.color.cardSelectedColor));
                maleCard.setCardBackgroundColor(getResources().getColor(R.color.walk_through_cardcolor));
            }
        });

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