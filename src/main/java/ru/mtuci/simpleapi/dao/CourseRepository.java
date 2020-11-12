package ru.mtuci.simpleapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mtuci.simpleapi.model.Course;


public interface CourseRepository extends JpaRepository<Course, Long> {
    @Modifying
    @Query("DELETE FROM Course c WHERE c.id=:id")
    int delete(@Param("id") Long id);
}