package com.techbull.bmi.ui.BMI_HOME;

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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;
import com.shawnlin.numberpicker.NumberPicker;
import com.techbull.bmi.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import soup.neumorphism.NeumorphCardView;

public class BmiFragment extends Fragment {
    private NeumorphCardView maleHolder, femaleHolder, cardShare, cmHolder, feetHolder, kgHolder, poundHolder;
    private RulerValuePicker heightInCm_ruler, heightInFeet_ruler, weightInKg_ruler, weightInPound_ruler;
    private NumberPicker numberPicker;
    private double age, defaultHeightInCm = 180, defaultHeightInInches = 70, defaultWeightInKg = 70, defaultWeightInPound = 155;
    private TextView bmiShow, fatPercentage, idealW, overW, healthyW, txtMale, txtFemale, textCm, textFeet, textKg, textPound, height0, height, height2, weight0, weight, weight2;
    private static NumberFormat nf = new DecimalFormat("###.#");
    private NumberFormat nf2 = new DecimalFormat("###.##");
    private ImageView share, selectMale, selectFemale, whatsApp, fb, insta, twitter;
    private ScrollView rootView;
    private LinearLayout shareLayout;
    boolean isShareLayoutVisible = false;
    private BmiModel data = new BmiModel();
    private ImageView moreIcon;
    private Dialog dialog;

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

        dialog = new Dialog(getContext());

        selectGender();
        selectAge();
        selectHeightUnit();
        selectWeightUnit();
        BmiMore();
        return root;
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

    private void init(View view) {
        moreIcon = view.findViewById(R.id.moreIcon);

        maleHolder = view.findViewById(R.id.maleHolder);
        femaleHolder = view.findViewById(R.id.femaleHolder);
        numberPicker = view.findViewById(R.id.number_picker);
        numberPicker.setValue(18);
        bmiShow = view.findViewById(R.id.bmiShow);
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

        setImagesWithGlide();
        screenShot();
        shareLayout();
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

    private void selectGender() {
        data.setMale(true);
        maleHolder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                data.setMale(true);
                fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), returnBmiResult()))) + "%");
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
        });

        femaleHolder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                data.setMale(false);
                fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), returnBmiResult()))) + "%");
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
        });
    }

    private int selectAge() {
        age = numberPicker.getValue();
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                age = newVal;
                fatPercentage.setText(nf.format((bodyFatPercentage((int) age, returnBmiResult()))) + "%");
            }
        });
        return (int) age;
    }

    //Height Unit...
    @SuppressLint("SetTextI18n")
    private void selectHeightUnit() {
//Default Values..
        heightInCm_ruler.setMinMaxValue(65, 300);
        heightInCm_ruler.selectValue((int) defaultHeightInCm);
        data.setHeightInCm(defaultHeightInCm);
        data.setCm(true);
        heightInCm_ruler.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {

            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                height0.setText(nf2.format((double) selectedValue / 100) + " Meter");
                height.setText(selectedValue + " Cm");
                height2.setText(nf2.format((double) selectedValue / 30.48) + " Ft");
                data.setHeightInCm(selectedValue);
                calculateResults();
            }
        });

        //After OnClick Values..
        cmHolder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                data.setCm(true);
                data.setInch(false);
                heightInFeet_ruler.setVisibility(View.GONE);
                heightInCm_ruler.setVisibility(View.VISIBLE);

                heightInCm_ruler.setMinMaxValue(65, 300);
                heightInCm_ruler.selectValue((int) defaultHeightInCm);
                height0.setText(nf2.format((double) defaultHeightInCm / 100) + " Meter");
                height.setText(defaultHeightInCm + " Cm");
                height2.setText(nf2.format((double) defaultHeightInCm / 30.48) + " Ft");
                data.setHeightInCm(defaultHeightInCm);
                heightInCm_ruler.setValuePickerListener(new RulerValuePickerListener() {
                    @Override
                    public void onValueChange(int selectedValue) {
                    }

                    @Override
                    public void onIntermediateValueChange(int selectedValue) {
                        height0.setText(nf2.format((double) selectedValue / 100) + " Meter");
                        height.setText(selectedValue + " Cm");
                        height2.setText(nf2.format(selectedValue / 30.48) + " Ft");
                        data.setHeightInCm(selectedValue);
                        calculateResults();
                    }
                });

                fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), returnBmiResult()))) + "%");
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

                reset();
            }
        });

