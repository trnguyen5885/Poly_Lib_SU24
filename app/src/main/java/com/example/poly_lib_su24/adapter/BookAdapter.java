package com.example.poly_lib_su24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poly_lib_su24.R;
import com.example.poly_lib_su24.model.Books;
import com.example.poly_lib_su24.viewholder.BookViewHolder;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {
    Context context;
    ArrayList<Books> listBook;
    public BookAdapter(Context context, ArrayList<Books> listBook){
        this.context = context;
        this.listBook = listBook;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.book_viewholder_layout, null);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Books books = listBook.get(position);
        holder.txtID.setText(books.getMaSach()+"");
        holder.txtBookTitle.setText(books.getTenSach());
        holder.txtType.setText(books.getMaLoai()+"");
        holder.txtPrice.setText(books.getGiaSach()+"");
    }

    @Override
    public int getItemCount() {
        return listBook.size();
    }
}
