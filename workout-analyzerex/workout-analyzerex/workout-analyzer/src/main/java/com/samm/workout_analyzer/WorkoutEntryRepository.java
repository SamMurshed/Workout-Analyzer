package com.samm.workout_analyzer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

//Database helper file using Spring, allowing to save data, find it, update and delete
public interface WorkoutEntryRepository extends JpaRepository<WorkoutEntry, Long> {
    List<WorkoutEntry> findByDateBetween(LocalDate start, LocalDate end);
}