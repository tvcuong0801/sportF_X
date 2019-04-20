package com.example.tvcuo.sportf;

public class DonDatTruoc {
    private int idDT;
    private String email;
    int idSB;
    private String loaiSan;
    private String ngay;
    private String gio;
    private String soGio;
    String ghiChu;
    private int daThanhToan;
    private int tongTien;

    DonDatTruoc(int idDT, String email, int idSB, String loaiSan, String ngay, String gio, String soGio, String ghiChu, int daThanhToan, int tongTien) {
        this.idDT = idDT;
        this.email = email;
        this.idSB = idSB;
        this.loaiSan = loaiSan;
        this.ngay = ngay;
        this.gio = gio;
        this.soGio = soGio;
        this.ghiChu = ghiChu;
        this.daThanhToan = daThanhToan;
        this.tongTien = tongTien;
    }

    public int getIdDT() {
        return idDT;
    }

    public void setIdDT(int idDT) {
        this.idDT = idDT;
    }

    String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdSB() {
        return idSB;
    }

    public void setIdSB(int idSB) {
        this.idSB = idSB;
    }

    String getLoaiSan() {
        return loaiSan;
    }

    public void setLoaiSan(String loaiSan) {
        this.loaiSan = loaiSan;
    }

    String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    String getSoGio() {
        return soGio;
    }

    public void setSoGio(String soGio) {
        this.soGio = soGio;
    }

    String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    int getDaThanhToan() {
        return daThanhToan;
    }

    public void setDaThanhToan(int daThanhToan) {
        this.daThanhToan = daThanhToan;
    }

    int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
