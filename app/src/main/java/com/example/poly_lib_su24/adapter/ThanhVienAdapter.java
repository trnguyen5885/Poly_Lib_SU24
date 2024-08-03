package com.example.poly_lib_su24.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poly_lib_su24.DAO.ThanhVienDAO;
import com.example.poly_lib_su24.R;
import com.example.poly_lib_su24.ThanhVienActivity;
import com.example.poly_lib_su24.model.ThanhVien;
import com.example.poly_lib_su24.viewholder.ThanhVienViewHolder;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienViewHolder> {
    private Context context;
    private ArrayList<ThanhVien> list;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
        this.thanhVienDAO = new ThanhVienDAO(context);
    }

    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thanhvien, parent, false);
        return new ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHolder holder, int position) {
        ThanhVien thanhVien = list.get(position);
        holder.tvmaThanhvien.setText(String.valueOf(thanhVien.getMathanhvien()));
        holder.tvtenThanhvien.setText(thanhVien.getTenthanhvien());

        // Giả sử bạn có ảnh trong thư mục drawable
        holder.imgThanhvien.setImageResource(R.drawable.kienthuc); // Thay thế bằng hình ảnh thực tế nếu có

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi hàm sửa thành viên từ activity
                if (context instanceof ThanhVienActivity) {
                    ((ThanhVienActivity) context).suaTenThanhVien(thanhVien);
                }
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Xóa Thành Viên")
                        .setMessage("Bạn có chắc chắn muốn xóa thành viên này?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                thanhVienDAO.delete(thanhVien.getMathanhvien());
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list.size());
                                Toast.makeText(context, "Xóa thành viên thành công", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
