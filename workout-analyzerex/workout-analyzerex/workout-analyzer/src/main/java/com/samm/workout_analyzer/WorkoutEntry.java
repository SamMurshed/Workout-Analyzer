package com.samm.workout_analyzer;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity //tells Spring this a database table Spring Boot and H2 
//creates a table upon running based on this class
public class WorkoutEntry {
    @Id
    @GeneratedValue
    private Long Id; //autogeneerates primary key aka Id for ID for each workout
    
    private String exercise;
    private int sets;
    private int reps;
    private double weight;
    private LocalDate date;

    //Constructors
    public WorkoutEntry(){}
    public WorkoutEntry(String exercise, int sets, int reps, double weight, LocalDate date){
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.date = date;
    }
    //Getters + Setters
    public Long getId(){ return Id;}
    public String getExercise(){ return exercise;}
    public void setExercise(String exercise){ this.exercise = exercise;}
    public int getSets(){ return sets;}
    public void setSets(int sets){ this.sets = sets;}
    public int getReps(){ return reps;}
    public void setReps(int reps) { this.reps = reps;}
    public double getWeight(){ return this.weight;}
    public void setWeight(double weight){ this.weight = weight;}
    public LocalDate getDate(){ return date;}
    public void setDate(LocalDate date){ this.date = date;}


}
