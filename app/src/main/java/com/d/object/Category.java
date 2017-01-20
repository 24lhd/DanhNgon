package com.d.object;

import java.io.Serializable;

/**
 * Created by d on 19/01/2017.
 */

public class Category implements Serializable{
    private String category;
    private String ma;

    @Override
    public String toString() {
        return "Category{" +
                "category='" + category + '\'' +
                ", ma='" + ma + '\'' +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public Category(String category, String ma) {

        this.category = category;
        this.ma = ma;
    }
}
