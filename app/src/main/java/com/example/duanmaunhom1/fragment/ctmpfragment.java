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

import com.example.duanmaunhom1.DAO.ChiTietpmDAO;
import com.example.duanmaunhom1.R;
import com.example.duanmaunhom1.adapter.CTPMAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ctmpfragment extends Fragment {
    private RecyclerView recyclerCTPM;
    private ChiTietpmDAO CTPMDAO;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ctpmfragment, container, false);
        recyclerCTPM = v.findViewById(R.id.recyclerCTPM);
        FloatingActionButton floatadd = v.findViewById(R.id.themmoi);
        CTPMDAO = new ChiTietpmDAO(getContext());
        loadCTPM();
        floatadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdailogctpt();
            }
        });
        return v;

    }
    private void loadCTPM() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerCTPM.setLayoutManager(linearLayoutManager);
        CTPMAdapter adapter = new CTPMAdapter(getContext(), CTPMDAO.getCTPM(), CTPMDAO);
        recyclerCTPM.setAdapter(adapter);
    }
    public void showdailogctpt(){
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_ctpm,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();
        EditText etmapm = view.findViewById(R.id.etmapm);
        EditText etmasach = view.findViewById(R.id.etmasach);
        EditText etsoluong = view.findViewById(R.id.etsoluong);
        Button btnluu = view.findViewById(R.id.btluu);
        Button btnhuy = view.findViewById(R.id.bthuy);
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mapm = Integer.parseInt(etmapm.getText().toString());
                int masach = Integer.parseInt(etmasach.getText().toString());
                int soluong = Integer.parseInt(etsoluong.getText().toString());
                // check


                boolean check = CTPMDAO.themctpm(mapm,masach,soluong);

                if (check){
                    Toast.makeText(getContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                    loadCTPM();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "Them that bai", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
}
