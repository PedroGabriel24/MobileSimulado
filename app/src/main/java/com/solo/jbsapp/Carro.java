package com.solo.jbsapp;

public class Carro {
    private int id;
    private String placa;
    private String dtEntrada;
    private String dtSaida;

    private String userEmail;

    public Carro() {
    }

    public Carro(int id, String placa, String dtEntrada, String userEmail) {
        this.id = id;
        this.placa = placa;
        this.dtEntrada = dtEntrada;
        this.userEmail = userEmail;
    }

    public Carro(int id, String placa, String dtEntrada, String dtSaida, String userEmail) {
        this.id = id;
        this.placa = placa;
        this.dtEntrada = dtEntrada;
        this.dtSaida = dtSaida;
        this.userEmail = userEmail;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDtEntrada() {
        return dtEntrada;
    }

    public void setDtEntrada(String dtEntrada) {
        this.dtEntrada = dtEntrada;
    }

    public String getDtSaida() {
        return dtSaida;
    }

    public void setDtSaida(String dtSaida) {
        this.dtSaida = dtSaida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
