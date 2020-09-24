package com.techbull.bmi.ui.BMI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;
import com.pixplicity.easyprefs.library.Prefs;
import com.shawnlin.numberpicker.NumberPicker;
import com.techbull.bmi.Helper.Keys;
import com.techbull.bmi.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import soup.neumorphism.NeumorphCardView;

public class BmiFragment extends Fragment {
    private static NumberFormat nf = new DecimalFormat("###.#");
    boolean isShareLayoutVisible = false;
    private NeumorphCardView maleHolder, femaleHolder, cardShare, cmHolder, feetHolder, kgHolder, poundHolder;
    private RulerValuePicker heightInCm_ruler, heightInFeet_ruler, weightInKg_ruler, weightInPound_ruler;
    private NumberPicker agePicker;
    private double age;
    private TextView bmiShow, bmiResultText, bmiResultText2, fatPercentage, idealW, overW, healthyW, txtMale, txtFemale, textCm, textFeet, textKg, textPound, height0, height, height2, weight0, weight, weight2;
    private NumberFormat nf2 = new DecimalFormat("###.##");
    private ImageView share, selectMale, selectFemale, whatsApp, fb, insta, twitter;
    private ScrollView rootView;
    private LinearLayout shareLayout;
    private ImageView moreIcon;
    private Dialog dialog;
    private int prefAge;
    private double prefWeight, prefHeight;
    private String prefGender, prefWeightUnit, prefHeightUnit;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bmi, container, false);

        init(root);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getContext() != null)
            dialog = new Dialog(getContext());

        fetchDataFromPrefs();
        initialData();
        selectGender();
        selectAge();
        selectHeightUnit();
        selectWeightUnit();
        BmiMore();

        setImagesWithGlide();
        screenShot();
        shareLayout();
    }

    private void fetchDataFromPrefs() {
        prefGender = Prefs.getString(Keys.KEY_GENDER, "Male");
        prefAge = Prefs.getInt(Keys.KEY_AGE, 18);
        prefWeight = Prefs.getDouble(Keys.KEY_WEIGHT, 70);
        prefHeight = Prefs.getDouble(Keys.KEY_HEIGHT, 180);
        prefWeightUnit = Prefs.getString(Keys.KEY_WEIGHT_UNIT, "kg");
        prefHeightUnit = Prefs.getString(Keys.KEY_HEIGHT_UNIT, "cm");
    }

    private void initialData() {
//Gender....
        if (prefGender.equals("Male")) {
            selectMale();
        }
        if (prefGender.equals("Female")) {
            selectFemale();
        }
//Age....
        agePicker.setValue(prefAge);

//Height....
        if (prefHeightUnit.equals("cm")) {
            selectCentimeter();
            centimeterRuler();
        }
        if (prefHeightUnit.equals("inch")) {
            selectFeet();
            feetRuler();
        }

//weight....
        if (prefWeightUnit.equals("kg")) {
            selectKg();
            kgRuler();
        }
        if (prefWeightUnit.equals("lbs")) {
            selectPound();
            poundRuler();
        }
    }

    private void init(View view) {
        moreIcon = view.findViewById(R.id.moreIcon);

        maleHolder = view.findViewById(R.id.maleHolder);
        femaleHolder = view.findViewById(R.id.femaleHolder);
        agePicker = view.findViewById(R.id.number_picker);
        bmiShow = view.findViewById(R.id.bmiShow);
        bmiResultText = view.findViewById(R.id.bmiResultText);
        bmiResultText2 = view.findViewById(R.id.bmiResultText2);
        fatPercentage = view.findViewById(R.id.fatPercentage);
        idealW = view.findViewById(R.id.idealW);
        overW = view.findViewById(R.id.overW);
        healthyW = view.findViewById(R.id.healthyW);
        txtMale = view.findViewById(R.id.textMale);
        txtFemale = view.findViewById(R.id.textFemale);
        selectMale = view.findViewById(R.id.selectMale);
        selectFemale = view.findViewById(R.id.selectFemale);

        whatsApp = view.findViewById(R.id.whatsApp);
        fb = view.findViewById(R.id.fb);
        insta = view.findViewById(R.id.instagram);
        twitter = view.findViewById(R.id.twitter);
        rootView = view.findViewById(R.id.scrollView);
        shareLayout = view.findViewById(R.id.shareLayout);
        share = view.findViewById(R.id.share);
        cardShare = view.findViewById(R.id.cardShare);

        height0 = view.findViewById(R.id.height0);
        height = view.findViewById(R.id.height);
        height2 = view.findViewById(R.id.height2);
        weight0 = view.findViewById(R.id.weight0);
        weight = view.findViewById(R.id.weight);
        weight2 = view.findViewById(R.id.weight2);
        textCm = view.findViewById(R.id.textCm);
        textFeet = view.findViewById(R.id.textFeet);
        textKg = view.findViewById(R.id.textKg);
        textPound = view.findViewById(R.id.textPound);
        cmHolder = view.findViewById(R.id.cmHolder);
        feetHolder = view.findViewById(R.id.feetHolder);
        kgHolder = view.findViewById(R.id.kgHolder);
        poundHolder = view.findViewById(R.id.poundHolder);
        heightInCm_ruler = view.findViewById(R.id.heightInCm_ruler);
        heightInFeet_ruler = view.findViewById(R.id.heightInFeet_ruler);
        weightInKg_ruler = view.findViewById(R.id.weightInKg_ruler);
        weightInPound_ruler = view.findViewById(R.id.weightInPound_ruler);
    }

    private void selectGender() {
        maleHolder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Prefs.putString(Keys.KEY_GENDER, "Male");
                prefGender = "Male";
                fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), calculateBMI()))) + "%");
                selectMale();
            }
        });

        femaleHolder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Prefs.putString(Keys.KEY_GENDER, "Female");
                prefGender = "Female";
                fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), calculateBMI()))) + "%");
                selectFemale();
            }
        });
    }

    private void selectMale() {
        maleHolder.setShapeType(1);
        maleHolder.setBackgroundColor(getResources().getColor(R.color.genderSelectedCardBackground));
        maleHolder.setShadowColorDark(getResources().getColor(R.color.genderSelectedShadowColorDark));
        maleHolder.setShadowColorLight(getResources().getColor(R.color.genderSelectedShadowColorLight));

        femaleHolder.setShapeType(2);
        femaleHolder.setBackgroundColor(getResources().getColor(R.color.neumorph_BackgroundColor));
        femaleHolder.setShadowColorDark(getResources().getColor(R.color.neumorph_shadowColorDark));
        femaleHolder.setShadowColorLight(getResources().getColor(R.color.neumorph_shadowColorLight));
        txtMale.setTextColor(Color.parseColor("#FFFFFF"));
        txtFemale.setTextColor(getResources().getColor(R.color.textColor));
    }

    private void selectFemale() {
        femaleHolder.setShapeType(1);
        femaleHolder.setBackgroundColor(getResources().getColor(R.color.genderSelectedCardBackground));
        femaleHolder.setShadowColorDark(getResources().getColor(R.color.genderSelectedShadowColorDark));
        femaleHolder.setShadowColorLight(getResources().getColor(R.color.genderSelectedShadowColorLight));

        maleHolder.setShapeType(2);
        maleHolder.setBackgroundColor(getResources().getColor(R.color.neumorph_BackgroundColor));
        maleHolder.setShadowColorDark(getResources().getColor(R.color.neumorph_shadowColorDark));
        maleHolder.setShadowColorLight(getResources().getColor(R.color.neumorph_shadowColorLight));
        txtFemale.setTextColor(Color.parseColor("#FFFFFF"));
        txtMale.setTextColor(getResources().getColor(R.color.textColor));
    }

    private int selectAge() {
        age = agePicker.getValue();
        agePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                age = newVal;
                Prefs.putInt(Keys.KEY_AGE, newVal);
                prefAge = newVal;
                fatPercentage.setText(nf.format((bodyFatPercentage(newVal, calculateBMI()))) + "%");
            }
        });
        return (int) age;
    }

    //Height Unit...
    @SuppressLint("SetTextI18n")
    private void selectHeightUnit() {
//After OnClick Values...
        cmHolder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                selectCentimeter();
                centimeterRuler();
                calculateResults();
//                reset();
            }
        });
