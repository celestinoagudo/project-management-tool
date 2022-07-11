package com.ican.code.projectmanagement.services;

import com.google.gson.Gson;
import com.ican.code.projectmanagement.domain.Backlog;
import com.ican.code.projectmanagement.domain.Project;
import com.ican.code.projectmanagement.exceptions.ProjectManagementException;
import com.ican.code.projectmanagement.repositories.BacklogRepository;
import com.ican.code.projectmanagement.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

import static com.ican.code.projectmanagement.constants.ErrorMessages.PROJECT_IDENTIFIER_EXISTS;
import static com.ican.code.projectmanagement.constants.ErrorMessages.PROJECT_IDENTIFIER_NOT_FOUND;
import static java.lang.Long.MIN_VALUE;
import static java.lang.String.format;
import static java.util.Objects.isNull;

@Service
public class ProjectService {

  private final ProjectRepository projectRepository;
  private final BacklogRepository backlogRepository;

  @Autowired
  public ProjectService(ProjectRepository projectRepository, BacklogRepository backlogRepository) {
    this.projectRepository = projectRepository;
    this.backlogRepository = backlogRepository;
  }

  public Project findByProjectIdentifier(final String projectIdentifier) {

    return getProjectByIdentifierStrict(projectIdentifier);
  }

  public List<Project> findAll() {

    return projectRepository.findAll();
  }

  public Project deleteProjectByIdentifier(final String projectIdentifier) {

    Project project = getProjectByIdentifierStrict(projectIdentifier);
    projectRepository.deleteById(project.getId());

    return project;
  }

  public Project saveOrUpdateProject(final Project project) {

    UnaryOperator<Project> validateAndSave =
        projectToSave -> {
          projectToSave.setProjectIdentifier(projectToSave.getProjectIdentifier().toUpperCase());
          final Project existingProject =
              getProjectByIdentifierPermissive(projectToSave.getProjectIdentifier());

          if (!isNull(existingProject)
              && !Objects.equals(existingProject.getId(), project.getId())) {
            throw new ProjectManagementException(
                format(PROJECT_IDENTIFIER_EXISTS, existingProject.getProjectIdentifier()));
          }

          return projectRepository.save(getProjectWithBacklogAssociation(projectToSave));
        };

    return validateAndSave.apply(project);
  }

  private Project getProjectByIdentifierStrict(final String projectIdentifier) {

    return projectRepository
        .findByProjectIdentifier(projectIdentifier)
        .orElseThrow(
            () ->
                new ProjectManagementException(
                    format(PROJECT_IDENTIFIER_NOT_FOUND, projectIdentifier)));
  }

  private Project getProjectByIdentifierPermissive(final String projectIdentifier) {

    return projectRepository.findByProjectIdentifier(projectIdentifier).orElse(null);
  }

  private Backlog getBacklogByIdentifier(final Project projectToSave) {

    return backlogRepository
        .findByProjectIdentifier(projectToSave.getProjectIdentifier())
        .orElseGet(
            () ->
                Backlog.builder().projectIdentifier(projectToSave.getProjectIdentifier()).build());
  }

  private Project getProjectWithBacklogAssociation(final Project projectToSave) {

    final Project project =
        projectRepository
            .findById(isNull(projectToSave.getId()) ? MIN_VALUE : projectToSave.getId())
            .orElseGet(
                () -> {
                  Gson cloneUtility = new Gson();
                  return cloneUtility.fromJson(cloneUtility.toJson(projectToSave), Project.class);
                });

    final Backlog backlog = getBacklogByIdentifier(projectToSave);
    project.setBacklog(backlog);
    backlog.setProject(project);

    return project;
  }
}
