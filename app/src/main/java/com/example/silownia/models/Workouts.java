package com.example.silownia.models;

public class Workouts {
    private int Workout_id;
    private String Workout_name;
    private String Workout_category;

    public Workouts(int workout_id, String workout_name, String workout_category) {
        Workout_id = workout_id;
        Workout_name = workout_name;
        Workout_category = workout_category;
    }


    public Workouts(String workout_name) {
        Workout_name = workout_name;
    }

    public int getWorkout_id() {
        return Workout_id;
    }

    public void setWorkout_id(int workout_id) {
        Workout_id = workout_id;
    }

    public String getWorkout_name() {
        return Workout_name;
    }

    public void setWorkout_name(String workout_name) {
        Workout_name = workout_name;
    }

    public String getWorkout_category() {
        return Workout_category;
    }

    public void setWorkout_category(String workout_category) {
        Workout_category = workout_category;
    }
}
