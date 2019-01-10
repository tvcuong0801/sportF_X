package com.example.tvcuo.sportf;

public class HinhAnh {
    private int IdHA;
    private int IdSB;
    private String Url;

    public HinhAnh(int idHA, int idSB, String url) {
        IdHA = idHA;
        IdSB = idSB;
        Url = url;
    }

    public int getIdHA() {
        return IdHA;
    }

    public void setIdHA(int idHA) {
        IdHA = idHA;
    }

    public int getIdSB() {
        return IdSB;
    }

    public void setIdSB(int idSB) {
        IdSB = idSB;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
