package com.d.object;

import java.io.Serializable;

/**
 * Created by d on 19/01/2017.
 */

public class DanhNgon implements Serializable{
    public DanhNgon(String stt, String content, String author, String category, String favorite) {
        this.stt = stt;
        this.content = content;
        this.author = author;
        this.category = category;
        this.favorite = favorite;
    }

    private String stt;
    private String content;
    private String author;
    private String category;
    private String favorite;

    @Override
    public String toString() {
        return "DanhNgon{" +
                "content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", favorite='" + favorite + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }
}
