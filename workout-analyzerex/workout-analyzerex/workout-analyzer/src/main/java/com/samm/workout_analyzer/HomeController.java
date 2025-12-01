package com.samm.workout_analyzer;
//imports the annotations @RestController and @Getmapping
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController 
// tells Spring this class is supposed to handle web requests and makes it so the class responds when someone visits the site.
// tells Spring methods in this class should be just retrurnig data directly, not no HTML page.
public class HomeController {

    @GetMapping("/") // handles GET requests tells Spring if somone goes to http://localhost:8080/ (aka home) run the method below 
    public String home() {
        return "Workout Analyzer is up and running!";
    }
    
}
