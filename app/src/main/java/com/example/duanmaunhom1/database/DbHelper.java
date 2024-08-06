package com.example.duanmaunhom1.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, "QUANLYTHUVIEN", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng và chèn dữ liệu mẫu
        String tLoaiSach = "create table LOAISACH( maloai integer primary key autoincrement, tenloai text)";
        db.execSQL(tLoaiSach);
        db.execSQL("insert into LOAISACH values (1,'Kinh Tế'), (2,'Văn Học'), (3,'Lịch Sử'), (4,'Toán Học'), (5,'Khoa Học'), (6,'Tâm Lý'), (7,'Triết Học'), (9,'Công Nghệ'), (10,'Nghệ Thuật'), (11,'Giáo Dục'), (12,'Du Lịch')");

        String tSach = "create table SACH(masach integer primary key autoincrement, tensach text, tacgia text, giaban integer, maloai integer references LOAISACH(maloai))";
        db.execSQL(tSach);
        db.execSQL("insert into SACH values " +
                    "(1,'Khởi nghiệp', 'Bùi Công Trứ', 15000, 1), " +
                    "(2,'Trạng Quỳnh', 'Nguyễn Du', 5000, 1), " +
                    "(3,'Tư Duy Nhanh và Chậm', 'Daniel Kahneman', 200000, 4), " +
                    "(4,'Đắc Nhân Tâm', 'Dale Carnegie', 120000, 3), " +
                    "(5,'Lập Trình Java', 'James Gosling', 250000, 5), " +
                    "(6,'Tài Chính Cá Nhân', 'Robert Kiyosaki', 150000, 1), " +
                    "(7,'Lịch Sử Thế Giới', 'Yuval Noah Harari', 300000, 2), " +
                    "(8,'Thiên Văn Học', 'Carl Sagan', 180000, 6), " +
                    "(9,'Tâm Lý Học', 'Sigmund Freud', 220000, 3), " +
                    "(10,'Nghệ Thuật Lãnh Đạo', 'John Maxwell', 210000, 7)");

        String tNguoidung = "create table NGUOIDUNG(mand integer primary key autoincrement, tennd text, sdt text, diachi text, tendangnhap text, matkhau text, role integer, verified integer)";
        db.execSQL(tNguoidung);
        db.execSQL("insert into NGUOIDUNG values (1,'User','0987654321','Hà Nội','user','123456',1,1)," +
                "(2,'Thủ Thư','0987654521','TPhcm','thuthu','123456',2,1)," +
                "(3,'Admin','0987673112','Đà nẳng','admin','123456',3,1)");

        String tPhieuMuon = "create table PHIEUMUON(mapm integer primary key autoincrement, ngaymuon text, ngaytra text, mand integer references NGUOIDUNG(mand))";
        db.execSQL(tPhieuMuon);
        db.execSQL("insert into PHIEUMUON values " +
                "(1,'20/09/2023', '21/09/2024', 1), " +
                "(2,'20/09/2023', '21/10/2024', 2), " +
                "(3,'20/11/2023', '21/12/2024', 3), " +
                "(4,'22/09/2023', '23/09/2024', 1), " +
                "(5,'22/10/2023', '23/11/2024', 2), " +
                "(6,'22/11/2023', '23/12/2024', 3), " +
                "(7,'22/12/2023', '23/01/2024', 1), " +
                "(8,'22/01/2024', '23/02/2024', 2), " +
                "(9,'22/02/2024', '23/03/2024', 3), " +
                "(10,'22/03/2024', '23/04/2024', 1)");

        String tCTPM = "create table CTPM(mactpm integer primary key autoincrement, mapm integer references PHIEUMUON(mapm), masach integer references SACH(masach), soluong integer,trasach integer)";
        db.execSQL(tCTPM);
        db.execSQL("insert into CTPM values" +
                "(1,1, 1, 2, 0), " +
                "(2,2, 2, 1, 1), " +
                "(3,3, 3, 3, 1), " +
                "(4,4, 4, 1, 0), " +
                "(5,5, 5, 2, 1), " +
                "(6,6, 6, 1, 0), " +
                "(7,7, 7, 3, 1), " +
                "(8,8, 8, 1, 0), " +
                "(9,9, 9, 2, 1), " +
                "(10,10, 10, 1, 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("drop table if exists LOAISACH");
            db.execSQL("drop table if exists SACH");
            db.execSQL("drop table if exists NGUOIDUNG");
            db.execSQL("drop table if exists PHIEUMUON");
            db.execSQL("drop table if exists CTPM");
            onCreate(db);
        }
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

}
