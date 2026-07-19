package com.example.eduplatform.controllers;

import com.example.eduplatform.models.Assignment;
import com.example.eduplatform.repositories.AssignmentRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;

    public AssignmentController(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @GetMapping("/assignments/{id}")
    public String displayAssignment(@PathVariable Long id, Model model) {
        Optional<Assignment> assignment = assignmentRepository.findById(id);
        if (assignment.isEmpty()) {
            return "redirect:/courses";
        }
        model.addAttribute("assignment", assignment.get());
        return "assignment-preview";
    }

    @GetMapping("/assignments/create")
    public String displayCreateAssignment(@RequestParam Long courseId, Model model) {
        Assignment assignment = new Assignment();
        assignment.setCourseId(courseId);
        model.addAttribute("assignment", assignment);
        return "create-assignment";
    }

    @PostMapping("/assignments/create")
    public String createAssignment(@Valid @ModelAttribute Assignment assignment, BindingResult result) {
        if (result.hasErrors()) {
            return "create-assignment";
        }
        Assignment saved = assignmentRepository.save(assignment);
        return "redirect:/assignments/" + saved.getId();
    }

    @GetMapping("/assignments/{id}/edit")
    public String displayEditAssignment(@PathVariable Long id, Model model) {
        Optional<Assignment> assignment = assignmentRepository.findById(id);
        if (assignment.isEmpty()) {
            return "redirect:/courses";
        }
        model.addAttribute("assignment", assignment.get());
        return "edit-assignment";
    }

    @PostMapping("/assignments/{id}/edit")
    public String editAssignment(@PathVariable Long id, @Valid @ModelAttribute Assignment assignment,
                                 BindingResult result) {
        assignment.setId(id);
        if (result.hasErrors()) {
            return "edit-assignment";
        }
        assignmentRepository.save(assignment);
        return "redirect:/assignments/" + id;
    }
}