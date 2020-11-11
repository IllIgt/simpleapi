package ru.mtuci.simpleapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.mtuci.simpleapi.model.Group;

@Transactional
public interface GroupRepository extends JpaRepository<Group, Long>{
    @Transactional
    @Modifying
    @Query("DELETE FROM Group g WHERE g.id=:id")
    int delete(@Param("id") Long id);
}