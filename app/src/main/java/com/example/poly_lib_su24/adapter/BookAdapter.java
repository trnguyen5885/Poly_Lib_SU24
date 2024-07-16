package com.example.poly_lib_su24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.poly_lib_su24.BookTypeActivity;
import com.example.poly_lib_su24.BooksActivity;
import com.example.poly_lib_su24.R;
import com.example.poly_lib_su24.model.BookType;
import com.example.poly_lib_su24.model.Books;
import com.example.poly_lib_su24.viewholder.BookViewHolder;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {
    Context context;
    ArrayList<Books> listBook;


    public BookAdapter(Context context, ArrayList<Books> listBook) {
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
        holder.txtID.setText("ID: " + books.getMaSach() + "");
        holder.txtBookTitle.setText(books.getTenSach());
        holder.txtType.setText(books.getTenLoaiSach() + "");
        holder.txtPrice.setText(books.getGiaSach() + "");
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = books.getMaLoai();
                ((BookTypeActivity)context).delete(id);
            }
        });
        // xu ly  hinh anh
        if(!listBook.get(position).getImg().equals("")){
            Glide.with(context).load(listBook.get(position).getImg()).into(holder.imgBook);
        }
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Books book = listBook.get(position);
                ((BooksActivity)context).suaSach(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBook.size();
    }

}

