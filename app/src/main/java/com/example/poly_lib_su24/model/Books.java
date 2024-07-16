package com.example.poly_lib_su24.model;

public class Books {
    int maSach;
    String tenSach;
    int giaSach;
    int maLoai;
    String img;
    String tenLoaiSach;
    public Books(int maSach, String tenSach, int giaSach, int maLoai, String tenLoaiSach, String img){
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaSach = giaSach;
        this.maLoai = maLoai;
        this.tenLoaiSach = tenLoaiSach;
        this.img = img;
    }
    public Books(String tenSach, int giaSach, int maLoai, String img){
        this.tenSach = tenSach;
        this.giaSach = giaSach;
        this.maLoai = maLoai;
        this.img = img;
    }

    public int getMaLoai() {
        return maLoai;
    }
    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGiaSach() {
        return giaSach;
    }

    public void setGiaSach(int giaSach) {
        this.giaSach = giaSach;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTenLoaiSach() {
        return tenLoaiSach;
    }

    public void setTenLoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
    }
}
