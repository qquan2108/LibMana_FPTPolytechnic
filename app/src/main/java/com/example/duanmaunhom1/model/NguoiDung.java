package com.example.duanmaunhom1.model;

public class NguoiDung {
    private int mand;
    private String tennd;
    private String sdt;
    private String diachi;
    private String tendangnhap;
    private String matkhau;
    private int role;
    private boolean phoneVerified;

    public NguoiDung(int mand, String tennd, String sdt, String diachi, String tendangnhap, String matkhau, int role, boolean phoneVerified) {
        this.mand = mand;
        this.tennd = tennd;
        this.sdt = sdt;
        this.diachi = diachi;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.role = role;
        this.phoneVerified = phoneVerified;
    }

    public NguoiDung(String tennd, String sdt, String diachi, String tendangnhap, String matkhau, boolean phoneVerified) {
        this.tennd = tennd;
        this.sdt = sdt;
        this.diachi = diachi;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.phoneVerified = phoneVerified;
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }
}
