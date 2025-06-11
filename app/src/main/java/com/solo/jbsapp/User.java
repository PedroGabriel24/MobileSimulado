package com.solo.jbsapp;

public class User {

    private String email;
    private String senha;
    private Boolean userRole;

    public User() {}

    public User(String email, String senha, Boolean userRole) {
        this.email = email;
        this.senha = senha;
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getUserRole() {
        return this.userRole;
    }
}
