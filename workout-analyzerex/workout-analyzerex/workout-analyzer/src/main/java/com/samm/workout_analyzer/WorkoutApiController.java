package com.samm.workout_analyzer;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
//takes in reqs and turns into entries 
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;


@RestController
@RequestMapping("/api/workouts")
public class WorkoutApiController {

    private final WorkoutEntryRepository repository;

    @Autowired
    public WorkoutApiController(WorkoutEntryRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public WorkoutEntry createWorkout(@RequestBody WorkoutEntry entry) {
        return repository.save(entry);
    }

    @GetMapping
    public List<WorkoutEntry> getAllWorkouts() {
        return repository.findAll();
    }
    
    @GetMapping("/analysis/volume")
    public List<WorkoutVolumeDto> getWorkoutVolumes() {
        return repository.findAll()  // fetch all entries
            .stream()
            .map(e -> new WorkoutVolumeDto(
                e.getId(),
                e.getExercise(),
                e.getDate(),
                e.getSets() * e.getReps() * e.getWeight()
            ))
            .collect(Collectors.toList());
    }

    @GetMapping("/analysis/summary")
    public List<WorkoutSummaryDto> getVolumeSummary(
        @RequestParam(required = false) String start,
        @RequestParam(required = false) String end
    ) {
        // Parse or default to full range
        LocalDate startDate = (start != null)
            ? LocalDate.parse(start)
            : LocalDate.of(1970, 1, 1);
        LocalDate endDate = (end != null)
            ? LocalDate.parse(end)
            : LocalDate.now().plusYears(100);  // far future

        // Fetch only entries in that range
        List<WorkoutEntry> entries = repository.findByDateBetween(startDate, endDate);

        // Summarize the volume per exercise
        return entries.stream()
            .collect(Collectors.groupingBy(
                WorkoutEntry::getExercise,
                Collectors.summingDouble(e -> e.getSets() * e.getReps() * e.getWeight())
            ))
            .entrySet().stream()
            .map(e -> new WorkoutSummaryDto(e.getKey(), e.getValue()))
            .collect(Collectors.toList());
    }


    @GetMapping("/analysis/trend")
    public List<WorkoutTrendDto> getWeeklyTrend(
        @RequestParam(required = false) String start,
        @RequestParam(required = false) String end
    ) {
        LocalDate startDate = (start != null)
            ? LocalDate.parse(start)
            : LocalDate.of(1970,1,1);
        LocalDate endDate = (end != null)
            ? LocalDate.parse(end)
            : LocalDate.now().plusYears(100);

        // Fetch entries in range
        List<WorkoutEntry> entries = repository.findByDateBetween(startDate, endDate);

        // Group by ISO week number, then sum volume
        Map<Integer, Double> volumeByWeek = entries.stream()
            .collect(Collectors.groupingBy(
                e -> e.getDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR),
                Collectors.summingDouble(e -> e.getSets() * e.getReps() * e.getWeight())
            ));

        // Map each week number back to a LocalDate (week start, Monday)
        return volumeByWeek.entrySet().stream()
            .map(e -> {
                // Year-week to date: find the Monday of that ISO week in the entry's year
                // use the year of startDate for simplicity; for mixed years, you can extend logic.
                LocalDate any = entries.stream()
                    .filter(x -> x.getDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == e.getKey())
                    .findFirst()
                    .get()
                    .getDate();
                int week = e.getKey();
                LocalDate weekStart = any
                    .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, week)
                    .with(java.time.DayOfWeek.MONDAY);
                return new WorkoutTrendDto(weekStart, e.getValue());
            })
            .sorted((a,b) -> a.getWeekStart().compareTo(b.getWeekStart()))
            .collect(Collectors.toList());
    }



}

