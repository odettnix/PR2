package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Commentari {
    public Commentari(String text_otz, String author, Date date_otz, int mark, char recommend) {
        this.recommend = recommend;
        this.text_otz = text_otz;
        this.author = author;
        this.date_otz = date_otz;
        this.mark = mark;
    }

    public Commentari() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text_otz, author;
    private char recommend;
    private Date date_otz;
    private int mark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getText_otz() {
        return text_otz;
    }

    public void setText_otz(String text_otz) {
        this.text_otz = text_otz;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public char getRecommend() {
        return recommend;
    }

    public void setRecommend(char recommend) {
        this.recommend = recommend;
    }

    public Date getDate_otz() {
        return date_otz;
    }

    public void setDate_otz(Date date_otz) {
        this.date_otz = date_otz;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}