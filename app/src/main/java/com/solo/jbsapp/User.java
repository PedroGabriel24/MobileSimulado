package com.solo.jbsapp;

public class User {
    private String email;
    private String senha;
    private UserRole userRole;

    public User() {}

    public User(String email, String senha, UserRole userRole) {
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

    public String getUserRole() {
        return userRole.getRole();
    }

    public enum UserRole {
        USER("user"),
        ADMIN("admin");

        private String role;

        UserRole(String role) {
            this.role = role;
        }
        public String getRole() {
            return role;
        }
    }
}
