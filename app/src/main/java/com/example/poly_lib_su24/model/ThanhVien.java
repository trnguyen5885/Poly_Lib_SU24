package com.example.poly_lib_su24.model;

public class ThanhVien {
    private int mathanhvien;
    private String tenthanhvien;
    private String img="";
    private int xoa;

    public ThanhVien(int mathanhvien, String tenthanhvien, String img) {
        this.mathanhvien = mathanhvien;
        this.tenthanhvien = tenthanhvien;
        this.img = img;
    }

    public ThanhVien(int mathanhvien, String tenthanhvien, String img, int xoa) {
        this.mathanhvien = mathanhvien;
        this.tenthanhvien = tenthanhvien;
        this.img = img;
        this.xoa = xoa;
    }

    public int getMathanhvien() {
        return mathanhvien;
    }

    public void setMathanhvien(int mathanhvien) {
        this.mathanhvien = mathanhvien;
    }

    public String getTenthanhvien() {
        return tenthanhvien;
    }

    public void setTenthanhvien(String tenthanhvien) {
        this.tenthanhvien = tenthanhvien;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getXoa() {
        return xoa;
    }

    public void setXoa(int xoa) {
        this.xoa = xoa;
    }
}
