package com.example.poly_lib_su24.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poly_lib_su24.R;

public class BookTypeViewHolder extends RecyclerView.ViewHolder {
    public TextView txtID,txtName;
    public Button btnDelete, btnEdit;

    public BookTypeViewHolder(@NonNull View itemView) {
        super(itemView);
        this.txtID = itemView.findViewById(R.id.txtIDBookType);
        this.txtName = itemView.findViewById(R.id.txtNameBookType);
        this.btnDelete = itemView.findViewById(R.id.btnDelete);
        this.btnEdit = itemView.findViewById(R.id.btnEdit);
    }
}
