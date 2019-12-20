package com.example.silownia.models;

public class Log_Entries {
    private int Entries_id;
    private Log_performed trening;
    private int Set_number;
    private float Weight;
    private int Reps;


    public Log_Entries(int entries_id, Log_performed trening, int set_number, float weight, int reps) {
        Entries_id = entries_id;
        this.trening = trening;
        Set_number = set_number;
        Weight = weight;
        Reps = reps;
    }

    public int getEntries_id() {
        return Entries_id;
    }

    public void setEntries_id(int entries_id) {
        Entries_id = entries_id;
    }

    public Log_performed getTrening() {
        return trening;
    }

    public void setTrening(Log_performed trening) {
        this.trening = trening;
    }

    public int getSet_number() {
        return Set_number;
    }

    public void setSet_number(int set_number) {
        Set_number = set_number;
    }

    public float getWeight() {
        return Weight;
    }

    public void setWeight(float weight) {
        Weight = weight;
    }

    public int getReps() {
        return Reps;
    }

    public void setReps(int reps) {
        Reps = reps;
    }

}
