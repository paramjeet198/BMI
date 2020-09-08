package com.techbull.bmi.ui.BMI_HOME;

public class BmiModel {
    private double heightInCm;
    private double heightInInch;
    private double weightInKg;
    private double weightInPound;
    private boolean isCm, isInch, isKg, isPound, isMale;

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public boolean isCm() {
        return isCm;
    }

    public void setCm(boolean cm) {
        isCm = cm;
    }

    public boolean isInch() {
        return isInch;
    }

    public void setInch(boolean inch) {
        isInch = inch;
    }

    public boolean isKg() {
        return isKg;
    }

    public void setKg(boolean kg) {
        isKg = kg;
    }

    public boolean isPound() {
        return isPound;
    }

    public void setPound(boolean pound) {
        isPound = pound;
    }

    public double getHeightInCm() {
        return heightInCm;
    }

    public void setHeightInCm(double heightInCm) {
        this.heightInCm = heightInCm;
    }

    public double getHeightInInch() {
        return heightInInch;
    }

    public void setHeightInInch(double heightInInch) {
        this.heightInInch = heightInInch;
    }

    public double getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(double weightInKg) {
        this.weightInKg = weightInKg;
    }

    public double getWeightInPound() {
        return weightInPound;
    }

    public void setWeightInPound(double weightInPound) {
        this.weightInPound = weightInPound;
    }

    public double calculateBMI() {
        if (isCm() && isKg()) {
            return weightInKg / (heightInCm / 100 * heightInCm / 100);
        } else if (isCm() && isPound()) {
            return weightInPound / 2.205 / (heightInCm / 100 * heightInCm / 100);
        } else if (isInch() && isPound()) {
            return weightInPound * 703 / (heightInInch * heightInInch);
        } else {
            return weightInKg / ((heightInInch / 39.37) * (heightInInch / 39.37));
        }
    }

    public double returnMinWeight() {
        if (isCm() && isKg()) {
            return 18.5 * (getHeightInCm() / 100 * getHeightInCm() / 100);
        } else if (isCm() && isPound()) {
            return 40.78552 * (getHeightInCm() / 100 * getHeightInCm() / 100);
        } else if (isInch() && isKg()) {
            return 18.5 * ((getHeightInInch() / 39.37) * (getHeightInInch() / 39.37));
        } else {
            return 40.78552 * ((getHeightInInch() / 39.37) * (getHeightInInch() / 39.37));
        }
    }

    public double returnMaxWeight() {
        if (isCm() && isKg()) {
            return 24.9 * (getHeightInCm() / 100 * getHeightInCm() / 100);
        } else if (isCm() && isPound()) {
            return 54.8951 * (getHeightInCm() / 100 * getHeightInCm() / 100);
        } else if (isInch() && isKg()) {
            return 24.9 * ((getHeightInInch() / 39.37) * (getHeightInInch() / 39.37));
        } else {
            return 54.8951 * ((getHeightInInch() / 39.37) * (getHeightInInch() / 39.37));
        }
    }

    public double calculateIdealWeight() {
        return (returnMinWeight() + returnMaxWeight()) / 2;
    }

    public double calculateOverWeight() {
        if (calculateBMI() > 25) {
            if (isCm() && isKg()) {
                return weightInKg - returnMaxWeight();
            } else if (isCm() && isPound()) {
                return weightInPound - returnMaxWeight();
            } else if (isInch() && isKg()) {
                return weightInKg - returnMaxWeight();
            } else {
                return weightInPound - returnMaxWeight();
            }
        } else {
            return 0;
        }
    }
}
