package com.example.poly_lib_su24.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poly_lib_su24.R;

public class ThanhVienViewHolder extends RecyclerView.ViewHolder {
    public TextView tvmaThanhvien, tvtenThanhvien;
    public Button btnDelete, btnEdit;
    public ImageView imgThanhvien;

    public ThanhVienViewHolder(@NonNull View itemView) {
        super(itemView);
        this.tvmaThanhvien = itemView.findViewById(R.id.tvmaThanhvien);
        this.tvtenThanhvien = itemView.findViewById(R.id.tvtenThanhvien);
        this.btnDelete = itemView.findViewById(R.id.btnDelete);
        this.btnEdit = itemView.findViewById(R.id.btnEdit);
        this.imgThanhvien = itemView.findViewById(R.id.imgThanhvien);
    }
}
