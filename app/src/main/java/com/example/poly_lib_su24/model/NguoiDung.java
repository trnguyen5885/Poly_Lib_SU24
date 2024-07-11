package com.example.poly_lib_su24.model;

public class NguoiDung {

    public int manguoidung;
    public String hoten;
    public String tendangnhap;
    public String matkhau;
    public int role;

    public NguoiDung(int manguoidung, String hoten, String tendangnhap, String matkhau, int role) {
        this.manguoidung = manguoidung;
        this.hoten = hoten;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.role = role;
    }

    public NguoiDung(String hoten, String tendangnhap, String matkhau) {
        this.hoten = hoten;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
    }

    public int getManguoidung() {
        return manguoidung;
    }

    public void setManguoidung(int manguoidung) {
        this.manguoidung = manguoidung;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
