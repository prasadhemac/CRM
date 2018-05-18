package com.ksoft.crm.data;

import java.awt.image.BufferedImage;

/**
 * Created by prasadh on 9/24/2016.
 */
public class Salary {

    private int id;

    private float basicSalary;

    private float allowance;

    private float otRate;

    private int deduction;

    public Salary(int id, float basicSalary, float allowance, float otRate, int deduction) {
        this.id = id;
        this.basicSalary = basicSalary;
        this.allowance = allowance;
        this.otRate = otRate;
        this.deduction = deduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(float basicSalary) {
        this.basicSalary = basicSalary;
    }

    public float getAllowance() {
        return allowance;
    }

    public void setAllowance(float allowance) {
        this.allowance = allowance;
    }

    public float getOtRate() {
        return otRate;
    }

    public void setOtRate(float id) {
        this.otRate = otRate;
    }

    public int getDeduction() {
        return deduction;
    }

    public void setDeduction(int deduction) {
        this.deduction = deduction;
    }
}
