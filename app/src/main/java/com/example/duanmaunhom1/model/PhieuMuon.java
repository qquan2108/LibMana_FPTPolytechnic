package com.example.duanmaunhom1.model;

import com.example.duanmaunhom1.CTPM;

public class PhieuMuon extends CTPM {
    private int mapm;
    private String ngaymuon;
    private String ngaytra;
    private int mand;

    private String tennd;

    public PhieuMuon(int mapm, String ngaymuon, String ngaytra, int mand, String tennd) {
        this.mapm = mapm;
        this.ngaymuon = ngaymuon;
        this.ngaytra = ngaytra;
        this.mand = mand;
        this.tennd = tennd;
    }

    public int getMapm() {
        return mapm;
    }

    public void setMapm(int mapm) {
        this.mapm = mapm;
    }

    public String getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(String ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public String getNgaytra() {
        return ngaytra;
    }

    public void setNgaytra(String ngaytra) {
        this.ngaytra = ngaytra;
    }

    public int getMand() {
        return mand;
    }

    public void setMand(int mand) {
        this.mand = mand;
    }

    public String getTennd() {
        return tennd;
    }

    public void setTennd(String tennd) {
        this.tennd = tennd;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
