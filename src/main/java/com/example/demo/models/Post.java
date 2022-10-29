package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {
    public Post(String title, String anons, String full_text, User user) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
        this.user = user;
    }

    public Post() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым")
    @NotBlank(message = "Поле не должно состоять из одних пробелов")
    @Size(min=2, max=50, message = "Размер данного поля должен быть в диапозоне от 2 до 50")
    private String title, anons, full_text;
    private int views;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    public User user;

    @ManyToMany
    @JoinTable (name="post_user",
            joinColumns=@JoinColumn (name="post_id"),
            inverseJoinColumns=@JoinColumn(name="user_id"))
    public List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
