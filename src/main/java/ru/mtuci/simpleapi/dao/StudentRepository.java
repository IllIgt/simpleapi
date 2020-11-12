package ru.mtuci.simpleapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.mtuci.simpleapi.model.Student;

import java.util.List;


public interface StudentRepository extends JpaRepository <Student, Long> {
    @Modifying
    @Query("DELETE FROM Student s WHERE s.id=:id")
    int delete(@Param("id") Long id);

    @Modifying
    @Query("DELETE FROM Student s WHERE s.id in (:studentsIds)")
    int delete(@Param("studentsIds") List<Long> studentsIds);
}
