package com.example.tvcuo.sportf;

public class SanCon {
    int idSC;
    int idSB1;
    String ten;
    int gia;

    public int getIdSC() {
        return idSC;
    }

    public void setIdSC(int idSC) {
        this.idSC = idSC;
    }

    public int getIdSB1() {
        return idSB1;
    }

    public void setIdSB1(int idSB) {
        this.idSB1 = idSB;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public SanCon(int idSC, int idSB, String ten, int gia) {
        this.idSC = idSC;
        this.idSB1 = idSB;
        this.ten = ten;
        this.gia = gia;
    }
}
