package com.javabrother.parfumshop.model;

public class WanitaParfumList {
    String nama, harga;

    public WanitaParfumList(String nama, String harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public WanitaParfumList() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }


}
