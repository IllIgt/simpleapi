package ru.mtuci.simpleapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mtuci.simpleapi.dao.GroupRepository;
import ru.mtuci.simpleapi.model.Group;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group get(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    @Override
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

}
