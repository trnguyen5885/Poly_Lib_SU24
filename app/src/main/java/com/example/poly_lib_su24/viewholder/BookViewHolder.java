package com.example.poly_lib_su24.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poly_lib_su24.R;

public class BookViewHolder extends RecyclerView.ViewHolder {
    public TextView txtBookTitle, txtPrice, txtType, txtID;
    public ImageView imgBook;
    public Button btnEdit, btnDelete;
    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        this.txtID = itemView.findViewById(R.id.txtIdBook);
        this.txtBookTitle = itemView.findViewById(R.id.txtBookName);
        this.txtPrice = itemView.findViewById(R.id.txtBookPrice);
        this.txtType = itemView.findViewById(R.id.txtBookType);
        this.btnEdit = itemView.findViewById(R.id.btnEdit);
        this.btnDelete = itemView.findViewById(R.id.btnDelete);
    }
}
