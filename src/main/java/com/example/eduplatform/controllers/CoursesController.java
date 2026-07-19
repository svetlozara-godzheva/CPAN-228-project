package com.example.eduplatform.controllers;

import com.example.eduplatform.models.Course;
import com.example.eduplatform.repositories.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
public class CoursesController {

    private final CourseRepository courseRepository;

    public CoursesController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    public String displayCourses(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 @RequestParam(defaultValue = "name") String sort,
                                 @RequestParam(defaultValue = "ASC") String direction,
                                 @RequestParam(required = false) String search,
                                 @RequestParam(defaultValue = "all") String filterType
    ) {

        Sort.Direction sortDir = Sort.Direction.fromString(direction);

        String[] sortValues = {"name", "startDate"};
        if (!Arrays.asList(sortValues).contains(sort)) {
            sort = "name";
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir, sort));

        Page<Course> coursePage;

        if (filterType.equalsIgnoreCase("name") && search != null && !search.trim().isEmpty()) {
            coursePage = courseRepository.findByNameContainingIgnoreCase(search.trim(), pageable);
        } else if (filterType.equalsIgnoreCase("code") && search != null && !search.trim().isEmpty()) {
            coursePage = courseRepository.findByCodeContainingIgnoreCase(search.trim(), pageable);
        } else {
            coursePage = courseRepository.findAll(pageable);
        }

        model.addAttribute("courses", coursePage.getContent());
        model.addAttribute("totalPages", coursePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("hasPrevious", coursePage.hasPrevious());
        model.addAttribute("hasNext", coursePage.hasNext());
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("filterType", filterType);

        return "courses";
    }
}
