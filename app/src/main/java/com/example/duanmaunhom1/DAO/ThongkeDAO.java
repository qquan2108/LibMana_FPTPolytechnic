package com.example.duanmaunhom1.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmaunhom1.database.DbHelper;

public class ThongkeDAO {
    DbHelper dbHelper;
    public ThongkeDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public int getDoanhThu(String ngaybatdau, String ngayketthuc){
        ngaybatdau = ngaybatdau.replace("/","");
        ngayketthuc = ngayketthuc.replace("/","");
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select sum(mand) from PHIEUMUON where substr(ngaytra,7) || substr(ngaytra,4,2) || substr(ngaytra,1,2) between ? and ?",new String[]{ngaybatdau,ngayketthuc});
        if (cursor.getCount() !=0){
            cursor.moveToFirst();
            return cursor.getInt(0);

        }
        return 0;
    }
}
