package com.javabrother.parfumshop.admin_fragment.Wanita;

public class WanitaList {
    String nama;
    String harga;
    String key;

    public WanitaList(String nama, String harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public WanitaList() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getHarga() {
        return harga;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
