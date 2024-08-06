package com.example.duanmaunhom1.DAO;

import static com.example.duanmaunhom1.EnterOTP.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duanmaunhom1.database.DbHelper;
import com.example.duanmaunhom1.model.NguoiDung;

public class NguoiDungDAO {
    private DbHelper dbHelper;
    private SharedPreferences sharedPreferences;

    public NguoiDungDAO(Context context) {
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("dataUser", Context.MODE_PRIVATE);
    }

    // Kiểm tra thông tin đăng nhập
    public boolean KiemTraDangNhap(String username, String password) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM NGUOIDUNG WHERE tendangnhap = ? AND matkhau = ?",
                new String[]{username, password}
        );


        boolean exists = cursor.getCount() > 0;
        if (exists) {
            cursor.moveToFirst();
            // Lấy role từ cursor
            int roleColumnIndex = cursor.getColumnIndex("role");
            if (roleColumnIndex != -1) {
                int role = cursor.getInt(roleColumnIndex);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("role", role);
                editor.putString("tendangnhap", username); // Lưu tên đăng nhập để sử dụng sau
                editor.apply();
            }
        }

        cursor.close(); // Đóng cursor
        return exists;
    }

    // Đăng ký tài khoản
    public boolean dangkytaikhoan(NguoiDung nguoiDung) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tennd", nguoiDung.getTennd());
        contentValues.put("sdt", nguoiDung.getSdt());
        contentValues.put("diachi", nguoiDung.getDiachi());
        contentValues.put("tendangnhap", nguoiDung.getTendangnhap());
        contentValues.put("matkhau", nguoiDung.getMatkhau());
        contentValues.put("role", nguoiDung.getRole()); // Gán đúng role từ đối tượng

        long check = sqLiteDatabase.insert("NGUOIDUNG", null, contentValues);
        return check != -1;
    }


    // Lấy role của người dùng theo tên đăng nhập
    public int getRoleByUser(String username) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT role FROM NGUOIDUNG WHERE tendangnhap = ?",
                new String[]{username}
        );

        int role = -1; // Giá trị mặc định nếu không tìm thấy
        if (cursor.moveToFirst()) {
            int roleColumnIndex = cursor.getColumnIndex("role");
            if (roleColumnIndex != -1) {
                role = cursor.getInt(roleColumnIndex);
            }
        }

        cursor.close(); // Đóng cursor
        return role;

    }

    @SuppressLint("Range")
    public String getUserNameByLogin(String loginName) {
        String userName = null;
        String selectQuery = "SELECT tennd FROM NGUOIDUNG WHERE tendangnhap = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{loginName});
        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndex("tennd"));
        } else {
            // Log nếu không tìm thấy kết quả
            Log.d("DbHelper", "Không tìm thấy người dùng với tên đăng nhập: " + loginName);
        }
        cursor.close();
        db.close();
        return userName;
    }

    private SQLiteDatabase getReadableDatabase() {
        return dbHelper.getReadableDatabase();
    }

    //    public boolean kiemtraMatkhau(String username, String sdt)  {
//        SQLiteDatabase sqLiteDatabase = null;
//        Cursor cursor = null;
//        boolean isValid = false;
//
//        try {
//            sqLiteDatabase = dbHelper.getReadableDatabase();
//            cursor = sqLiteDatabase.rawQuery(
//                    "SELECT * FROM NGUOIDUNG WHERE tendangnhap = ? AND matkhau = ?",
//                    new String[]{username, sdt}
//            );
//
//            if (cursor != null && cursor.moveToFirst()) {
//                isValid = true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (cursor != null) {
//                cursor.close(); // Đóng cursor
//            }
//            if (sqLiteDatabase != null) {
//                sqLiteDatabase.close(); // Đóng database
//            }
//        }
//
//        return isValid;
//    }
public boolean KiemTradmk(String username, String sdt) {
    SQLiteDatabase sqLiteDatabase = null;
    Cursor cursor = null;
    boolean exists = false;

    try {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM NGUOIDUNG WHERE tendangnhap = ? AND sdt = ?",
                new String[]{username, sdt}
        );

        if (cursor != null && cursor.moveToFirst()) {
            exists = true;
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (cursor != null) {
            cursor.close(); // Đóng cursor
        }
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close(); // Đóng database
        }
    } return exists;
}
    public boolean capNhatMatKhauMoi(String username, String matKhauMoi) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matkhau", matKhauMoi);

        int rowsAffected = sqLiteDatabase.update("NGUOIDUNG", contentValues, "tendangnhap = ?", new String[]{username});
        sqLiteDatabase.close();

        return rowsAffected > 0;
    }

    public boolean addNewUser(String user, String pass, String name, String diachi, String phoneNumber) {
        Log.d(TAG, "Adding new user with phone number: " + phoneNumber);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("tennd", name);
        values.put("sdt", phoneNumber);
        values.put("diachi", diachi);
        values.put("tendangnhap", user);
        values.put("matkhau", pass);
        values.put("role", 1);
        values.put("verified", 1);

        // Chèn người dùng mới vào bảng NGUOIDUNG
        long newRowId = db.insert("NGUOIDUNG", null, values);
        Log.d(TAG, "New row ID: " + newRowId);

        return newRowId != -1;
    }

    private SQLiteDatabase getWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }
}