//Height in Feet....
        feetHolder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                selectFeet();
                feetRuler();
                calculateResults();
//                reset();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void centimeterRuler() {
        heightInCm_ruler.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                height0.setText(nf2.format((double) selectedValue / 100) + " Meter");
                height.setText(selectedValue + " Cm");
                height2.setText(nf2.format(selectedValue / 30.48) + " Ft");
                Prefs.putDouble(Keys.KEY_HEIGHT, selectedValue);

                fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), calculateBMI()))) + "%");
                prefHeight = selectedValue;
                calculateResults();

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void feetRuler() {
        heightInFeet_ruler.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                height0.setText((int) (selectedValue) + " Inches");
                height.setText((int) selectedValue / 12 + " Feet " + selectedValue % 12 + " Inch");
                height2.setText(nf2.format(selectedValue / 39.37) + " Meter");
                Prefs.putDouble(Keys.KEY_HEIGHT, selectedValue);
                fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), calculateBMI()))) + "%");
                prefHeight = selectedValue;
                calculateResults();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void selectCentimeter() {
        heightInFeet_ruler.setVisibility(View.GONE);
        heightInCm_ruler.setVisibility(View.VISIBLE);
        heightInCm_ruler.setMinMaxValue(65, 300);
        heightInCm_ruler.selectValue((int) prefHeight);
        Prefs.putString(Keys.KEY_HEIGHT_UNIT, "cm");
        prefHeightUnit = "cm";
        height0.setText(nf2.format((double) prefHeight / 100) + " Meter");
        height.setText(prefHeight + " Cm");
        height2.setText(nf2.format((double) prefHeight / 30.48) + " Ft");

        setSelectedCM();
    }

    private void setSelectedCM() {
        cmHolder.setShapeType(1);
        cmHolder.setBackgroundColor(getResources().getColor(R.color.genderSelectedCardBackground));
        cmHolder.setShadowColorDark(getResources().getColor(R.color.genderSelectedShadowColorDark));
        cmHolder.setShadowColorLight(getResources().getColor(R.color.genderSelectedShadowColorLight));
        textCm.setTextColor(Color.parseColor("#FFFFFF"));

        feetHolder.setShapeType(2);
        feetHolder.setBackgroundColor(getResources().getColor(R.color.neumorph_BackgroundColor));
        feetHolder.setShadowColorDark(getResources().getColor(R.color.neumorph_shadowColorDark));
        feetHolder.setShadowColorLight(getResources().getColor(R.color.neumorph_shadowColorLight));
        textFeet.setTextColor(getResources().getColor(R.color.textColor));
    }

    @SuppressLint("SetTextI18n")
    private void selectFeet() {
        heightInFeet_ruler.setVisibility(View.VISIBLE);
        heightInCm_ruler.setVisibility(View.GONE);
        heightInFeet_ruler.setMinMaxValue(15, 120);
        heightInFeet_ruler.selectValue((int) prefHeight);
        Prefs.putString(Keys.KEY_HEIGHT_UNIT, "inch");
        prefHeightUnit = "inch";
        height0.setText((int) (prefHeight) + " Inches");
        height.setText((int) prefHeight / 12 + " Feet " + (int) (prefHeight % 12) + " Inch");
        height2.setText(nf2.format(prefHeight / 39.37) + " Meter");

        setSelectedFeet();
    }

    private void setSelectedFeet() {
        feetHolder.setShapeType(1);
        feetHolder.setBackgroundColor(getResources().getColor(R.color.genderSelectedCardBackground));
        feetHolder.setShadowColorDark(getResources().getColor(R.color.genderSelectedShadowColorDark));
        feetHolder.setShadowColorLight(getResources().getColor(R.color.genderSelectedShadowColorLight));
        textFeet.setTextColor(Color.parseColor("#FFFFFF"));

        cmHolder.setShapeType(2);
        cmHolder.setBackgroundColor(getResources().getColor(R.color.neumorph_BackgroundColor));
        cmHolder.setShadowColorDark(getResources().getColor(R.color.neumorph_shadowColorDark));
        cmHolder.setShadowColorLight(getResources().getColor(R.color.neumorph_shadowColorLight));
        textCm.setTextColor(getResources().getColor(R.color.textColor));
    }

    //Weight Unit..
    @SuppressLint("SetTextI18n")
    private void selectWeightUnit() {
//Weight in kg....
        kgHolder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                selectKg();
                kgRuler();
                calculateResults();
//                reset();
            }
        });

