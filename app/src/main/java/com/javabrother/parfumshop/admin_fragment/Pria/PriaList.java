package com.javabrother.parfumshop.admin_fragment.Pria;

public class PriaList {
    String nama;
    String harga;
    String key;

    public PriaList(String nama, String harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public PriaList() {
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
