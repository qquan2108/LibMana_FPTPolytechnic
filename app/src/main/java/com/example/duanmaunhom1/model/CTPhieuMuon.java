package com.example.duanmaunhom1.model;

public class CTPhieuMuon {
    private int mactpm;
    private int mapm;
    private int masach;
    private int soluong;

    private String tensach;

    private int trasach;
//    ct.mactpm , ct.mapm, ct.masach,ct.trasach, ct.soluong, s.tensach,ct.trasach
    public CTPhieuMuon(int mactpm, int mapm, int masach, int soluong, String tensach, int trasach) {
        this.mactpm = mactpm;
        this.mapm = mapm;
        this.masach = masach;
        this.soluong = soluong;
        this.tensach = tensach;
        this.trasach = trasach;
    }

    public int getMactpm() {
        return mactpm;
    }

    public void setMactpm(int mactpm) {
        this.mactpm = mactpm;
    }

    public int getMapm() {
        return mapm;
    }

    public void setMapm(int mapm) {
        this.mapm = mapm;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public int getTrasach() {
        return trasach;
    }

    public void setTrasach(int trasach) {
        this.trasach = trasach;
    }
}