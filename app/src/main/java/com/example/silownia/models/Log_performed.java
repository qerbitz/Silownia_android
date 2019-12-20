package com.example.silownia.models;

import java.sql.Date;

public class Log_performed {
    private int Log_id;
    private Workouts trening;
    private Date data;

    public Log_performed(int log_id, Workouts trening, Date data) {
        Log_id = log_id;
        this.trening = trening;
        this.data = data;
    }

    public Log_performed(Workouts trening, Date data) {
        this.trening = trening;
        this.data = data;
    }

    public Log_performed(Date data) {
        this.data = data;
    }


    public int getLog_id() {
        return Log_id;
    }

    public void setLog_id(int log_id) {
        Log_id = log_id;
    }

    public Workouts getCwiczenie() {
        return trening;
    }

    public void setCwiczenie(Workouts cwiczenie) {
        this.trening = cwiczenie;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
