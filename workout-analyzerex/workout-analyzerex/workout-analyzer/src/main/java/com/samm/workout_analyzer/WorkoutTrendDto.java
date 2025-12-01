package com.samm.workout_analyzer;

import java.time.LocalDate;

public class WorkoutTrendDto {
    private LocalDate weekStart;
    private double totalVolume;

    public WorkoutTrendDto(LocalDate weekStart, double totalVolume) {
        this.weekStart = weekStart;
        this.totalVolume = totalVolume;
    }

    public LocalDate getWeekStart() { return weekStart; }
    public double getTotalVolume() { return totalVolume; }
}
