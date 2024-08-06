package com.example.duanmaunhom1.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmaunhom1.DAO.ThanhVienDAO;
import com.example.duanmaunhom1.R;
import com.example.duanmaunhom1.model.NguoiDung;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {
    private Context context;
    private ArrayList<NguoiDung> list;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<NguoiDung> list, ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.list = list;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thanhvien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMand.setText("ID: " + list.get(position).getMand());
        holder.txtTenND.setText("Tên: " + list.get(position).getTennd());
        holder.txtsdt.setText("Số điện thoại: " + list.get(position).getSdt());
        holder.txtDiaChi.setText("Địa chỉ: " + list.get(position).getDiachi());
        holder.txtTenDangNhap.setText("Tên đăng nhập: " + list.get(position).getTendangnhap());
        holder.txtMatKhau.setText("Mật khẩu: " + list.get(position).getMatkhau());
        holder.txtrole.setText("Vai trò: " + list.get(position).getRole());
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdailogupdate(list.get(holder.getAdapterPosition()));
            }
        });
        holder.ivDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xóa thành viên " + list.get(holder.getAdapterPosition()).getTennd() + " Không?");
                builder.setIcon(R.drawable.canhbao1);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int mand = list.get(position).getMand();
                thanhVienDAO.xoathanhvien(mand);
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                loaddata();
                    }
                });
                builder.setNegativeButton("Không", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
//                int mand = list.get(position).getMand();
//                thanhVienDAO.xoathanhvien(mand);
//                Toast.makeText(context, "xoa thanh cong", Toast.LENGTH_SHORT).show();
//                loaddata();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMand, txtTenND, txtsdt, txtDiaChi, txtTenDangNhap, txtMatKhau, txtrole;
        ImageView ivEdit, ivDele;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMand = itemView.findViewById(R.id.txtMand);
            txtTenND = itemView.findViewById(R.id.txtTenND);
            txtsdt = itemView.findViewById(R.id.txtsdt);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            txtTenDangNhap = itemView.findViewById(R.id.txtTenDangNhap);
            txtMatKhau = itemView.findViewById(R.id.txtMatKhau);
            txtrole = itemView.findViewById(R.id.txtrole);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDele = itemView.findViewById(R.id.ivDele);
        }
    }

    public void showdailogupdate(NguoiDung nd){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_thanhvien, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();
        TextView tvtitle = view.findViewById(R.id.txttieude);
        EditText edtten = view.findViewById(R.id.ettennd);
        EditText etsdt = view.findViewById(R.id.etsdt);
        EditText etdiachi = view.findViewById(R.id.etdiachi);
        EditText ettendangnhap = view.findViewById(R.id.ettendangnhap);
        EditText etmatkhau = view.findViewById(R.id.etmatkhau);
        EditText etvaitro = view.findViewById(R.id.etvaitro);
        Button btluu = view.findViewById(R.id.btluu);
        Button bthuy = view.findViewById(R.id.bthuy);

        tvtitle.setText("Sửa Thanh vien");
        btluu.setText("cap nhat");
        edtten.setText(nd.getTennd());
        etsdt.setText(nd.getSdt());
        etdiachi.setText(nd.getDiachi());
        ettendangnhap.setText(nd.getTendangnhap());
        etmatkhau.setText(nd.getMatkhau());
        etvaitro.setText(String.valueOf(nd.getRole()));

        btluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tennd = edtten.getText().toString();
//                if (tennd.equals("")){
//                    Toast.makeText(ThanhVienActivity.this, "Nhap ten thanh vien", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                String sdt = etsdt.getText().toString();
//                if (sdt.equals("")){
//                    Toast.makeText(ThanhVienActivity.this, "Nhap so dien thoai", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                String diachi = etdiachi.getText().toString();
//                if (diachi.equals("")){
//                    Toast.makeText(ThanhVienActivity.this, "Nhap dia chi ", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                String tendangnhap = ettendangnhap.getText().toString();
//                if (tendangnhap.equals("")){
//                    Toast.makeText(ThanhVienActivity.this, "Nhap ten dang nhap", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                String matkhau = etmatkhau.getText().toString();
//                if (matkhau.equals("")){
//                    Toast.makeText(ThanhVienActivity.this, "nhap mat khau", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                int vaitro = Integer.parseInt(etvaitro.getText().toString());

                NguoiDung nguoiDungupdate = new NguoiDung(nd.getMand(), tennd, sdt, diachi, tendangnhap, matkhau, vaitro, false);
                boolean check = thanhVienDAO.suathanhvien(nguoiDungupdate);
                if (check){
                    Toast.makeText(context, "cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                    loaddata();
                    alertDialog.dismiss();
                }else {
                    Toast.makeText(context, "cap nhat that bai", Toast.LENGTH_SHORT).show();
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
public  void loaddata(){
        list.clear();
        list = thanhVienDAO.getDSNguoidung();
        notifyDataSetChanged();
}
}
