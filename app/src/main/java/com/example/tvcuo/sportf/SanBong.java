package com.example.tvcuo.sportf;

public class SanBong {
    private int idSB;
    private String ten;
    private String diaChi;
    private int loai;
    private double danhGia;
    private String hinhAnh;

    SanBong(int idSB, String ten, String diaChi, int loai, double danhGia, String hinhAnh) {
        this.idSB = idSB;
        this.ten = ten;
        this.diaChi = diaChi;
        this.loai = loai;
        this.danhGia = danhGia;
        this.hinhAnh = hinhAnh;
    }

    public int getIdSB() {
        return idSB;
    }

    public void setIdSB(int idSB) {
        this.idSB = idSB;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

    public double getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(double danhGia) {
        this.danhGia = danhGia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
