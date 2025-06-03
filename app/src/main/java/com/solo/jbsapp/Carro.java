package com.solo.jbsapp;

import java.time.LocalDateTime;

public class Carro {
    private String placa;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

    public Carro() {
    }

    public Carro(String placa, LocalDateTime dataEntrada) {
        this.placa = placa;
        this.dataEntrada = dataEntrada;
    }

    public Carro(String placa, LocalDateTime dataEntrada, LocalDateTime dataSaida) {
        this.placa = placa;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }
}
