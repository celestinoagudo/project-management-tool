package com.ican.code.projectmanagement.services;

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

    UnaryOperator<Project> validateAndSave =
        projectToSave -> {
          projectToSave.setProjectIdentifier(projectToSave.getProjectIdentifier().toUpperCase());
          projectRepository
              .findByProjectIdentifier(projectToSave.getProjectIdentifier())
              .ifPresent(
                  existingProject -> {
                    if (Objects.equals(existingProject.getId(), project.getId())) return;
                    throw new ProjectManagementException(
                        format(PROJECT_IDENTIFIER_EXISTS, existingProject.getProjectIdentifier()));
                  });

          final Project projectWithBacklog =
              projectRepository
                  .findById(isNull(projectToSave.getId()) ? MIN_VALUE : projectToSave.getId())
                  .orElseGet(
                      () -> {
                        final Backlog backlog =
                            backlogRepository
                                .findByProjectIdentifier(projectToSave.getProjectIdentifier())
                                .orElseGet(
                                    () -> {
                                      return Backlog.builder()
                                          .project(projectToSave)
                                          .projectIdentifier(projectToSave.getProjectIdentifier())
                                          .build();
                                    });

                        final Project withBacklog =
                            Project.builder()
                                .id(projectToSave.getId())
                                .projectName(projectToSave.getProjectName())
                                .projectIdentifier(projectToSave.getProjectIdentifier())
                                .description(projectToSave.getDescription())
                                .startDate(projectToSave.getStartDate())
                                .endDate(projectToSave.getEndDate())
                                .createdAt(projectToSave.getCreatedAt())
                                .backlog(backlog)
                                .build();

                        backlog.setProject(withBacklog);

                        return withBacklog;
                      });

          return projectRepository.save(projectWithBacklog);
        };

    return validateAndSave.apply(project);
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
