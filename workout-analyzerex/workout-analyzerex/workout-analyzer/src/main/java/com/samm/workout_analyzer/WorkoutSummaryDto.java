package com.samm.workout_analyzer;

public class WorkoutSummaryDto {
    private String exercise;
    private double totalVolume;

    public WorkoutSummaryDto(String exercise, double totalVolume) {
        this.exercise = exercise;
        this.totalVolume = totalVolume;
    }

    public String getExercise() { 
        return exercise; 
    }
    public double getTotalVolume() { 
        return totalVolume; 
    }
}
