package com.example.eduplatform.controllers;

import com.example.eduplatform.models.Course;
import com.example.eduplatform.repositories.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Optional;

@Controller
public class CoursesController {

    private static final Long DEFAULT_INSTRUCTOR_ID = 1L;

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
        model.addAttribute("totalElements", coursePage.getTotalElements());
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

    @GetMapping("/courses/{id}")
    public String previewCourse(@PathVariable Long id, Model model) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            return "redirect:/courses";
        }
        model.addAttribute("course", course.get());
        return "course-preview";
    }

    @GetMapping("/courses/create")
    public String displayCreateCourse(Model model) {
        model.addAttribute("course", new Course());
        return "create-course";
    }

    @PostMapping("/courses/create")
    public String createCourse(@Valid @ModelAttribute Course course, BindingResult result) {
        if (course.getStartDate() != null && course.getEndDate() != null && course.getEndDate().isBefore(course.getStartDate())) {
            result.rejectValue("endDate", "endDate.beforeStart", "Course end date cannot be before the start date");
        }
        if (result.hasErrors()) {
            return "create-course";
        }
        // for now hard code the user id
        course.setInstructorId(1l);
        Course saved = courseRepository.save(course);
        return "redirect:/courses/" + saved.getId();
    }

    @GetMapping("/courses/{id}/edit")
    public String displayEditForm(@PathVariable Long id, Model model) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            return "redirect:/courses";
        }
        model.addAttribute("course", course.get());
        return "edit-course";
    }

    @PostMapping("/courses/{id}/edit")
    public String editCourse(@PathVariable Long id, @Valid @ModelAttribute Course course, BindingResult result) {
        if (course.getStartDate() != null && course.getEndDate() != null && course.getEndDate().isBefore(course.getStartDate())) {
            result.rejectValue("endDate", "endDate.beforeStart", "Course end date cannot be before the start date");
        }
        if (result.hasErrors()) {
            course.setId(id);
            return "edit-course";
        }
        course.setId(id);
        courseRepository.save(course);
        return "redirect:/courses/" + id;
    }
}