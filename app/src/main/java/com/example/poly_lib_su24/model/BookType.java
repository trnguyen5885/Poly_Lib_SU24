package com.example.poly_lib_su24.model;

public class BookType {
    int maLoai;
    String tenLoaiSach;
    public BookType(int maLoai, String tenLoaiSach){
        this.maLoai = maLoai;
        this.tenLoaiSach = tenLoaiSach;
    }
    public BookType(String tenLoaiSach){
        this.tenLoaiSach = tenLoaiSach;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoaiSach() {
        return tenLoaiSach;
    }

    public void setTenLoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
    }
}
