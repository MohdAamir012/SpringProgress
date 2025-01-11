package com.jvm.Week5.Service;

import com.jvm.Week5.Entity.Project;
import com.jvm.Week5.Repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectService {
    @Autowired
    private ProjectRepo projectRepository;

    public void saveProject(Project projectObj) {
        projectRepository.save(projectObj);
    }

    public void deleteProject(Integer projectId) {
        projectRepository.deleteById(projectId);
    }
}
