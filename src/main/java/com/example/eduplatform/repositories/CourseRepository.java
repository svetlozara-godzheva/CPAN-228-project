package com.example.eduplatform.repositories;

import com.example.eduplatform.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Course> findByCodeContainingIgnoreCase(String code, Pageable pageable);
}
