package ru.mtuci.simpleapi.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mtuci.simpleapi.dao.GroupRepository;
import ru.mtuci.simpleapi.dto.GroupDTO;
import ru.mtuci.simpleapi.model.Group;
import ru.mtuci.simpleapi.service.GroupService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = GroupController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController {
    public final static String REST_URL = "/api/v1/groups";

    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService, GroupRepository groupRepository) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<GroupDTO> getAll() {
        log.info("get all groups");
        return groupService.getAll();
    }

}
