package com.example.silownia.models;

public class Targets {
    private int Targets_id;
    private Belongs_to nalezy;
    private int Set_number;
    private int Min_reps;
    private int Max_reps;

    public Targets(int targets_id, Belongs_to nalezy, int set_number, int min_reps, int max_reps) {
        Targets_id = targets_id;
        this.nalezy = nalezy;
        Set_number = set_number;
        Min_reps = min_reps;
        Max_reps = max_reps;
    }

    public Targets(Belongs_to nalezy, int set_number, int min_reps, int max_reps) {
        this.nalezy = nalezy;
        Set_number = set_number;
        Min_reps = min_reps;
        Max_reps = max_reps;
    }

    public int getTargets_id() {
        return Targets_id;
    }

    public void setTargets_id(int targets_id) {
        Targets_id = targets_id;
    }

    public Belongs_to getNalezy() {
        return nalezy;
    }

    public void setNalezy(Belongs_to nalezy) {
        this.nalezy = nalezy;
    }

    public int getSet_number() {
        return Set_number;
    }

    public void setSet_number(int set_number) {
        Set_number = set_number;
    }

    public int getMin_reps() {
        return Min_reps;
    }

    public void setMin_reps(int min_reps) {
        Min_reps = min_reps;
    }

    public int getMax_reps() {
        return Max_reps;
    }

    public void setMax_reps(int max_reps) {
        Max_reps = max_reps;
    }
}
