package com.ican.code.projectmanagement.services;

import com.ican.code.projectmanagement.domain.Project;
import com.ican.code.projectmanagement.exceptions.ProjectManagementException;
import com.ican.code.projectmanagement.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ican.code.projectmanagement.constants.ErrorMessages.PROJECT_IDENTIFIER_EXISTS;
import static com.ican.code.projectmanagement.constants.ErrorMessages.PROJECT_IDENTIFIER_NOT_FOUND;
import static java.lang.String.format;

@Service
public class ProjectService {

  private final ProjectRepository projectRepository;

  @Autowired
  public ProjectService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  public Project findByProjectIdentifier(String projectIdentifier) {

    return getProjectByIdentifier(projectIdentifier);
  }

  public List<Project> findAll() {

    return projectRepository.findAll();
  }

  public Project deleteProjectByIdentifier(String projectIdentifier) {

    Project project = getProjectByIdentifier(projectIdentifier);
    projectRepository.deleteById(project.getId());

    return project;
  }

  public Project saveOrUpdateProject(Project project) {

    projectRepository
        .findByProjectIdentifier(project.getProjectIdentifier())
        .ifPresent(
            existingProject -> {
              throw new ProjectManagementException(
                  format(PROJECT_IDENTIFIER_EXISTS, existingProject.getProjectIdentifier()));
            });

    return projectRepository.save(project);
  }

  private Project getProjectByIdentifier(String projectIdentifier) {

    return projectRepository
        .findByProjectIdentifier(projectIdentifier)
        .orElseThrow(
            () ->
                new ProjectManagementException(
                    format(PROJECT_IDENTIFIER_NOT_FOUND, projectIdentifier)));
  }
}
