package com.example.poly_lib_su24;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poly_lib_su24.adapter.BookAdapter;
import com.example.poly_lib_su24.dao.BooksDAO;
import com.example.poly_lib_su24.model.Books;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class BooksActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    RecyclerView rvCustomers;
    ArrayList<Books> listBooks = new ArrayList<Books>();
    BooksDAO booksDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer);

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolBarCustom);
        navigationView = findViewById(R.id.navigationView);
        rvCustomers = findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.logo);

        booksDAO = new BooksDAO(BooksActivity.this);
        booksDAO.addBook(new Books("Tim Hieu Trai Tim", "Kien Thuc", "50.000"));

        listBooks = booksDAO.getAllBooks();
        BookAdapter adapter = new BookAdapter(BooksActivity.this, listBooks);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BooksActivity.this);
        rvCustomers.setLayoutManager(linearLayoutManager);
        rvCustomers.setAdapter(adapter);
    }
}