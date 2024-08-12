package com.example.duanmaunhom1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmaunhom1.database.DbHelper;
import com.example.duanmaunhom1.model.Sach;

import java.util.ArrayList;

public class SachDAO {
    private DbHelper dbHelper;
    public SachDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Sach> getDSSach(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT s.masach, s.tensach, s.tacgia, s.giaban, s.maloai, l.tenloai  FROM SACH s , LOAISACH l WHERE s.maloai = l.maloai",null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5)));
            }while (cursor.moveToNext());

        }

        return list;
    }
    public boolean themSach(String tensach, String tacgia, int giaban, int maloai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach",tensach);
        contentValues.put("tacgia",tacgia);
        contentValues.put("giaban",giaban);
        contentValues.put("maloai",maloai);

        long check = sqLiteDatabase.insert("SACH",null,contentValues);
//        if (check == -1) {
//            return false;
//        }else {
//            return true;
//        }
        return check != -1;
    }
    public boolean suasach(Sach sach){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("tensach",sach.getTensach());
        contentValues.put("tacgia",sach.getTacgia());
        contentValues.put("giaban",sach.getGiaban());
        contentValues.put("maloai",sach.getMaloai());

        int check = sqLiteDatabase.update("SACH",contentValues,"masach = ?",new String[]{sach.getMasach()+""});
       return check != 0;


    }
    public int xoassach(int masach){
        SQLiteDatabase sqLiteDatabase= dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CTPM WHERE masach = ?",new String[]{String.valueOf(masach)});
        if (cursor.getCount() > 0){
            return 0;
        }else {
            int check = sqLiteDatabase.delete("SACH","masach = ?", new String[]{String.valueOf(masach)});
            if (check == 0){
                return 1 ;//khong xoa duoc

            }else {
                return 1 ;//xoa duoc
            }

        }
    }

}
