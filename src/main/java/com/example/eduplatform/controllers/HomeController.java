package com.example.eduplatform.controllers;

import com.example.eduplatform.repositories.AssignmentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class HomeController {

    private final AssignmentRepository assignmentRepository;

    public HomeController(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @GetMapping("/")
    public String displayHomepage(Model model) {
        model.addAttribute("assignments",
                assignmentRepository.findAssignmentsToGrade(LocalDateTime.now()));
        return "home";
    }

}
