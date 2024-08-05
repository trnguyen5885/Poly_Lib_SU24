package com.example.poly_lib_su24.DAO;

import android.content.ContentValues;
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
        Cursor c = db.rawQuery("select pm.maphieumuon, pm.giathue, pm.ngaymuon, nd.manguoidung, s.masach,s.tensach, pm.trangthai from nguoidung nd, sach s, phieumuon pm  where s.masach=pm.masach and nd.manguoidung = pm.manguoidung", null);
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
    public boolean thayDoiTrangThai(int mapm){
        SQLiteDatabase sqLiteDatabase = dpHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trangthai",1);
        long check = sqLiteDatabase.update("PHIEUMUON",contentValues,"maphieumuon = ?",new String[]{String.valueOf(mapm)});
        if(check == -1) {
            return false;
        }
        else return true;
    }
    public boolean addItemPM(PhieuMuon phieuMuon) {
        SQLiteDatabase sqLiteDatabase = dpHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //PHIEUMUON(maphieumuon integer primary key autoincrement , giathue integer, ngaymuon text, manguoidung integer references NGUOIDUNG(manguoidung), masach integer references SACH(masach), trangthai integer
//        contentValues.put("maphieumuon", phieuMuon.getMaPhieu());
        contentValues.put("giathue", phieuMuon.getGiathue());
        contentValues.put("ngaymuon", phieuMuon.getNgayThue());
        contentValues.put("manguoidung", phieuMuon.getMaND());
        contentValues.put("masach", phieuMuon.getMaSach());
        contentValues.put("trangthai", phieuMuon.getTrangthai());

        long check = sqLiteDatabase.insert("PHIEUMUON", null, contentValues);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }


}
