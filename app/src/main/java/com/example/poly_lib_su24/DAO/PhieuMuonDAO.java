package com.example.poly_lib_su24.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.poly_lib_su24.database.DpHelper;
import com.example.poly_lib_su24.model.Books;
import com.example.poly_lib_su24.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonDAO {
    public DpHelper dpHelper;
    public  PhieuMuonDAO(Context context){
        dpHelper = new DpHelper(context);
    }
    public ArrayList<PhieuMuon> getDSPhieuMuon(){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase db = dpHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select pm.maphieumuon, pm.giathue, pm.ngaymuon, tv.manguoidung, s.masach,s.tensach, pm.trangthai from thanhvien tv, sach s, phieumuon pm  where s.masach=pm.masach", null);
        if(c.moveToFirst()){
            do{
                int maPhieuMuon = c.getInt(0);
                int giaThue = c.getInt(1);
                String ngayThue = c.getString(2);
                int maNguoiDung = c.getInt(3);
                int maSach  = c.getInt(4);
                String tenSach = c.getString(5);
                int trangThai = c.getInt(6);
                PhieuMuon phieumuon = new PhieuMuon(maPhieuMuon, giaThue, ngayThue, maNguoiDung, maSach,tenSach, trangThai);
                list.add(phieumuon);
            }
            while (c.moveToNext());
        }
        return list;
    }



}
