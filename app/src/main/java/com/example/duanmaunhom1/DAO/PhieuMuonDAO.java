package com.example.duanmaunhom1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmaunhom1.database.DbHelper;
import com.example.duanmaunhom1.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonDAO {
    private DbHelper dbHelper;

        public PhieuMuonDAO(Context context) {
            dbHelper = new DbHelper(context);
        }
    // LAY PHIEU MUON CAC PHIEU MUON
    public ArrayList<PhieuMuon> getDSPhieuMuon(){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        //SELECT p.mapm, p.ngaymuon, p.ngaytra, p.mand, n.tennd  FROM PHIEUMUON p , NGUOIDUNG n WHERE p.mand = n.mand

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT p.mapm, p.ngaymuon, p.ngaytra, p.mand, n.tennd  FROM PHIEUMUON p , NGUOIDUNG n WHERE p.mand = n.mand",null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuon(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4)));
            }while (cursor.moveToNext());

        }

        return list;
    }
    public boolean thempm(String ngaymuon, String ngaytra,  int mand){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ngaymuon",ngaymuon);
        contentValues.put("ngaytra",ngaytra);
        contentValues.put("mand",mand);


        long check = sqLiteDatabase.insert("PHIEUMUON",null,contentValues);
//        if (check == -1) {
//            return false;
//        }else {
//            return true;
//        }
        return check != -1;
    }

    public boolean suaphiuemuon(PhieuMuon phieuMuon){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put("ngaymuon",phieuMuon.getNgaymuon());
        contentValues.put("ngaytra",phieuMuon.getNgaytra());
        contentValues.put("mand",phieuMuon.getMand());


        int check = sqLiteDatabase.update("PHIEUMUON",contentValues,"mapm = ?",new String[]{String.valueOf(phieuMuon.getMapm())});
        return check != 0;


    }
    public int xoapm(int mapm) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        // kiem tra su ton tai cua nhung cuon sach trong bang sach voi the loai dang thuc hien xoa
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CTPM WHERE mapm = ?", new String[]{String.valueOf(mapm)});
        if (cursor.getCount() > 0) {
            return 0;
        } else {
            int check = sqLiteDatabase.delete("PHIEUMUON", "mapm = ?", new String[]{String.valueOf(mapm)});
            if (check == 0) {
                return 1;//khong xoa duoc

            } else {
                return 1;//xoa duoc
            }

        }

    }

}
