package com.example.poly_lib_su24.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.poly_lib_su24.database.DpHelper;
import com.example.poly_lib_su24.model.Books;
import com.example.poly_lib_su24.model.NguoiDung;

import java.security.PublicKey;
import java.util.ArrayList;

public class NguoiDungDAO {

    public DpHelper dpHelper;

    public NguoiDungDAO(Context context) {
        dpHelper = new DpHelper(context);
    }

    public boolean KiemTraDangNhap(String username, String pass) {
        SQLiteDatabase db = dpHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM NGUOIDUNG WHERE tendangnhap = ? AND matkhau = ?",
                new String[]{username,pass});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean DangKyNguoiDung(NguoiDung nguoiDung) {
        SQLiteDatabase db = dpHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", "Nguyễn Hoàng Tuấn");
        values.put("tendangnhap",nguoiDung.getTendangnhap());
        values.put("matkhau", nguoiDung.getMatkhau());
        long check = db.insert("NGUOIDUNG", null, values);
        return check != -1;
    }

    public ArrayList<NguoiDung> getAllCustomer(){
        ArrayList<NguoiDung> listND = new ArrayList<>();
        SQLiteDatabase db = dpHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM NGUOIDUNG ", null);
        if(c.moveToFirst()){
            do{
                int maNguoiDung = c.getInt(0);
                String tenND = c.getString(1);
                String tenDangNhap = c.getString(2);
                String matKhau = c.getString(3);
                int role = c.getInt(4);
                NguoiDung nguoiDung = new NguoiDung(maNguoiDung, tenND, tenDangNhap, matKhau, role);
                listND.add(nguoiDung);
            }
            while (c.moveToNext());
        }
        return listND;
    }

}
