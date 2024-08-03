package com.example.poly_lib_su24;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    Button btnSach, btnThanhVien, btnPhieuMuon, btnThongKe;
    ActionBarDrawerToggle toggle;

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

        btnPhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this,PhieuMuonActivity.class);
                startActivity(i);
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, ThongKeActivity.class);
                startActivity(i);
            }
        });

        btnThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, ThanhVienActivity.class);
                startActivity(i);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.logotoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(v -> {
            if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId() == R.id.theLoai) {
                        Intent intent = new Intent(AdminActivity.this, BookTypeActivity.class);
                        startActivity(intent);
                    }
                    else if (item.getItemId() == R.id.sach){
                        Intent intent = new Intent(AdminActivity.this, BooksActivity.class);
                        startActivity(intent);
                    }else if (item.getItemId() == R.id.phieuMuon){
                        Intent intent = new Intent(AdminActivity.this, PhieuMuonActivity.class);
                        startActivity(intent);
                    }else if (item.getItemId() == R.id.thanhVien){
                        Intent intent = new Intent(AdminActivity.this, ThanhVienActivity.class);
                        startActivity(intent);
                    }
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}