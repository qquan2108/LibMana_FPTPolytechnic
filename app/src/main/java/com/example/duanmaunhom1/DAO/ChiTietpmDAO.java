package com.example.duanmaunhom1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duanmaunhom1.database.DbHelper;
import com.example.duanmaunhom1.model.CTPhieuMuon;

import java.util.ArrayList;

public class ChiTietpmDAO {

    private DbHelper dbHelper;

    public ChiTietpmDAO(Context context){
        dbHelper = new DbHelper(context);

    }

    // llay danh sach cac cuon sach
//  // SELECT ct.mactpm , ct.mapm, ct.masach, ct.soluong, s.tensach  FROM SACH s , CTPM ct WHERE s.masach = ct.masach
    public ArrayList<CTPhieuMuon> getCTPM(){
        ArrayList<CTPhieuMuon> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ct.mactpm , ct.mapm, ct.masach,ct.trasach, ct.soluong,ct.trasach, s.tensach  FROM SACH s , CTPM ct WHERE s.masach = ct.masach",null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
        }do{
            list.add( new CTPhieuMuon(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4),cursor.getInt(5)));
        }while (cursor.moveToNext());

        return list;

    }
    public boolean themctpm(int mapm, int masach, int soluong){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put("mapm",mapm);
        contentValues.put("masach",masach);
        contentValues.put("soluong",soluong);


        long check = sqLiteDatabase.insert("CTPM",null,contentValues);
//        if (check == -1) {
//            return false;
//        }else {
//            return true;
//        }
        return check != -1;
    }

    public boolean suaCTPM(CTPhieuMuon ctPhieuMuon){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("mapm",ctPhieuMuon.getMapm());
        contentValues.put("masach",ctPhieuMuon.getMasach());
        contentValues.put("soluong",ctPhieuMuon.getSoluong());


        int check = sqLiteDatabase.update("CTPM",contentValues,"mactpm = ?",new String[]{ctPhieuMuon.getMactpm()+""});
        return check != 0;
    }
    public void xoactpm(int mactpm){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        db.delete("CTPM","mactpm=?",new String[]{mactpm+""});
    }
    public boolean thaydoitrangthai(int mactpm) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trasach", 1); // Cập nhật trạng thái trả sách thành 1
        int rowsAffected = sqLiteDatabase.update("CTPM", contentValues, "mactpm = ?", new String[]{String.valueOf(mactpm)});
        return rowsAffected > 0; // Kiểm tra xem có bản ghi nào được cập nhật không
    }
//    public ArrayList<CTPhieuMuon> getCTPM() {
//        ArrayList<CTPhieuMuon> list = new ArrayList<>();
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.query("CTPM", null, null, null, null, null, null);
//        if (cursor.moveToFirst()) {
//            do {
//                // Đọc dữ liệu từ cursor và thêm vào danh sách
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return list;
//    }

    }

