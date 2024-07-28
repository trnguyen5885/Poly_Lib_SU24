package com.example.poly_lib_su24;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poly_lib_su24.DAO.BooksDAO;
import com.example.poly_lib_su24.DAO.PhieuMuonDAO;
import com.example.poly_lib_su24.R;
import com.example.poly_lib_su24.adapter.BookAdapter;
import com.example.poly_lib_su24.adapter.BookTypeAdapter;
import com.example.poly_lib_su24.adapter.PhieuMuonAdapter;
import com.example.poly_lib_su24.model.Books;
import com.example.poly_lib_su24.model.PhieuMuon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PhieuMuonActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rvPhieuMuon;
    ArrayList<PhieuMuon> listPM = new ArrayList<PhieuMuon>();
    PhieuMuonDAO phieuMuonDAO;
    PhieuMuonAdapter adapter;
    FloatingActionButton fButton;
    TextView txtTrangThai;
    ArrayList<Books> filteredPhieuMuon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_muon);

        toolbar = findViewById(R.id.toolBar);
        rvPhieuMuon = findViewById(R.id.rvPhieuMuon);
        fButton = findViewById(R.id.fButton);
        phieuMuonDAO = new PhieuMuonDAO(PhieuMuonActivity.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản Lí Phiếu Mượn ");

        doDuLieu();
    }

    public void doDuLieu(){
        listPM = phieuMuonDAO.getDSPhieuMuon();
        PhieuMuonAdapter phieuMuonAdapter = new PhieuMuonAdapter(PhieuMuonActivity.this, listPM);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PhieuMuonActivity.this);
        rvPhieuMuon.setLayoutManager(linearLayoutManager);
        rvPhieuMuon.setAdapter(phieuMuonAdapter);
    }
}