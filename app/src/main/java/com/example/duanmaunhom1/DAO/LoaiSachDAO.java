package com.example.duanmaunhom1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmaunhom1.database.DbHelper;
import com.example.duanmaunhom1.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachDAO {
    private DbHelper dbHelper;

    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Lấy danh sách loại sách
    public ArrayList<LoaiSach> getDSLoaiSach() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAISACH", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int maloai = cursor.getInt(cursor.getColumnIndexOrThrow("maloai"));
                String tenloai = cursor.getString(cursor.getColumnIndexOrThrow("tenloai"));
                LoaiSach loaiSach = new LoaiSach(maloai, tenloai);
                list.add(loaiSach);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    // Thêm loại sách
    public boolean themloaisach(String tenLoai) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenLoai);
        long check = sqLiteDatabase.insert("LOAISACH", null, contentValues);
        return check != -1;
    }

    // Sửa loại sách
    public boolean sualoaisach(LoaiSach loaiSach) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", loaiSach.getTenloai());
        int check = sqLiteDatabase.update("LOAISACH", contentValues, "maloai = ?", new String[]{String.valueOf(loaiSach.getMaloai())});
        return check != 0;
    }

    // Xóa loại sách
    public int xoaloaisach(int maloai) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        // Kiểm tra sự tồn tại của những cuốn sách trong bảng SACH với thể loại đang thực hiện xóa
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SACH WHERE maloai = ?", new String[]{String.valueOf(maloai)});
        if (cursor.getCount() > 0) {
            return 0; // Không được xóa
        } else {
            int check = sqLiteDatabase.delete("LOAISACH", "maloai = ?", new String[]{String.valueOf(maloai)});
            if (check == 0) {
                return -1; // Không xóa được
            } else {
                return 1; // Xóa được
            }
        }
    }

    // Tìm kiếm dữ liệu
//    public ArrayList<LoaiSach> searchData(String query) {
//        ArrayList<LoaiSach> list = new ArrayList<>();
//        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAISACH WHERE tenloai LIKE ?", new String[]{"%" + query + "%"});
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                int maloai = cursor.getInt(cursor.getColumnIndexOrThrow("maloai"));
//                String tenloai = cursor.getString(cursor.getColumnIndexOrThrow("tenloai"));
//                LoaiSach loaiSach = new LoaiSach(maloai, tenloai);
//                list.add(loaiSach);
//            } while (cursor.moveToNext());
//            cursor.close();
//        }
//        return list;
//    }
}
