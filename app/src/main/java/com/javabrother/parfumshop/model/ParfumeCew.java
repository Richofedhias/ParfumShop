package com.javabrother.parfumshop.model;

public class ParfumeCew {
    String Nama;
    String harga;

    public ParfumeCew() {
    }

    public ParfumeCew(String nama, String harga) {
        Nama = nama;
        this.harga = harga;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

}
