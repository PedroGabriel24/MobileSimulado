package com.solo.jbsapp;

import java.util.Objects;

public class Carro {

    private String id;
    private String placa;
    private String dtEntrada;
    private String dtSaida;
    private Double preco;

    private String userEmail;

    public Carro() {
    }

    public Carro(String placa, String dtEntrada, String userEmail) {
        this.id = createId(placa, dtEntrada);
        this.placa = placa;
        this.dtEntrada = dtEntrada;
        this.userEmail = userEmail;
    }

    public Carro (String placa, String dtEntrada, String dtSaida, Double preco, String userEmail) {
        this.id = createId(placa, dtEntrada);
        this.placa = placa;
        this.dtEntrada = dtEntrada;
        this.dtSaida = dtSaida;
        this.userEmail = userEmail;
        this.preco = preco;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carro carro = (Carro) o;
        return Objects.equals(placa, carro.placa) && Objects.equals(dtEntrada, carro.dtEntrada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placa, dtEntrada);
    }

    private String createId(String placa, String dtEntrada){
        return placa + " " + dtEntrada;
    }
}
