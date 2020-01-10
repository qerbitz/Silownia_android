package com.example.silownia.models;

import java.util.Date;

public class BodyMeasure {
    private int id;
    private Double weight;
    private String data;


    public BodyMeasure(int id, Double weight, String data) {
        this.id = id;
        this.weight = weight;
        this.data = data;
    }

    public BodyMeasure(Double weight, String data) {
        this.weight = weight;
        this.data = data;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
