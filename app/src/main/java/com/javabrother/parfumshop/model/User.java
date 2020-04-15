package com.javabrother.parfumshop.model;

public class User {
    private String email;
    private String nama;

    public User() {
    }

    public User(String email, String nama) {
        this.email = email;
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
