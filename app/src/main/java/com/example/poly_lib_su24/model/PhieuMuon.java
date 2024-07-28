package com.example.poly_lib_su24.model;

public class PhieuMuon {

    int maPhieu,giathue,maND,trangthai,maSach;
    String ngayThue,tenSach;

    public PhieuMuon(int maPhieu, int giathue,String ngayThue,int maND,int maSach,String tenSach, int trangthai) {
        this.maPhieu = maPhieu;
        this.giathue = giathue;
        this.maND = maND;
        this.trangthai = trangthai;
        this.maSach = maSach;
        this.ngayThue = ngayThue;
        this.tenSach = tenSach;
    }

    public int getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(int maPhieu) {
        this.maPhieu = maPhieu;
    }

    public int getGiathue() {
        return giathue;
    }

    public void setGiathue(int giathue) {
        this.giathue = giathue;
    }

    public int getMaND() {
        return maND;
    }

    public void setMaND(int maND) {
        this.maND = maND;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(String ngayThue) {
        this.ngayThue = ngayThue;
    }
}
