package com.example.duanmaunhom1.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duanmaunhom1.CTPM;
import com.example.duanmaunhom1.DAO.NguoiDungDAO;
import com.example.duanmaunhom1.DangNhap;
import com.example.duanmaunhom1.DocSach;
import com.example.duanmaunhom1.LoaiSachActivity;
import com.example.duanmaunhom1.PhieuMuonMainActivity;
import com.example.duanmaunhom1.R;
import com.example.duanmaunhom1.SachActivity;
import com.example.duanmaunhom1.ThanhVienActivity;
import com.example.duanmaunhom1.ThongKeActivity;
import com.example.duanmaunhom1.database.DbHelper;

public class trangchufragment1 extends Fragment {
    LinearLayout qlsach, qlloaisach, qlthanhvien, qlphieumuon, thongke, ctpm, docsach;
    TextView txtXinChao;
    DbHelper db;
    Button dangxuat;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.trangchufragment1, container, false);

        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(getContext());

        docsach = v.findViewById(R.id.docsach);
        txtXinChao = v.findViewById(R.id.txinchao);
        qlsach = v.findViewById(R.id.qlsach);
        qlloaisach = v.findViewById(R.id.qlloaisach);
        qlthanhvien = v.findViewById(R.id.qlthanhvien);
        qlphieumuon = v.findViewById(R.id.qlphieumuon);
        thongke = v.findViewById(R.id.thongke);
        dangxuat = v.findViewById(R.id.dangxuat);
        ctpm = v.findViewById(R.id.CTPM);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataUser", MODE_PRIVATE);
        int role = sharedPreferences.getInt("role", -1);
        String loginName = sharedPreferences.getString("tendangnhap", "");

        Log.d("trangchu", "Tên đăng nhập: " + loginName);

        switch (role) {
            case 1:
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
        String userName = nguoiDungDAO.getUserNameByLogin(loginName);
        if (userName != null) {
            txtXinChao.setText("Xin chào, " + userName);
        } else {
            txtXinChao.setText("Không tìm thấy người dùng");
        }

      dangxuat.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i = new Intent(getContext(), DangNhap.class);
              startActivity(i);
          }

      });
        qlsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SachActivity.class);
                startActivity(i);
            }
        });

        qlloaisach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LoaiSachActivity.class);
                startActivity(i);
            }
        });

        qlphieumuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PhieuMuonMainActivity.class);
                startActivity(i);
            }
        });

        qlthanhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ThanhVienActivity.class);
                startActivity(i);
            }
        });

        thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ThongKeActivity.class);
                startActivity(i);
            }
        });
        ctpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), CTPM.class);
                startActivity(i);
            }
        });
        docsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DocSach.class);
                startActivity(i);
            }
        });




        return v;

    }
}
