package com.example.duanmaunhom1.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.duanmaunhom1.DAO.ThanhVienDAO;
import com.example.duanmaunhom1.R;
import com.google.android.material.textfield.TextInputEditText;


public class ChangePassFragment extends Fragment {
    TextInputEditText edpass0ld,edpass,edrepass;
    Button btsave,bthuy;
//    ThanhVienDAO thanhVienDAO;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_change_pass, container, false);
        edpass0ld=v.findViewById(R.id.edPass0ld);
        edpass=v.findViewById(R.id.edPassChange);
        edrepass=v.findViewById(R.id.edRePassChange);
        btsave=v.findViewById(R.id.bntsave);
        bthuy=v.findViewById(R.id.bthuy);
        bthuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    edpass0ld.setText("");
                    edpass.setText("");
                    edrepass.setText("");
            }
        });
        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldpass = edpass0ld.getText().toString();
                String newpass = edpass.getText().toString();
                String repass = edrepass.getText().toString();
                if (newpass.equals(repass)){
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataUser",Context.MODE_PRIVATE);
                    String tendangnhap = sharedPreferences.getString("tendangnhap","");
                    //cap nhat
                    ThanhVienDAO thanhVienDAO = new ThanhVienDAO(getContext());
                    boolean check =thanhVienDAO.capnhatmatkhau(tendangnhap,oldpass,newpass);
                    if (check){
                        Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return v;
           }

        }