//Height in Feet....
        feetHolder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                data.setInch(true);
                data.setCm(false);

                heightInFeet_ruler.setVisibility(View.VISIBLE);
                heightInCm_ruler.setVisibility(View.GONE);
                heightInFeet_ruler.setMinMaxValue(15, 150);
                heightInFeet_ruler.selectValue((int) defaultHeightInInches);
                data.setHeightInInch(defaultHeightInInches);
                height.setText((int) defaultHeightInInches / 12 + " Feet " + (int) defaultHeightInInches % 12 + " Inch");

                heightInFeet_ruler.setValuePickerListener(new RulerValuePickerListener() {
                    @Override
                    public void onValueChange(int selectedValue) {
                    }

                    @Override
                    public void onIntermediateValueChange(int selectedValue) {
                        height0.setText((int) (selectedValue * 2.54) + " Cm");
                        height.setText((int) selectedValue / 12 + " Feet " + selectedValue % 12 + " Inch");
                        height2.setText(nf2.format(selectedValue / 39.37) + " Meter");
                        data.setHeightInInch(selectedValue);
                        calculateResults();
                    }
                });

                fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), returnBmiResult()))) + "%");
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

                reset();
            }
        });
    }

    //Weight Unit..
    @SuppressLint("SetTextI18n")
    private void selectWeightUnit() {
//Default Weight Values..
        data.setKg(true);
        data.setPound(false);
        weightInKg_ruler.selectValue(60);
        weightInKg_ruler.selectValue((int) defaultWeightInKg);
        data.setWeightInKg(defaultWeightInKg);
        weightInKg_ruler.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {

            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {
                weight0.setText(nf.format(selectedValue * 2.205) + " Lb");
                weight.setText(selectedValue + " Kg");
                weight2.setText(nf.format(selectedValue / 6.35) + " Stone");
                data.setWeightInKg(selectedValue);
                calculateResults();
            }
        });

        //After OnClick Values..
        kgHolder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                data.setKg(true);
                data.setPound(false);

                weightInPound_ruler.setVisibility(View.GONE);
                weightInKg_ruler.setVisibility(View.VISIBLE);
                weightInKg_ruler.setMinMaxValue(20, 200);
                weightInKg_ruler.selectValue((int) defaultWeightInKg);

                weight.setText(defaultWeightInKg + " kg");
                data.setWeightInKg(defaultWeightInKg);
                weightInKg_ruler.setValuePickerListener(new RulerValuePickerListener() {
                    @Override
                    public void onValueChange(int selectedValue) {
                    }

                    @Override
                    public void onIntermediateValueChange(int selectedValue) {
                        weight0.setText(nf.format(selectedValue * 2.205) + " Lb");
                        weight.setText(selectedValue + " kg");
                        weight2.setText(nf.format(selectedValue / 6.35) + " Stone");
                        data.setWeightInKg(selectedValue);
                        calculateResults();
                    }
                });

                fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), returnBmiResult()))) + "%");
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

                reset();
            }
        });

//Weight in Pound....
        poundHolder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                data.setPound(true);
                data.setKg(false);

                weightInPound_ruler.setVisibility(View.VISIBLE);
                weightInKg_ruler.setVisibility(View.GONE);
                weightInPound_ruler.setMinMaxValue(40, 400);
                weightInPound_ruler.selectValue((int) defaultWeightInPound);

                weight.setText(defaultWeightInPound + " Pound");
                data.setWeightInPound(defaultWeightInPound);

                weightInPound_ruler.setValuePickerListener(new RulerValuePickerListener() {
                    @Override
                    public void onValueChange(int selectedValue) {

                    }

                    @Override
                    public void onIntermediateValueChange(int selectedValue) {
                        weight0.setText(nf.format(selectedValue / 2.205) + " Kg");
                        weight.setText(selectedValue + " Pound");
                        weight2.setText(nf.format((double) selectedValue / 14) + " Stone");
                        data.setWeightInPound(selectedValue);
                        calculateResults();
                    }
                });

                fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), returnBmiResult()))) + "%");
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

                reset();
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
        if (data.isCm() && data.isKg()) {
            bmiShow.setText(nf2.format(data.calculateBMI()));
            idealW.setText(nf.format(data.calculateIdealWeight()) + " kg");
            overW.setText(nf.format(data.calculateOverWeight()) + "kg");
            healthyW.setText(nf.format(data.returnMinWeight()) + " - " + nf.format(data.returnMaxWeight()) + " kg");
            fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), data.calculateBMI()))) + "%");
        } else if (data.isCm() && data.isPound()) {
            bmiShow.setText(nf2.format(data.calculateBMI()));
            idealW.setText(nf.format(data.calculateIdealWeight()) + " lbs");
            overW.setText(nf.format(data.calculateOverWeight()) + " lbs");
            healthyW.setText(nf.format(data.returnMinWeight()) + " - " + nf.format(data.returnMaxWeight()) + " lbs");
            fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), data.calculateBMI()))) + "%");
        } else if (data.isInch() && data.isKg()) {
            bmiShow.setText(nf2.format(data.calculateBMI()));
            idealW.setText(nf.format(data.calculateIdealWeight()) + " kg");
            overW.setText(nf.format(data.calculateOverWeight()) + "kg");
            healthyW.setText(nf.format(data.returnMinWeight()) + " - " + nf.format(data.returnMaxWeight()) + " kg");
            fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), data.calculateBMI()))) + "%");
        } else {
            bmiShow.setText(nf2.format(data.calculateBMI()));
            idealW.setText(nf.format(data.calculateIdealWeight()) + " lbs");
            overW.setText(nf.format(data.calculateOverWeight()) + " lbs");
            healthyW.setText(nf.format(data.returnMinWeight()) + " - " + nf.format(data.returnMaxWeight()) + " lbs");
            fatPercentage.setText(nf.format((bodyFatPercentage(selectAge(), data.calculateBMI()))) + "%");
        }
    }

    float bodyFatPercentage(int age, double bmiResult) {
        if (data.isMale()) {
            return (float) (((1.20 * bmiResult) + (0.23 * age)) - 16.2);
        } else {
            return (float) (((1.20 * bmiResult) + (0.23 * age)) - 5.4);
        }
    }

    private double returnBmiResult() {
        return data.calculateBMI();
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
        sendIntent.putExtra(Intent.EXTRA_TEXT, "My Body Mass Index (BMI) is " + nf.format(data.calculateBMI()) + " \n\nCheck out Android app at :-\n https://play.google.com/store/apps/details?id=com.techbull.bmi&hl=en ");
        sendIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(getContext(), getBitmapFromView(rootView, rootView.getChildAt(0).getHeight(), rootView.getChildAt(0).getWidth())));
        try {
            startActivity(sendIntent);

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "App Not Installed", Toast.LENGTH_SHORT).show();
        }
    }

}