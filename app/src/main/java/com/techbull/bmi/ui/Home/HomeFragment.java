package com.techbull.bmi.ui.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.pixplicity.easyprefs.library.Prefs;
import com.techbull.bmi.Helper.Keys;
import com.techbull.bmi.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.DecimalFormat;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DecimalFormat nf = new DecimalFormat("#");
    private ImageView userImg;
    private TextView gender, age, weight, height, idealWeight;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDataToViews();

        Glide.with(getContext()).load(Prefs.getString(Keys.USER_IMAGE, "")).placeholder(R.drawable.user).into(userImg);
        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(getActivity());
            }
        });

    }

    void init(View view) {
        userImg = view.findViewById(R.id.userImg);
        gender = view.findViewById(R.id.gender);
        age = view.findViewById(R.id.age);
        height = view.findViewById(R.id.height);
        weight = view.findViewById(R.id.weight);
        idealWeight = view.findViewById(R.id.idealWeight);

    }

    @SuppressLint("SetTextI18n")
    private void setDataToViews() {
        gender.setText(Prefs.getString(Keys.KEY_GENDER, "Male"));
        age.setText(String.valueOf(Prefs.getInt(Keys.KEY_AGE, 18)));
        height.setText(nf.format(Prefs.getDouble(Keys.KEY_HEIGHT, 180)) + " " + Prefs.getString(Keys.KEY_HEIGHT_UNIT, "cm"));
        weight.setText(nf.format(Prefs.getDouble(Keys.KEY_WEIGHT, 70)) + " " + Prefs.getString(Keys.KEY_WEIGHT_UNIT, "kg"));
//      idealWeight.setText((int) preferenceHelper.getFloat("idealWight", 60) + " " + preferenceHelper.getString("weightUnit", "kg"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri imageUri;
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                Glide.with(getContext()).load(imageUri).into(userImg);
                Prefs.putString(Keys.USER_IMAGE, imageUri.toString());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}