package com.ksoft.crm.data;

/**
 * Created by prasadh on 9/25/2016.
 */
public class Deduction {

    private int id;

    private float epf;

    private float etf;

    private float welfare;

    public Deduction(int id, float epf, float etf, float welfare) {
        this.id = id;
        this.epf = epf;
        this.etf = etf;
        this.welfare = welfare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getEpf() {
        return epf;
    }

    public void setEpf(float epf) {
        this.epf = epf;
    }

    public float getEtf() {
        return etf;
    }

    public void setEtf(float etf) {
        this.etf = etf;
    }

    public float getWelfare() {
        return welfare;
    }

    public void setWelfare(float welfare) {
        this.welfare = welfare;
    }
}
