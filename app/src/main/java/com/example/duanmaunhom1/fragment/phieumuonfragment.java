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

import com.example.duanmaunhom1.DAO.PhieuMuonDAO;
import com.example.duanmaunhom1.R;
import com.example.duanmaunhom1.adapter.PhieuMuonAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class phieumuonfragment extends Fragment {
    private RecyclerView recyclerViewphieumuon;
    private PhieuMuonDAO phieuMuonDAO;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.phieumuonfragment, container, false);

        recyclerViewphieumuon=v.findViewById(R.id.recyclerViewqlpm);
        FloatingActionButton floadd =v.findViewById(R.id.themmoi);

        phieuMuonDAO=new PhieuMuonDAO(getContext());
        loadqlphiuemuon();
        floadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdailogpm();
            }
        });


        return v;

    }
    private void loadqlphiuemuon() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewphieumuon.setLayoutManager(linearLayoutManager);
        PhieuMuonAdapter adapter = new PhieuMuonAdapter(getContext(),phieuMuonDAO.getDSPhieuMuon(),phieuMuonDAO);
        recyclerViewphieumuon.setAdapter(adapter);
    }
    public  void showdailogpm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dailog_phieumuon,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        // anh xa
        EditText etngaymuon = view.findViewById(R.id.etngaymuon);
        EditText etngaytra = view.findViewById(R.id.etngaytra);
        EditText etmanguoimuon = view.findViewById(R.id.etnguoimuon);
        Button btluu = view.findViewById(R.id.btluu);
        Button bthuy = view.findViewById(R.id.bthuy);

        btluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngaymuon = etngaymuon.getText().toString();
                String ngaytra = etngaytra.getText().toString();
                int maNguoiMuon = Integer.parseInt(etmanguoimuon.getText().toString());


                boolean check = phieuMuonDAO.thempm(ngaymuon,ngaytra,maNguoiMuon);

                if (check){
                    Toast.makeText(getContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                    loadqlphiuemuon();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "Them that bai", Toast.LENGTH_SHORT).show();
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
