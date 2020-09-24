package com.techbull.bmi.WalkThrough;

public class ModelData {
    private boolean isMale = false, isFemale = false, isTransGender = false, isKg = false, isLbs = false, isCm = false, isFt = false;
    private int age;
    private double weight, height;

    public boolean isCm() {
        return isCm;
    }

    public void setCm(boolean cm) {
        isCm = cm;
    }

    public boolean isFt() {
        return isFt;
    }

    public void setFt(boolean ft) {
        isFt = ft;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isKg() {
        return isKg;
    }

    public void setKg(boolean kg) {
        isKg = kg;
    }

    public boolean isLbs() {
        return isLbs;
    }

    public void setLbs(boolean lbs) {
        isLbs = lbs;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
    }

    public boolean isTransGender() {
        return isTransGender;
    }

    public void setTransGender(boolean transGender) {
        isTransGender = transGender;
    }
}
