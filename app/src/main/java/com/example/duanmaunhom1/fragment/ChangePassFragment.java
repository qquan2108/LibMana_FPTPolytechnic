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
                        Toast.makeText(getContext(), "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Nhap mat khau khong trung nhau", Toast.LENGTH_SHORT).show();
                }

            }
        });



//        btsave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences pred = getActivity().getSharedPreferences("User_file", Context.MODE_PRIVATE);
//                String pass0ld = pred.getString("PASSWORD","");
//                if (validate()>0){
//                    ThanhVien thanhVien = thanhVienDAO.getID(user);
//                    thanhVien.setMatkhau(edpass.getText().toString());
//
//                    thanhVienDAO.updatepass(thanhVien);
//                }
//            }
//        });
        return v;
           }

//            public int validate() {
//                int check = 1;
//                if (edpass0ld.getText().length() == 0 || edpass.getText().length() == 0 || edrepass.getText().length() == 0) {
//                    Toast.makeText(getContext(), "Ban phai nhap day du thong tin", Toast.LENGTH_SHORT).show();
//                    check = -1;
//                } else {
//                    SharedPreferences pred = getActivity().getSharedPreferences("User_file", Context.MODE_PRIVATE);
//                    String pass0ld = pred.getString("PASSWORD", "");
//                    String pass = edpass.getText().toString();
//                    String repass = edrepass.getText().toString();
//                    if (!pass0ld.equals(edpass0ld.getText().toString())) {
//                        Toast.makeText(getContext(), "Mat khau cu sai ", Toast.LENGTH_SHORT).show();
//                        check = -1;
//                    }
//                    if (!pass.equals(repass)) {
//                        Toast.makeText(getContext(), "Mat khau khong trung nhau", Toast.LENGTH_SHORT).show();
//                        check = -1;
//                    }
//                }
//                return check;
//            }
        }