package com.example.school.controller;

import com.example.school.controller.util.Response;
import com.example.school.dto.GroupDto;
import com.example.school.dto.input.AddGroupDto;
import com.example.school.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@Tag(name = "groups", description = "Operations on groups")

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Operation(summary = "List all groups.")
    @ApiResponse(responseCode = "200", description = "Found all groups.")
    @GetMapping("groups")
    public List<GroupDto> getGroups() {
        return groupService.getAll();
    }

    @Operation(summary = "Add new group.")
    @ApiResponse(responseCode = "200", description = "New group successfully added.")
    @ApiResponse(responseCode = "404", description = "School not found.")
    @PostMapping("group")
    public Response addGroup(@RequestBody AddGroupDto addGroupDto) {
        Response response = new Response();
        try {
            response.setContent(groupService.add(addGroupDto));
        } catch (NotFoundException e) {
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Operation(summary = "List all students in group.")
    @ApiResponse(responseCode = "200", description = "Found all students.")
    @ApiResponse(responseCode = "404", description = "Group not found.")
    @GetMapping("groups/{group}/students")
    public Response getStudentsFromGroup(@Parameter(description = "Group name") @PathVariable("group") String groupName) {
        Response response = new Response();
        try {
            response.setContent(groupService.getStudents(groupName));
        } catch (NotFoundException e) {
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
}