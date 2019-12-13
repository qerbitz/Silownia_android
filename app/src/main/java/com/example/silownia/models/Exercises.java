package com.example.silownia.models;

public class Exercises {
    private int Exercise_id;
    private String Exercise_name;
    private String Exercise_category;

    public Exercises(int exercise_id, String exercise_name, String exercise_category) {
        Exercise_id = exercise_id;
        Exercise_category = exercise_category;
        Exercise_name = exercise_name;
    }

    public Exercises(String exercise_name, String exercise_category) {
        Exercise_category = exercise_category;
        Exercise_name = exercise_name;
    }

    public Exercises(String exercise_name) {
        Exercise_name = exercise_name;
    }

    public Exercises(int string, String string1, boolean b) {

    }

    public int getExercise_id() {
        return Exercise_id;
    }

    public void setExercise_id(int exercise_id) {
        Exercise_id = exercise_id;
    }

    public String getExercise_category() {
        return Exercise_category;
    }

    public void setExercise_category(String exercise_category) {
        Exercise_category = exercise_category;
    }

    public String getExercise_name() {
        return Exercise_name;
    }

    public void setExercise_name(String exercise_name) {
        Exercise_name = exercise_name;
    }
}
