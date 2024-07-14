package com.example.poly_lib_su24;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poly_lib_su24.adapter.BookTypeAdapter;
import com.example.poly_lib_su24.dao.BookTypeDAO;
import com.example.poly_lib_su24.model.BookType;

import java.util.ArrayList;

public class BookTypeActivity extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<BookType> list = new ArrayList<>();
    BookTypeDAO bookTypeDAO;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_type);
        toolbar = findViewById(R.id.toolBar);
        rv = findViewById(R.id.rvBookType);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản Lí Loại Sách");


        bookTypeDAO = new BookTypeDAO(BookTypeActivity.this);
        bookTypeDAO.addBook(new BookType("Kiến Thức"));

        doDuLieu();

    }
    public void doDuLieu(){
        list = bookTypeDAO.getAllBookType();
        BookTypeAdapter bookTypeAdapter = new BookTypeAdapter(BookTypeActivity.this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookTypeActivity.this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(bookTypeAdapter);
    }
    public void delete(int id){
        bookTypeDAO.delete(id);
        doDuLieu();
    }
}