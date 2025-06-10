package com.solo.jbsapp;

public class Carro {
    private int id;
    private String placa;
    private String dtEntrada;
    private String dtSaida;

    public Carro() {
    }

    public Carro(int id, String placa, String dtEntrada) {
        this.id = id;
        this.placa = placa;
        this.dtEntrada = dtEntrada;
    }

    public Carro(int id, String placa, String dtEntrada, String dtSaida) {
        this.id = id;
        this.placa = placa;
        this.dtEntrada = dtEntrada;
        this.dtSaida = dtSaida;
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
}
