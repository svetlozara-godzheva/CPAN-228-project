package com.example.eduplatform.repositories;

import com.example.eduplatform.models.CourseGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseGradeRepository extends JpaRepository<CourseGrade, Long> {
}
