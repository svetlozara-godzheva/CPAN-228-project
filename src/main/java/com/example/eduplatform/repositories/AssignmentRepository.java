package com.example.eduplatform.repositories;

import com.example.eduplatform.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("SELECT a FROM Assignment a WHERE a.dueDate < :dateTime ORDER BY a.dueDate ASC")
    List<Assignment> findAssignmentsToGrade(@Param("dateTime") LocalDateTime dateTime);
}
