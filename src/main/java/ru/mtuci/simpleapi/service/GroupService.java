package ru.mtuci.simpleapi.service;

import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.model.Group;

import java.util.List;

public interface GroupService {

    Group get(Long id);

    List<GroupDTO> getAll();
}