//Weight in Pound....
        poundHolder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                poundRuler();
                selectPound();
                calculateResults();
//                reset();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void selectKg() {
        Prefs.putString(Keys.KEY_WEIGHT_UNIT, "kg");
        prefWeightUnit = "kg";
        weightInPound_ruler.setVisibility(View.GONE);
        weightInKg_ruler.setVisibility(View.VISIBLE);
        weightInKg_ruler.setMinMaxValue(20, 200);
        weightInKg_ruler.selectValue((int) prefWeight);


        weight0.setText(nf.format(prefWeight * 2.205) + " Lb");
        weight.setText(prefWeight + " kg");
        weight2.setText(nf.format(prefWeight / 6.35) + " Stone");

        setSelectedKg();
    }

    @SuppressLint("SetTextI18n")
    private void setSelectedKg() {
        kgHolder.setShapeType(1);
        kgHolder.setBackgroundColor(getResources().getColor(R.color.genderSelectedCardBackground));
        kgHolder.setShadowColorDark(getResources().getColor(R.color.genderSelectedShadowColorDark));
        kgHolder.setShadowColorLight(getResources().getColor(R.color.genderSelectedShadowColorLight));
        textKg.setTextColor(Color.parseColor("#FFFFFF"));

        poundHolder.setShapeType(2);
        poundHolder.setBackgroundColor(getResources().getColor(R.color.neumorph_BackgroundColor));
        poundHolder.setShadowColorDark(getResources().getColor(R.color.neumorph_shadowColorDark));
        poundHolder.setShadowColorLight(getResources().getColor(R.color.neumorph_shadowColorLight));
        textPound.setTextColor(getResources().getColor(R.color.textColor));
    }

    @SuppressLint("SetTextI18n")
    private void selectPound() {
        Prefs.putString(Keys.KEY_WEIGHT_UNIT, "lbs");
        prefWeightUnit = "lbs";
        weightInPound_ruler.setVisibility(View.VISIBLE);
        weightInKg_ruler.setVisibility(View.GONE);
        weightInPound_ruler.setMinMaxValue(40, 500);
        weightInPound_ruler.selectValue((int) prefWeight);

        weight0.setText(nf.format(prefWeight / 2.205) + " Kg");
        weight.setText(prefWeight + " Pound");
        weight2.setText(nf.format((double) prefWeight / 14) + " Stone");

        setSelectedLbs();
    }

    private void setSelectedLbs() {
        poundHolder.setShapeType(1);
        poundHolder.setBackgroundColor(getResources().getColor(R.color.genderSelectedCardBackground));
        poundHolder.setShadowColorDark(getResources().getColor(R.color.genderSelectedShadowColorDark));
        poundHolder.setShadowColorLight(getResources().getColor(R.color.genderSelectedShadowColorLight));
        textPound.setTextColor(Color.parseColor("#FFFFFF"));

        kgHolder.setShapeType(2);
        kgHolder.setBackgroundColor(getResources().getColor(R.color.neumorph_BackgroundColor));
        kgHolder.setShadowColorDark(getResources().getColor(R.color.neumorph_shadowColorDark));
        kgHolder.setShadowColorLight(getResources().getColor(R.color.neumorph_shadowColorLight));
        textKg.setTextColor(getResources().getColor(R.color.textColor));
    }

    @SuppressLint("SetTextI18n")
    private void kgRuler() {
        weightInKg_ruler.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                weight0.setText(nf.format(selectedValue * 2.205) + " Lb");
                weight.setText(selectedValue + " kg");
                weight2.setText(nf.format(selectedValue / 6.35) + " Stone");
                Prefs.putDouble(Keys.KEY_WEIGHT, selectedValue);

                prefWeight = selectedValue;
                calculateResults();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void poundRuler() {
        weightInPound_ruler.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {

            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                weight0.setText(nf.format(selectedValue / 2.205) + " Kg");
                weight.setText(selectedValue + " Pound");
                weight2.setText(nf.format((double) selectedValue / 14) + " Stone");
                Prefs.putDouble(Keys.KEY_WEIGHT, selectedValue);
                prefWeight = selectedValue;
                calculateResults();
            }
        });
    }

    private void reset() {
        idealW.setText("-");
        overW.setText("-");
        healthyW.setText("-");
        fatPercentage.setText("-");
    }

    @SuppressLint("SetTextI18n")
    private void calculateResults() {
        bmiResultText(calculateBMI());
        bmiShow.setText(nf2.format(calculateBMI()));
        if (prefHeightUnit.equals("cm") && prefWeightUnit.equals("kg")) {
            idealW.setText(nf.format(calculateIdealWeight()) + " kg");
            overW.setText(nf.format(calculateOverWeight()) + "kg");
            healthyW.setText(nf.format(returnMinWeight()) + " - " + nf.format(returnMaxWeight()) + " kg");
            fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), calculateBMI()))) + "%");
        } else if (prefHeightUnit.equals("cm") && prefWeightUnit.equals("lbs")) {
            idealW.setText(nf.format(calculateIdealWeight()) + " lbs");
            overW.setText(nf.format(calculateOverWeight()) + " lbs");
            healthyW.setText(nf.format(returnMinWeight()) + " - " + nf.format(returnMaxWeight()) + " lbs");
            fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), calculateBMI()))) + "%");
        } else if (prefHeightUnit.equals("ft") && prefWeightUnit.equals("kg")) {
            idealW.setText(nf.format(calculateIdealWeight()) + " kg");
            overW.setText(nf.format(calculateOverWeight()) + "kg");
            healthyW.setText(nf.format(returnMinWeight()) + " - " + nf.format(returnMaxWeight()) + " kg");
            fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), calculateBMI()))) + "%");
        } else {
            idealW.setText(nf.format(calculateIdealWeight()) + " lbs");
            overW.setText(nf.format(calculateOverWeight()) + " lbs");
            healthyW.setText(nf.format(returnMinWeight()) + " - " + nf.format(returnMaxWeight()) + " lbs");
            fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), calculateBMI()))) + "%");
        }
    }

    public double calculateOverWeight() {
        if (calculateBMI() > 25) {
            if (prefHeightUnit.equals("cm") && prefWeightUnit.equals("kg")) {
                return prefWeight - returnMaxWeight();
            } else if (prefHeightUnit.equals("cm") && prefWeightUnit.equals("lbs")) {
                return prefWeight - returnMaxWeight();
            } else if (prefHeightUnit.equals("inch") && prefWeightUnit.equals("kg")) {
                return prefWeight - returnMaxWeight();
            } else {
                return prefWeight - returnMaxWeight();
            }
        } else {
            return 0;
        }
    }

    public double calculateIdealWeight() {
        return (returnMinWeight() + returnMaxWeight()) / 2;
    }

    public double calculateBMI() {
//        Toast.makeText(getContext(), prefWeightUnit + " " + prefHeightUnit, Toast.LENGTH_SHORT).show();
        if (prefHeightUnit.equals("cm") && prefWeightUnit.equals("kg")) {
            return prefWeight / (prefHeight / 100 * prefHeight / 100);
        } else if (prefHeightUnit.equals("cm") && prefWeightUnit.equals("lbs")) {
            return prefWeight / 2.205 / (prefHeight / 100 * prefHeight / 100);
        } else if (prefHeightUnit.equals("inch") && prefWeightUnit.equals("lbs")) {
            return prefWeight * 703 / (prefHeight * prefHeight);
        } else {
            return prefWeight / ((prefHeight / 39.37) * (prefHeight / 39.37));
        }
    }

    public double returnMinWeight() {
        if (prefHeightUnit.equals("cm") && prefWeightUnit.equals("kg")) {
            return 18.5 * (prefHeight / 100 * prefHeight / 100);
        } else if (prefHeightUnit.equals("cm") && prefWeightUnit.equals("lbs")) {
            return 40.78552 * (prefHeight / 100 * prefHeight / 100);
        } else if (prefHeightUnit.equals("inch") && prefWeightUnit.equals("kg")) {
            return 18.5 * ((prefHeight / 39.37) * (prefHeight / 39.37));
        } else {
            return 40.78552 * ((prefHeight / 39.37) * (prefHeight / 39.37));
        }
    }

    public double returnMaxWeight() {
        if (prefHeightUnit.equals("cm") && prefWeightUnit.equals("kg")) {
            return 24.9 * (prefHeight / 100 * prefHeight / 100);
        } else if (prefHeightUnit.equals("cm") && prefWeightUnit.equals("lbs")) {
            return 54.8951 * (prefHeight / 100 * prefHeight / 100);
        } else if (prefHeightUnit.equals("inch") && prefWeightUnit.equals("kg")) {
            return 24.9 * ((prefHeight / 39.37) * (prefHeight / 39.37));
        } else {
            return 54.8951 * ((prefHeight / 39.37) * (prefHeight / 39.37));
        }
    }

    float bodyFatPercentage(int age, double bmiResult) {
        if (prefGender.equals("Male")) {
            return (float) (((1.20 * bmiResult) + (0.23 * age)) - 16.2);
        } else {
            return (float) (((1.20 * bmiResult) + (0.23 * age)) - 5.4);
        }
    }

    @SuppressLint("SetTextI18n")
    void bmiResultText(double bmi) {
        if (bmi < 18.5) {
            bmiResultText.setText("Underweight");
            bmiResultText.setTextColor(Color.parseColor("#00A8F0"));

            bmiResultText2.setText("Time to grab a bite!");
            bmiResultText2.setTextColor(Color.parseColor("#00A8F0"));
        } else if (bmi >= 18.5 && bmi <= 24.9) {
            bmiResultText.setText("Normal");
            bmiResultText.setTextColor(Color.parseColor("#38C8A8"));

            bmiResultText2.setText("Great Shape!");
            bmiResultText2.setTextColor(Color.parseColor("#38C8A8"));
        } else if (bmi > 24.9 && bmi <= 30.9) {
            bmiResultText.setText("Overweight");
            bmiResultText.setTextColor(Color.parseColor("#FFB828"));

            bmiResultText2.setText("Time for a run!");
            bmiResultText2.setTextColor(Color.parseColor("#FFB828"));
        } else if (bmi > 30.9 && bmi <= 39.9) {
            bmiResultText.setText("Obese");
            bmiResultText.setTextColor(Color.parseColor("#F1801D"));

            bmiResultText2.setText("Time for a run!");
            bmiResultText2.setTextColor(Color.parseColor("#F1801D"));
        } else {
            bmiResultText.setText("Morbidly Obese");
            bmiResultText.setTextColor(Color.parseColor("#FF1805"));

            bmiResultText2.setText("Time for a run!");
            bmiResultText2.setTextColor(Color.parseColor("#FF1805"));
        }

    }

    public void BmiMore() {
        moreIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.dialog_bmi_chart_table);
                Button ok = dialog.findViewById(R.id.bmiTableOk);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    private void screenShot() {
        whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                } else {
                    sendScreenShot("com.whatsapp");
                }
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                } else {
                    sendScreenShot("com.facebook.katana");
                }
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                } else {
                    sendScreenShot("com.instagram.android");
                }
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                } else {
                    sendScreenShot("com.twitter.android");
                }
            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        OutputStream fOut;
        String path = "";
        File folder = new File(Environment.getExternalStorageDirectory() + "/" + "Olympia", "Screenshot");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-d_hh:mm", now);

        File f = new File(folder, now + " Quotes.jpg");
        try {
            fOut = new FileOutputStream(f);
            inImage.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), f.getAbsolutePath(), f.getName(), f.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Uri.parse(path);
    }

    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    private void sendScreenShot(String packageName) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/*");
        sendIntent.setType("text/plain");
        sendIntent.setPackage(packageName); //To Open specific apps fb,whatsapp,insta,etc.,
        sendIntent.putExtra(Intent.EXTRA_TEXT, "My Body Mass Index (BMI) is " + nf.format(calculateBMI()) + " \n\nCheck out Android app at :-\n https://play.google.com/store/apps/details?id=com.techbull.bmi&hl=en ");
        sendIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(getContext(), getBitmapFromView(rootView, rootView.getChildAt(0).getHeight(), rootView.getChildAt(0).getWidth())));
        try {
            startActivity(sendIntent);

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "App Not Installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareLayout() {
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShareLayoutVisible) {
                    isShareLayoutVisible = false;
                    Animation hide = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in);
                    shareLayout.startAnimation(hide);
                    shareLayout.setVisibility(View.GONE);
                } else {
                    isShareLayoutVisible = true;
                    Animation show = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out);
                    shareLayout.startAnimation(show);
                    shareLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        cardShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShareLayoutVisible) {
                    isShareLayoutVisible = false;
                    Animation hide = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in);
                    shareLayout.startAnimation(hide);
                    shareLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setImagesWithGlide() {
        Glide.with(this).load(R.drawable.ic_user_male_circle).into(selectMale);
        Glide.with(this).load(R.drawable.ic_user_female_circle).into(selectFemale);

        Glide.with(this).load(R.drawable.ic_whatsapp).into(whatsApp);
        Glide.with(this).load(R.drawable.ic_facebook_round).into(fb);
        Glide.with(this).load(R.drawable.ic_instagram).into(insta);
        Glide.with(this).load(R.drawable.ic_twitter).into(twitter);
    }

}