package com.example.poly_lib_su24;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    Button btnSach, btnThanhVien, btnPhieuMuon, btnThongKe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        drawerLayout = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolBarCustom);
        navigationView = findViewById(R.id.navigationView);
        btnSach = findViewById(R.id.btnQuanLySach);
        btnThanhVien = findViewById(R.id.btnQuanLyThanhVien);
        btnPhieuMuon = findViewById(R.id.btnQuanLyPhieuMuon);
        btnThongKe = findViewById(R.id.btnThongKe);

        btnSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, BookTypeActivity.class);
                startActivity(i);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.logotoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }
}