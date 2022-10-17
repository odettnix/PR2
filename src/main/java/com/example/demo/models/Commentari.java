package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Commentari {
    public Commentari(String heading, String text_otz, String author, String date_otz, String mark) {
        this.heading = heading;
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
    private String heading, text_otz, author, date_otz, mark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
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

    public String getDate_otz() {
        return date_otz;
    }

    public void setDate_otz(String date_otz) {
        this.date_otz = date_otz;
    }
}