package com.example.tvcuo.sportf;

public class BinhLuan {
    private int idbl;
    private int idsb;
    private String cmt;
    private String hinhAnh;
    private String ten;
    private int danhGia;

    public BinhLuan(int idbl, int idsb, String cmt, String hinhAnh, String ten, int danhGia) {
        this.idbl = idbl;
        this.idsb = idsb;
        this.cmt = cmt;
        this.hinhAnh = hinhAnh;
        this.ten = ten;
        this.danhGia = danhGia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(int danhGia) {
        this.danhGia = danhGia;
    }

    public int getIdbl() {
        return idbl;
    }

    public void setIdbl(int idbl) {
        this.idbl = idbl;
    }

    public int getIdsb() {
        return idsb;
    }

    public void setIdsb(int idsb) {
        this.idsb = idsb;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }
}