package com.example.poly_lib_su24.model;

public class Books {
    int maSach;
    String tenSach;
    int giaSach;
    int maLoai;
    int img;
    public Books(int maSach, String tenSach, int giaSach, int maLoai){
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaSach = giaSach;
        this.maLoai = maLoai;
    }
    public Books(String tenSach, int giaSach, int maLoai){
        this.tenSach = tenSach;
        this.giaSach = giaSach;
        this.maLoai = maLoai;
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
}
