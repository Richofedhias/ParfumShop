package com.javabrother.parfumshop.model;

public class KeranjangList {
    String Nama;
    String harga;
    String jumlah;
    String key;

    public KeranjangList(String nama, String harga, String jumlah) {
        Nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public KeranjangList() {
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return " "+Nama+"\n" +
                " "+harga +"\n" +
                " "+harga;
    }
}
