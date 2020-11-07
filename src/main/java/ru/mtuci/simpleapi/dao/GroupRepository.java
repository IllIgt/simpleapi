package ru.mtuci.simpleapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.mtuci.simpleapi.model.Group;

@Transactional
public interface GroupRepository extends JpaRepository<Group, Long>{

}