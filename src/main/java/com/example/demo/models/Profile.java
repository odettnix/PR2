package com.example.demo.models;

import org.apache.tomcat.jni.Address;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import java.util.Set;





import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;

@Entity
@Table(name = "profile")
public class Profile {
    public Profile(String username, String name, Date data_reg, char gender, int age) {
        this.username = username;
        this.name = name;
        this.data_reg = data_reg;
        this.gender = gender;
        this.age = age;
    }

    public Profile() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "Поле не может быть пустым")
    @NotBlank(message = "Поле не должно состоять из одних пробелов")
    @Size(min=1, max=50, message = "Размер данного поля должен быть в диапозоне от 1 до 50")
    private String username;


    @NotEmpty(message = "Поле не может быть пустым")
    @NotBlank(message = "Поле не должно состоять из одних пробелов")
    @Size(min=1, max=50, message = "Размер данного поля должен быть в диапозоне от 1 до 50")
    private String name;



    @PastOrPresent(message = "Выбранная вами дата должна быть либо в настоящем, либо в прошлом")
//    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date data_reg;

    private char gender;
    @Min(value = 16, message = "Пользователь должен быть старше 16")
    @NotNull(message = "Поле не может быть пустым")
    @Positive(message = "Поле должно быть больше 0")
    private int age;







//    @OneToMany(mappedBy = "profile", fetch = FetchType.EAGER)
//    private List<Post> post;

//    @ManyToMany()
//    @JoinTable(name = "post_profile",
//            joinColumns = @JoinColumn (name = "profile_id"),
//            inverseJoinColumns = @JoinColumn (name = "post_id")
//    )
//    public List<Post> posts;
//
//    public List<Post> getPosts() {
//        return posts;
//    }

//    public void setPosts(List<Post> posts) {
//        this.posts = posts;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getData_reg() {
        return data_reg;
    }


    public void setData_reg(Date data_reg) {
        this.data_reg = data_reg;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }







}
