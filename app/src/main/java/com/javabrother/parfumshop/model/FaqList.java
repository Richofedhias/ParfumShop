package com.javabrother.parfumshop.model;

public class FaqList {
    String Pertanyaan;
    String key;

    public FaqList(String pertanyaan) {
        Pertanyaan = pertanyaan;
    }

    public FaqList() {
    }

    public String getPertanyaan() {
        return Pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        Pertanyaan = pertanyaan;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
