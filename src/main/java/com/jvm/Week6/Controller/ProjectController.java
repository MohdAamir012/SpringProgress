package com.jvm.Week6.Controller;

import com.jvm.Week6.Entity.Project;
import com.jvm.Week6.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity createProject(@RequestBody Project projectObj) {
        projectService.saveProject(projectObj);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}

