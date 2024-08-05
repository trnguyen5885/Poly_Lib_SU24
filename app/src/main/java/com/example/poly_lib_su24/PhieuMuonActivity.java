package com.example.poly_lib_su24;

import static java.security.AccessController.getContext;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poly_lib_su24.DAO.BooksDAO;
import com.example.poly_lib_su24.DAO.NguoiDungDAO;
import com.example.poly_lib_su24.DAO.PhieuMuonDAO;
import com.example.poly_lib_su24.R;
import com.example.poly_lib_su24.adapter.BookAdapter;
import com.example.poly_lib_su24.adapter.BookTypeAdapter;
import com.example.poly_lib_su24.adapter.PhieuMuonAdapter;
import com.example.poly_lib_su24.model.BookType;
import com.example.poly_lib_su24.model.Books;
import com.example.poly_lib_su24.model.NguoiDung;
import com.example.poly_lib_su24.model.PhieuMuon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class PhieuMuonActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rvPhieuMuon;
    ArrayList<PhieuMuon> listPM = new ArrayList<>();;
    PhieuMuonDAO phieuMuonDAO;
    PhieuMuonAdapter adapter;
    FloatingActionButton fButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_muon);

        toolbar = findViewById(R.id.toolBarPM);
        rvPhieuMuon = findViewById(R.id.rvPhieuMuon);
        fButton = findViewById(R.id.fButton);
        phieuMuonDAO = new PhieuMuonDAO(PhieuMuonActivity.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quản Lí Phiếu Mượn");

        doDuLieu();

        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }

    private void doDuLieu(){
        phieuMuonDAO = new PhieuMuonDAO(PhieuMuonActivity.this);
        listPM = phieuMuonDAO.getDSPhieuMuon();
        adapter = new PhieuMuonAdapter(PhieuMuonActivity.this, listPM);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PhieuMuonActivity.this);
        rvPhieuMuon.setLayoutManager(linearLayoutManager);
        rvPhieuMuon.setAdapter(adapter);
    }

    private void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(PhieuMuonActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_them_phieumuon,null);
        Spinner spnThanhVien = v.findViewById(R.id.spnThanhVien);
        Spinner spnSach = v.findViewById(R.id.spnSach);
        EditText edtGiaThue = v.findViewById(R.id.edtGiaThue);
        getDataThanhVien(spnThanhVien);
        getDataSach(spnSach);
        builder.setView(v);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                HashMap<String,Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                int matv = (int) hsTV.get("manguoidung");

                HashMap<String,Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                int maSach = (int) hsSach.get("masach");

                int giaTien = Integer.parseInt(edtGiaThue.getText().toString());

                themPhieuMuon(matv,maSach,giaTien);
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
    private void getDataThanhVien(Spinner spnThanhVien){
        NguoiDungDAO nguoiDungDAO =  new NguoiDungDAO(PhieuMuonActivity.this);
        ArrayList<NguoiDung> list = nguoiDungDAO.getAllCustomer();

        ArrayList<HashMap<String,Object>> listHM = new ArrayList<>();
        for(NguoiDung nd:list){
            HashMap<String,Object> hs = new HashMap<>();
            hs.put("manguoidung",nd.getManguoidung());
            hs.put("hoten",nd.getHoten());
            listHM.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                 PhieuMuonActivity.this,
                        listHM,
                        android.R.layout.simple_list_item_1,
                        new String[]{"hoten"},
                        new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);
    }
    private void getDataSach(Spinner spnSach){
        BooksDAO booksDAO=  new BooksDAO(PhieuMuonActivity.this);
        ArrayList<Books> listBook = booksDAO.getAllBooks();

        ArrayList<HashMap<String,Object>> listHM = new ArrayList<>();
        for(Books books:listBook){
            HashMap<String,Object> hs = new HashMap<>();
            hs.put("masach",books.getMaSach());
            hs.put("tensach",books.getTenSach());
            listHM.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                PhieuMuonActivity.this,
                listHM,
                android.R.layout.simple_list_item_2,
                new String[]{"tensach"},
                new int[]{android.R.id.text2});
        spnSach.setAdapter(simpleAdapter);
    }

    private void themPhieuMuon(int matv, int maSach,int giaTien){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currentTime);

        PhieuMuon phieuMuon = new PhieuMuon(giaTien,matv,0,maSach,ngay);

        boolean kiemtra = phieuMuonDAO.addItemPM(phieuMuon);
        if(kiemtra){
            Toast.makeText(PhieuMuonActivity.this,"Thêm phiếu mượn thành công",Toast.LENGTH_SHORT).show();
            doDuLieu();
        }else{
            Toast.makeText(PhieuMuonActivity.this,"Thêm phiếu mượn thất bại",Toast.LENGTH_SHORT).show();
        }

    }
}