package com.example.duanmaunhom1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmaunhom1.database.DbHelper;
import com.example.duanmaunhom1.model.NguoiDung;

import java.util.ArrayList;

public class ThanhVienDAO {
    private DbHelper dbHelper;

    public ThanhVienDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // LAY THANH VIEN CAC THANH VIEN
    public ArrayList<NguoiDung> getDSNguoidung() {
        ArrayList<NguoiDung> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new NguoiDung(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), false));
            } while (cursor.moveToNext());

        }
        return list;
    }

    // THEM THANH VIEN
    public boolean themthanhvien(String tennd, String sodienthoai, String diachi, String tendangnhap, String matkhau, int role) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put("tennd", tennd);
        contentValues.put("sdt", sodienthoai);
        contentValues.put("diachi", diachi);
        contentValues.put("tendangnhap", tendangnhap);
        contentValues.put("matkhau", matkhau);
        contentValues.put("role", role);

        long check = sqLiteDatabase.insert("NGUOIDUNG", null, contentValues);
//        if (check == -1) {
//            return false;
//        }else {
//            return true;
//        }
        return check != -1;
    }

    public boolean suathanhvien(NguoiDung nguoiDung) {

        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("tennd", nguoiDung.getTennd());
        contentValues.put("sdt", nguoiDung.getSdt());
        contentValues.put("diachi", nguoiDung.getDiachi());
        contentValues.put("tendangnhap", nguoiDung.getTendangnhap());
        contentValues.put("matkhau", nguoiDung.getMatkhau());
        contentValues.put("role", nguoiDung.getRole());

        int check = sqLiteDatabase.update("NGUOIDUNG", contentValues, "mand=?", new String[]{String.valueOf(nguoiDung.getMand())});
        return check != 0;
    }

    public void xoathanhvien(int mand) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("NGUOIDUNG", "mand=?", new String[]{mand + ""});
    }

    public boolean capnhatmatkhau(String username, String oldpass, String newpass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE tendangnhap=? AND matkhau=?", new String[]{username, oldpass});
        if (cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", newpass);
            long check = sqLiteDatabase.update("NGUOIDUNG", contentValues, "tendangnhap=?", new String[]{username});
            if (check == -1)
                return false;
            return true;
        }
            return false;

        }
    }



