package com.javabrother.parfumshop;

public class PriaParfumList {
    private String namaParfum, hargaParfum;

    public PriaParfumList(String namaParfum, String hargaParfum) {
        this.namaParfum = namaParfum;
        this.hargaParfum = hargaParfum;
    }

    public PriaParfumList() {
    }

    public String getNamaParfum() {
        return namaParfum;
    }

    public void setNamaParfum(String namaParfum) {
        this.namaParfum = namaParfum;
    }

    public String getHargaParfum() {
        return hargaParfum;
    }

    public void setHargaParfum(String hargaParfum) {
        this.hargaParfum = hargaParfum;
    }

}
