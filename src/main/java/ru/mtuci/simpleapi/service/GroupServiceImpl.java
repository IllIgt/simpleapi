package ru.mtuci.simpleapi.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mtuci.simpleapi.dao.CourseRepository;
import ru.mtuci.simpleapi.dao.GroupRepository;
import ru.mtuci.simpleapi.dao.StudentRepository;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.dto.StudentDTO;
import ru.mtuci.simpleapi.mapper.GroupMapper;
import ru.mtuci.simpleapi.model.Course;
import ru.mtuci.simpleapi.model.Group;
import ru.mtuci.simpleapi.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;


    @Autowired
    public GroupServiceImpl(
            GroupRepository groupRepository,
            ModelMapper modelMapper,
            StudentRepository studentRepository,
            CourseRepository courseRepository) {

        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.courseRepository = courseRepository;
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
        List<Student> students = new ArrayList<>();
        List<Course> courses = new ArrayList<>();

        if (!groupDTO.getStudentsId().isEmpty()) {
            students = studentRepository.findAllById(groupDTO.getStudentsId());
        }
        if (!groupDTO.getCoursesId().isEmpty()) {
            courses = courseRepository.findAllById(groupDTO.getCoursesId());
        }
        Group group = modelMapper.map(groupDTO, Group.class);
        group.setStudents(students);
        group.setCourses(courses);

        Long groupId = groupRepository.save(group).getId();

        for(Student student : group.getStudents()){
            if (student.getGroup() == null || !student.getGroup().getId().equals(groupId)) {
                student.setGroup(group);
            }
        }
        studentRepository.saveAll(group.getStudents());

        for(Course course: group.getCourses()){
            List<Group> groups = course.getGroups();
            if (!groups.contains(group)) {
                groups.add(group);
                course.setGroups(groups);
            }
        }
        courseRepository.saveAll(group.getCourses());

        return modelMapper.map(group, GroupDTO.class);
    }

    @Override
    public List<StudentDTO> getGroupStudents(Long id) {
        //  TODO add null Handling
        Group group = groupRepository.findById(id).orElse(new Group());
        List<StudentDTO> students = new ArrayList<>();
        for (final Student student : group.getStudents()) {
            students.add(modelMapper.map(student, StudentDTO.class));
        }
        return students;
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
