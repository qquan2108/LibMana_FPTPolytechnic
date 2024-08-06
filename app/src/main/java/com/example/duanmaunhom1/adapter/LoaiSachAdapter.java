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

import com.example.duanmaunhom1.DAO.LoaiSachDAO;
import com.example.duanmaunhom1.R;
import com.example.duanmaunhom1.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiSach> list;
    private LoaiSachDAO loaiSachDAO;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list, LoaiSachDAO loaiSachDAO) {
        this.context = context;
        this.list = list;
        this.loaiSachDAO = loaiSachDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_qlsach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaLoai.setText("ID: " + list.get(position).getMaloai());
        holder.txtTenLoai.setText(list.get(position).getTenloai());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc muốn xóa loại sách " + list.get(holder.getAdapterPosition()).getTenloai() + " không?");
                builder.setIcon(R.drawable.canhbao1);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int check = loaiSachDAO.xoaloaisach(list.get(holder.getAdapterPosition()).getMaloai());
                        switch (check) {
                            case -1:
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Bạn cần xóa các cuốn sách trong thể loại trước", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                loadLoaiSach();
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Không", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaLoai, txtTenLoai;
        ImageView ivEdit, ivDele;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDele = itemView.findViewById(R.id.ivDele);
        }
    }

    public void showDialogUpdate(LoaiSach loaiSach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        View view = inflater.inflate(R.layout.dailog_loaisach, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.setCancelable(false);
        alertDialog.show();

        TextView txttieude = view.findViewById(R.id.txttieude);
        EditText edtTenLoai = view.findViewById(R.id.ettenloai);
        Button btnLuu = view.findViewById(R.id.btluu);
        Button btnHuy = view.findViewById(R.id.bthuy);

        txttieude.setText("Cập nhật loại sách");
        btnLuu.setText("Cập nhật");
        edtTenLoai.setText(loaiSach.getTenloai());

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edtTenLoai.getText().toString();
                LoaiSach loaiSachUpdate = new LoaiSach(loaiSach.getMaloai(), tenLoai);
                boolean check = loaiSachDAO.sualoaisach(loaiSachUpdate);
                if (check) {
                    Toast.makeText(context, "Sửa loại sách thành công", Toast.LENGTH_SHORT).show();
                    loadLoaiSach();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(context, "Sửa loại sách thất bại", Toast.LENGTH_SHORT).show();
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

    private void loadLoaiSach() {
        list.clear();
        list.addAll(loaiSachDAO.getDSLoaiSach());
        notifyDataSetChanged();
    }
}
