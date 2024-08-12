package com.example.duanmaunhom1.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaunhom1.DAO.ThanhVienDAO;
import com.example.duanmaunhom1.R;
import com.example.duanmaunhom1.adapter.ThanhVienAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class thanhvienfragment extends Fragment {
    private RecyclerView recyclerViewqlthanhvien;
    private ThanhVienDAO thanhVienDAO;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.thanhvienfragment, container, false);
        recyclerViewqlthanhvien = v.findViewById(R.id.recyclerViewqlthanhvien);
        FloatingActionButton floadd = v.findViewById(R.id.themmoi);
        floadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdailog();
            }
        });

        thanhVienDAO = new ThanhVienDAO(getContext());
        loathanhvien();


        return v;

    }
    private void loathanhvien() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewqlthanhvien.setLayoutManager(linearLayoutManager);
        ThanhVienAdapter adapter = new ThanhVienAdapter(getContext(), thanhVienDAO.getDSNguoidung(),thanhVienDAO);
        recyclerViewqlthanhvien.setAdapter(adapter);
    }
    public void showdailog( ){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_thanhvien,null);
        builder.setView(view);


        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        // anh xa
        EditText edtten = view.findViewById(R.id.ettennd);
        EditText etsdt = view.findViewById(R.id.etsdt);
        EditText etdiachi = view.findViewById(R.id.etdiachi);
        EditText ettendangnhap = view.findViewById(R.id.ettendangnhap);
        EditText etmatkhau = view.findViewById(R.id.etmatkhau);
        EditText etvaitro = view.findViewById(R.id.etvaitro);
        Button btluu = view.findViewById(R.id.btluu);
        Button bthuy = view.findViewById(R.id.bthuy);

        btluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tennd = edtten.getText().toString();
                if (tennd.equals("")){
                    Toast.makeText(getContext(), "Chưa nhập tên thành viên", Toast.LENGTH_SHORT).show();
                    return;
                }
                String sdt = etsdt.getText().toString();
                if (sdt.equals("")){
                    Toast.makeText(getContext(), "Chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    return;
                }
                String diachi = etdiachi.getText().toString();
                if (diachi.equals("")){
                    Toast.makeText(getContext(), "Chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tendangnhap = ettendangnhap.getText().toString();
                if (tendangnhap.equals("")){
                    Toast.makeText(getContext(), "Chưa nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                    return;
                }
                String matkhau = etmatkhau.getText().toString();
                if (matkhau.equals("")){
                    Toast.makeText(getContext(), "Chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                int vaitro = Integer.parseInt(etvaitro.getText().toString());


                boolean check = thanhVienDAO.themthanhvien(tennd,sdt,diachi,tendangnhap,matkhau,vaitro);

                if (check){
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loathanhvien();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
        bthuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


    }
}
