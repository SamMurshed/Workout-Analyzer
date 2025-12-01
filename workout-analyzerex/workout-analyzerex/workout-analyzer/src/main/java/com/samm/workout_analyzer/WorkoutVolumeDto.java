package com.samm.workout_analyzer;

import java.time.LocalDate;

public class WorkoutVolumeDto {
    private Long id;
    private String exercise;
    private LocalDate date;
    private double volume;

    public WorkoutVolumeDto(Long id, String exercise, LocalDate date, double volume) {
        this.id = id;
        this.exercise = exercise;
        this.date = date;
        this.volume = volume;
    }

    public Long getId() { 
        return id;
    }
    public String getExercise() { 
        return exercise; 
    }
    public LocalDate getDate() { 
        return date; 
    }
    public double getVolume() { 
        return volume; 
    }
}
