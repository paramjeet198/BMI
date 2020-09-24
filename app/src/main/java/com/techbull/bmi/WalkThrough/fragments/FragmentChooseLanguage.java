package com.techbull.bmi.WalkThrough.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techbull.bmi.R;
import com.techbull.bmi.WalkThrough.Language.AdapterCountryList;
import com.techbull.bmi.WalkThrough.Language.CountryList;
import com.techbull.bmi.WalkThrough.WalkThrough;

import java.util.ArrayList;
import java.util.List;

public class FragmentChooseLanguage extends Fragment {
    private Button nextBtn;
    private SharedPreferences preferences;
    private RecyclerView recyclerView;
    private List<CountryList> countryLists;

    public FragmentChooseLanguage() {
        // Required empty public constructor
    }

    public static FragmentChooseLanguage newInstance(String param1, String param2) {
        FragmentChooseLanguage fragment = new FragmentChooseLanguage();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_language, container, false);

        if (getActivity() != null)
            preferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);

        countryLists = new ArrayList<>();

        nextBtn = view.findViewById(R.id.nextBtn);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        loadData();
        enableNextBtn();
        updatePosition();
        return view;
    }

    private void loadData() {
        countryLists.add(new CountryList("English", "En"));
        countryLists.add(new CountryList("Spanish", "sp"));
        countryLists.add(new CountryList("Russian", "ru"));
        countryLists.add(new CountryList("Portuguese", "Po"));
        countryLists.add(new CountryList("French", "Fr"));
        countryLists.add(new CountryList("Arabic", "Ar"));

        recyclerView.setAdapter(new AdapterCountryList(getContext(), countryLists));
    }


    void updatePosition() {
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().putInt("gender", 1).apply();
                ((WalkThrough) getActivity()).updatePosition(1);
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