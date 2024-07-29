package com.example.poly_lib_su24.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DpHelper extends SQLiteOpenHelper {
    public DpHelper(@Nullable Context context) {
        super(context, "QUANLYTHUVIEN", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        Tạo bảng loại sách
//        CREATE TABLE LOAISACH (
//                maloai integer primary key autoincrement,
//                tenloaisach text
//        )

        String sqlLoaiSach = "CREATE TABLE LOAISACH ( maloai integer primary key autoincrement, tenloaisach text, img text, xoa integer)";
        db.execSQL(sqlLoaiSach);

//        Tạo bảng sách
//        CREATE TABLE SACH (
//                masach integer primary key autoincrement ,
//                tensach text,
//                giasach integer,
//                maloai integer references LOAISACH(maloai)
//
//        )

        String sqlSach = " CREATE TABLE SACH(masach integer primary key autoincrement , tensach text, giasach integer, maloai integer references LOAISACH(maloai), img text, xoa integer)";
        db.execSQL(sqlSach);

//        Tạo bảng người dùng
//        CREATE TABLE NGUOIDUNG(
//                manguoidung integer primary key autoincrement,
//                hoten text,
//                tendangnhap text,
//                matkhau text,
//                role integer,
//        )

//        role: 1 admin , 2 nguoidung

        String sqlNguoiDung = "CREATE TABLE NGUOIDUNG( manguoidung integer primary key autoincrement, hoten text, tendangnhap text, matkhau text, role integer)";
        db.execSQL(sqlNguoiDung);

//        Tạo bảng phiếu mượn
//        CREATE TABLE PHIEUMUON(
//                maphieumuon integer primary key autoincrement ,
//                giathue integer,
//                ngaymuon text,
//                manguoidung integer references NGUOIDUNG(manguoidung)
//        )

        String sqlPhieuMuon = "CREATE TABLE PHIEUMUON(maphieumuon integer primary key autoincrement , giathue integer, ngaymuon text, manguoidung integer references NGUOIDUNG(manguoidung), masach integer references SACH(masach), trangthai integer)";
        db.execSQL(sqlPhieuMuon);

//        Tạo bảng chi tiết phiếu mượn
//        CREATE TABLE CTPM(
//                mactpm integer primary key autoincrement,
//                maphieumuon integer references PHIEUMUON(maphieumuon),
//                masach integer references SACH(masach)
//        )

//        String sqlCTPM = "CREATE TABLE CTPM(mactpm integer primary key autoincrement, maphieumuon integer references PHIEUMUON(maphieumuon), masach integer references SACH(masach))";
//        db.execSQL(sqlCTPM);

//        Tạo bảng thành viên
//        CREATE TABLE THANHVIEN(
//                mathanhvien integer primary key autoincrement,
//                tenthanhvien text,
//                manguoidung integer references NGUOIDUNG(manguoidung)
//        )

        String sqlThanhVien = "CREATE TABLE THANHVIEN( mathanhvien integer primary key autoincrement, tenthanhvien text, manguoidung integer references NGUOIDUNG(manguoidung))";
        db.execSQL(sqlThanhVien);

//        Data mẫu
        db.execSQL("INSERT INTO LOAISACH VALUES (1, 'Kiến thức','https://res.cloudinary.com/namnn512/image/upload/v1721122941/r2vedgox1ag2ohrfesez.jpg',''), (2, 'Kỹ năng','https://res.cloudinary.com/namnn512/image/upload/v1721129837/hscysntrnuiqgp2kv2hg.jpg',''), (3, 'Tiểu Thuyết','https://res.cloudinary.com/namnn512/image/upload/v1721129790/ctbrgmh1tuxjp47rnnqs.jpg',''), (4,'Giải Trí','https://res.cloudinary.com/namnn512/image/upload/v1721127973/xthvvboqdrwv4ljnb42m.jpg','')");
        db.execSQL("INSERT INTO SACH VALUES (1, 'Hiểu về trái tim', 35000, 1,'',''), (2, 'Kỹ năng thuyết trình', 25000, 2,'',''), (3, 'Hai Vạn Năm', 28000, 3,'',''), (4, 'Nắng xuân', 19000, 4,'','')");
        db.execSQL("INSERT INTO NGUOIDUNG VALUES (1,'Nguyễn Trần Trung Nguyên','trungnguyenk4','5885',1), (2,'Nguyễn Nhật Nam','nhatnamk5','123',2)");
        db.execSQL("INSERT INTO PHIEUMUON VALUES (1, 35000, '01-03-2024',2,1,0)");
        db.execSQL("INSERT INTO PHIEUMUON VALUES (2, 45000, '01-05-2024',3,3,1)");
        db.execSQL("INSERT INTO PHIEUMUON VALUES (3, 45000, '01-07-2024',3,4,0)");

//        db.execSQL("INSERT INTO CTPM VALUES (1,1,1)");
        db.execSQL("INSERT INTO THANHVIEN VALUES (1,'Nguyễn Nhật Nam',2)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
//            db.execSQL("DROP TABLE IF EXISTS CTPM");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            onCreate(db);
        }
    }
}
