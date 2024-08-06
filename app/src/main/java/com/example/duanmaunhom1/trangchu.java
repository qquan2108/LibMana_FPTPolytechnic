package com.example.duanmaunhom1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.duanmaunhom1.DAO.NguoiDungDAO;
import com.example.duanmaunhom1.database.DbHelper;
import com.example.duanmaunhom1.fragment.loaisachfragment;

public class trangchu extends AppCompatActivity {
    LinearLayout qlsach, qlloaisach, qlthanhvien, qlphieumuon, thongke, ctpm, docsach;
    TextView txtXinChao;
    DbHelper db;
    Button dangxuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);

        // Khởi tạo NguoiDungDAO
        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(this);

        docsach = findViewById(R.id.docsach);
        txtXinChao = findViewById(R.id.txinchao);
        qlsach = findViewById(R.id.qlsach);
        qlloaisach = findViewById(R.id.qlloaisach);
        qlthanhvien = findViewById(R.id.qlthanhvien);
        qlphieumuon = findViewById(R.id.qlphieumuon);
        thongke = findViewById(R.id.thongke);
        dangxuat = findViewById(R.id.dangxuat);
        ctpm = findViewById(R.id.CTPM);

        // Lấy role và tên đăng nhập đã lưu
        SharedPreferences sharedPreferences = getSharedPreferences("dataUser", MODE_PRIVATE);
        int role = sharedPreferences.getInt("role", -1);
        String loginName = sharedPreferences.getString("tendangnhap", "");

        Log.d("trangchu", "Tên đăng nhập: " + loginName);

        switch (role) {
            case 1:
                ctpm.setVisibility(View.GONE);
                qlphieumuon.setVisibility(View.GONE);
                qlloaisach.setVisibility(View.GONE);
                qlsach.setVisibility(View.GONE);
                qlthanhvien.setVisibility(View.GONE);
                thongke.setVisibility(View.GONE);
                break;
            case 2: // thủ thư
                qlthanhvien.setVisibility(View.GONE);
                thongke.setVisibility(View.GONE);
                break;
            case 3: // admin
                break;
            default:
                qlloaisach.setVisibility(View.GONE);
                qlsach.setVisibility(View.GONE);
                qlthanhvien.setVisibility(View.GONE);
                thongke.setVisibility(View.GONE);
                qlphieumuon.setVisibility(View.GONE);
                break;
        }

        // Lấy tên người dùng từ database theo tên đăng nhập
        String userName = nguoiDungDAO.getUserNameByLogin(loginName);
        if (userName != null) {
            txtXinChao.setText("Xin chào, " + userName);
        } else {
            txtXinChao.setText("Không tìm thấy người dùng");
        }

        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(trangchu.this, DangNhap.class);
                startActivity(i);
            }
        });
        qlsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(trangchu.this, SachActivity.class);
                startActivity(i);
            }
        });

        qlloaisach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(trangchu.this, LoaiSachActivity.class);
                startActivity(i);
            }
        });

        qlphieumuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(trangchu.this, PhieuMuonMainActivity.class);
                startActivity(i);
            }
        });

        qlthanhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(trangchu.this, ThanhVienActivity.class);
                startActivity(i);
            }
        });

        thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(trangchu.this, ThongKeActivity.class);
                startActivity(i);
            }
        });
        ctpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(trangchu.this, CTPM.class);
                startActivity(i);
            }
        });
        docsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(trangchu.this, DocSach.class);
                startActivity(i);
            }
        });
    }
}
