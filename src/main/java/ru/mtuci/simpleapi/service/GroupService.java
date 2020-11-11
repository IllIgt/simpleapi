package ru.mtuci.simpleapi.service;

import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.dto.StudentDTO;
import ru.mtuci.simpleapi.model.Group;
import ru.mtuci.simpleapi.model.Student;

import java.util.List;

public interface GroupService {

    GroupDTO get(Long id);

    List<GroupDTO> getAll();

    GroupDTO save(GroupDTO group);

    List<StudentDTO> getGroupStudents(Long id);

    void delete(Long id);
}
