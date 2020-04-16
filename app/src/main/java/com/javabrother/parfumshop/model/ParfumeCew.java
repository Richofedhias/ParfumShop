package com.javabrother.parfumshop.model;

public class ParfumeCew {
    String Nama;
    String harga;
    String jumlah;

    public ParfumeCew() {
    }

    public ParfumeCew(String nama, String harga, String jumlah) {
        Nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
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

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
