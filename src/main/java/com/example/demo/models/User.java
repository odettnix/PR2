package com.example.demo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements UserDetails {

    public User(String username, String password, boolean active, String name, Date data_reg, char gender, int age, MoreInfo moreInfo) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.name = name;
        this.data_reg = data_reg;
        this.gender = gender;
        this.age = age;
        this.moreInfo = moreInfo;
    }

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private boolean active;
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



    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToOne(optional = true, mappedBy = "user")
    private MoreInfo moreInfo;

    @ManyToMany()
    @JoinTable(name = "post_user",
            joinColumns = @JoinColumn (name = "user_id"),
            inverseJoinColumns = @JoinColumn (name = "post_id")
    )
    public List<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Post> post;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /////


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

    public MoreInfo getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(MoreInfo moreInfo) {
        this.moreInfo = moreInfo;
    }
}


