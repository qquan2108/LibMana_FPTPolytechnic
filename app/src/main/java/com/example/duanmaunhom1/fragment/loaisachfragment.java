package com.example.duanmaunhom1.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaunhom1.DAO.LoaiSachDAO;
import com.example.duanmaunhom1.R;
import com.example.duanmaunhom1.adapter.LoaiSachAdapter;
import com.example.duanmaunhom1.database.DbHelper;
import com.example.duanmaunhom1.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class loaisachfragment extends Fragment {
    private LoaiSachDAO loaiSachDAO;
    private DbHelper dbHelper;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private RecyclerView recyclerViewLoaiSach;
    private ArrayList<LoaiSach> list;
    private RelativeLayout main;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.loaisachfragment, container, false);
        Toolbar toolbar = v.findViewById(R.id.toolbar);


        // Setup ListView and Adapter

        // Setup RecyclerView
        recyclerViewLoaiSach = v.findViewById(R.id.recyclerViewqlloaisach);
        FloatingActionButton floatadd = v.findViewById(R.id.themmoi);
        main = v.findViewById(R.id.main);

        dbHelper = new DbHelper(getContext());
        loaiSachDAO = new LoaiSachDAO(getContext());

        // Setup FloatingActionButton
        floatadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogthem();
            }
        });

        // Load data into RecyclerView
        capnhapdulieu();

        return v;

    }
    private void capnhapdulieu() {
        list = loaiSachDAO.getDSLoaiSach();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewLoaiSach.setLayoutManager(linearLayoutManager);
        LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(getContext(), list, loaiSachDAO);
        recyclerViewLoaiSach.setAdapter(loaiSachAdapter);
    }
    private void showDialogthem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_loaisach, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        // Bind UI elements
        EditText edttenloaisach = view.findViewById(R.id.ettenloai);
        Button btnluu = view.findViewById(R.id.btluu);
        Button btnHuy = view.findViewById(R.id.bthuy);

        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edttenloaisach.getText().toString();
                if (tenLoai.equals("")) {
                    Toast.makeText(getContext(), "Nhập đủ dữ liệu !!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean check = loaiSachDAO.themloaisach(tenLoai);
                if (check) {
                    shownote(main, "Thêm Thành công");
                    capnhapdulieu();
                    alertDialog.dismiss();
                } else {
                    shownote(main, "Thêm Thất Bại");
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
    private void shownote(View view, String mesage) {
        Snackbar.make(view, mesage, Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

}
