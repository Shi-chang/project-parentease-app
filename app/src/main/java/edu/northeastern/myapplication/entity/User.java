package edu.northeastern.myapplication.entity;

/**
 * The user class.
 */
public class User {
    private String username;
    private String email;
    private String city;
    private Tip[] tips;

    public User() {
    }

    public User(String username, String email, String city, Tip[] tips) {
        this.username = username;
        this.email = email;
        this.city = city;
        this.tips = tips;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public Tip[] getTips() {
        return tips;
    }
}
