package com.example.silownia.models;

public class Belongs_to {
    private int belongs_id;
    private Exercises cwiczenie;
    private Workouts trening;

    public Belongs_to(int belongs_id, Exercises cwiczenie, Workouts trening) {
        this.belongs_id = belongs_id;
        this.cwiczenie = cwiczenie;
        this.trening = trening;
    }

    public Belongs_to(Exercises cwiczenie, Workouts trening) {
        this.belongs_id = belongs_id;
        this.cwiczenie = cwiczenie;
        this.trening = trening;
    }

    public int getBelongs_id() {
        return belongs_id;
    }

    public void setBelongs_id(int belongs_id) {
        this.belongs_id = belongs_id;
    }

    public Exercises getCwiczenie() {
        return cwiczenie;
    }

    public void setCwiczenie(Exercises cwiczenie) {
        this.cwiczenie = cwiczenie;
    }

    public Workouts getTrening() {
        return trening;
    }

    public void setTrening(Workouts trening) {
        this.trening = trening;
    }
}
