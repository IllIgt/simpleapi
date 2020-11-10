package ru.mtuci.simpleapi.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mtuci.simpleapi.dao.GroupRepository;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.mapper.GroupMapper;
import ru.mtuci.simpleapi.mapper.StudentMapper;
import ru.mtuci.simpleapi.model.Group;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, ModelMapper modelMapper) {

        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(new GroupMapper());
    }

    @Override
    public Group get(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    @Override
    public List<GroupDTO> getAll() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(group -> modelMapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());
    }

}
