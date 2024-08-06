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

        String sqlThanhVien = "CREATE TABLE THANHVIEN(" +
                "mathanhvien INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenthanhvien TEXT, " +
                "manguoidung INTEGER REFERENCES NGUOIDUNG(manguoidung), " +
                "img TEXT, " +
                "xoa INTEGER)";
        db.execSQL(sqlThanhVien);

//        Data mẫu
        db.execSQL("INSERT INTO LOAISACH VALUES (1, 'Kiến thức','https://res.cloudinary.com/namnn512/image/upload/v1721122941/r2vedgox1ag2ohrfesez.jpg',''), (2, 'Kỹ năng','https://res.cloudinary.com/namnn512/image/upload/v1721129837/hscysntrnuiqgp2kv2hg.jpg',''), (3, 'Tiểu Thuyết','https://res.cloudinary.com/namnn512/image/upload/v1721129790/ctbrgmh1tuxjp47rnnqs.jpg',''), (4,'Giải Trí','https://res.cloudinary.com/namnn512/image/upload/v1721127973/xthvvboqdrwv4ljnb42m.jpg','')");
        db.execSQL("INSERT INTO SACH VALUES " +
                "(1, 'Hiểu về trái tim', 35000, 1,'',''), " +
                "(2, 'Não bộ nghĩ gì về bạn', 30000, 1,'https://res.cloudinary.com/namnn512/image/upload/v1722877312/0b4b78de-659c-4657-b203-00159dd4bf23.png',''), " +
                "(3, 'Thiên  tài bên trái  kẻ điên bên phải', 40000, 1,'https://res.cloudinary.com/namnn512/image/upload/v1722877337/c3e63c04-1d72-4134-b6f6-3c98663fe815.png',''), " +
                "(4, 'Tư duy nhanh và chậm', 25000, 2,'https://res.cloudinary.com/namnn512/image/upload/v1722877387/7c67d881-a521-487d-91f0-680536f1ae3f.png',''), " +
                "(5, 'Kỷ luật làm nên con người', 45000, 2,'https://res.cloudinary.com/namnn512/image/upload/v1722877429/52cacd35-ed69-4614-bad3-88f45c6d6629.png',''), " +
                "(6, 'Sherlock Holmes', 50000, 3,'https://res.cloudinary.com/namnn512/image/upload/v1722876829/154464f5-923b-4427-8256-aacc4c9d04ac.png',''), " +
                "(7, 'Jekyll & Hyde', 70000, 3,'https://res.cloudinary.com/namnn512/image/upload/v1722876874/16133076-21aa-49e1-8d35-e17d43f69a23.png',''), " +
                "(8, 'Harry Potter', 50000, 3,'https://res.cloudinary.com/namnn512/image/upload/v1722876960/9a4da819-7848-475a-a19d-5b0066872ffc.png',''), " +
                "(9, 'Naruto Shippuden', 50000, 4,'https://res.cloudinary.com/namnn512/image/upload/v1722877085/eca8aa41-86c1-446c-b12a-ac3e4546030c.png',''), " +
                "(10, 'Doraemon', 20000, 4,'https://res.cloudinary.com/namnn512/image/upload/v1722877141/f30e9a31-2ba5-4ce8-a487-56c2c92e17e1.png',''), " +
                "(11, 'Connan', 25000, 4,'https://res.cloudinary.com/namnn512/image/upload/v1722877138/abae2f28-2812-4bbc-a06d-14c29131f946.png','')");
        db.execSQL("INSERT INTO NGUOIDUNG VALUES (1,'Nguyễn Trần Trung Nguyên','trungnguyenk4','5885',1), (2,'Nguyễn Nhật Nam','nhatnamk5','123',2)");
        db.execSQL("INSERT INTO PHIEUMUON VALUES (1, 35000, '01-03-2024',2,1,0)");


//        db.execSQL("INSERT INTO CTPM VALUES (1,1,1)");
        db.execSQL("INSERT INTO THANHVIEN VALUES (1,'Nguyễn Nhật Nam',2,'https://res.cloudinary.com/namnn512/image/upload/v1722959390/lxsxjnaw6wsd0x4g9oet.jpg','')");
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
