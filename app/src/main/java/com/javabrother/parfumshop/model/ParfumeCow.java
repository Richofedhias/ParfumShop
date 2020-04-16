package com.javabrother.parfumshop.model;

public class ParfumeCow {
    String Nama;
    String harga;
    String jumlah;

    public ParfumeCow() {
    }

    public ParfumeCow(String nama, String harga, String jumlah) {
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
