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

import com.example.duanmaunhom1.DAO.SachDAO;
import com.example.duanmaunhom1.R;
import com.example.duanmaunhom1.adapter.SachAdapter;
import com.example.duanmaunhom1.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class sachfragment extends Fragment {
    private RecyclerView recyclerViewSach;
    private SachDAO sachDAO;
    private ArrayList<Sach> sachArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sachfragment, container, false);
        recyclerViewSach = v.findViewById(R.id.recyclerViewqlSach);
        FloatingActionButton floadd = v.findViewById(R.id.themmoi);

        floadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hien thi dailog them sach
                showdailogthem();
            }
        });

        sachDAO = new SachDAO(getContext());
        loadSach();
        return v;
    }


    private void loadSach() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewSach.setLayoutManager(linearLayoutManager);
        SachAdapter adapter = new SachAdapter(getContext(), sachDAO.getDSSach(),sachDAO);
        recyclerViewSach.setAdapter(adapter);
    }
    public void showdailogthem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_sach,null);
        builder.setView(view);

        AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        // anh xa
        EditText edtTenSach = view.findViewById(R.id.ettensach);
        EditText edTacGia = view.findViewById(R.id.ettacgia);
        EditText edtTienthue = view.findViewById(R.id.ettienthue);
        EditText edttheloai = view.findViewById(R.id.ettenloai);
        Button btnluu = view.findViewById(R.id.btluu);
        Button btnHuy = view.findViewById(R.id.bthuy);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edtTenSach.getText().toString();
                if (tenSach.equals("")){
                    Toast.makeText(getContext(), "Chưa nhập tên sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tacGia = edTacGia.getText().toString();
                if (tacGia.equals("")){
                    Toast.makeText(getContext(), "Chưa nhập tên tác giả", Toast.LENGTH_SHORT).show();
                }
                int tienThue = Integer.parseInt(edtTienthue.getText().toString());

                int theLoai =  Integer.parseInt(edttheloai.getText().toString());



                boolean check = sachDAO.themSach(tenSach,tacGia,tienThue,theLoai);

                if (check){
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    loadSach();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


}
}
