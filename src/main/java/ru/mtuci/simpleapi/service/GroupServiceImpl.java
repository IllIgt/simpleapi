package ru.mtuci.simpleapi.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mtuci.simpleapi.dao.GroupRepository;
import ru.mtuci.simpleapi.dao.StudentRepository;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.dto.StudentDTO;
import ru.mtuci.simpleapi.mapper.GroupMapper;
import ru.mtuci.simpleapi.model.Group;
import ru.mtuci.simpleapi.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GroupServiceImpl(
            GroupRepository groupRepository,
            ModelMapper modelMapper,
            StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(new GroupMapper());
    }

    @Override
    public GroupDTO get(Long id) {
        //  TODO add null Handling
        return modelMapper.map(groupRepository.findById(id).orElse(new Group()), GroupDTO.class);
    }

    @Override
    public List<GroupDTO> getAll() {
        //  TODO add null Handling
        List<Group> groups = groupRepository.findAll();
        return groups.stream()
                .map(group -> modelMapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public GroupDTO save(GroupDTO groupDTO) {
        Group group = modelMapper.map(groupDTO, Group.class);
        group.setStudents(new ArrayList<>());
        return modelMapper.map(groupRepository.save(group), GroupDTO.class);
    }

    @Override
    public List<StudentDTO> getGroupStudents(Long id) {
        //  TODO add null Handling
        GroupDTO groupDTO = modelMapper.map(groupRepository.findById(id).orElse(new Group()), GroupDTO.class);
        return groupDTO.getStudents();
    }

    @Override
    public void delete(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        if(group.isPresent()){
            List<Long> studentIds = group.get()
                    .getStudents()
                    .stream()
                    .map(Student::getId)
                    .collect(Collectors.toList());

            log.info("delete related students");
            studentRepository.delete(studentIds);

            log.info("delete delete group with id" + id);
            groupRepository.delete(id);
        }
        log.warn("group isn't present");
        //  TODO add Exceptions Handling
    }

}
