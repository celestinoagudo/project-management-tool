package com.ican.code.projectmanagement.services;

import com.ican.code.projectmanagement.constants.ErrorMessages;
import com.ican.code.projectmanagement.constants.Priority;
import com.ican.code.projectmanagement.constants.Status;
import com.ican.code.projectmanagement.domain.Backlog;
import com.ican.code.projectmanagement.domain.ProjectTask;
import com.ican.code.projectmanagement.exceptions.ProjectManagementException;
import com.ican.code.projectmanagement.repositories.BacklogRepository;
import com.ican.code.projectmanagement.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.ican.code.projectmanagement.constants.ErrorMessages.PROJECT_IDENTIFIER_NOT_FOUND;
import static com.ican.code.projectmanagement.constants.ErrorMessages.PROJECT_SEQUENCE_NOT_FOUND;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class ProjectTaskService {

  private final BacklogRepository backlogRepository;
  private final ProjectTaskRepository projectTaskRepository;

  @Autowired
  public ProjectTaskService(
      final BacklogRepository backlogRepository,
      final ProjectTaskRepository projectTaskRepository) {

    this.backlogRepository = backlogRepository;
    this.projectTaskRepository = projectTaskRepository;
  }

  public ProjectTask saveProjectTask(
      final String projectIdentifier, final ProjectTask projectTask) {

    final Backlog backlog = getBacklogByProjectIdentifier(projectIdentifier);
    projectTask.setBacklog(backlog);
    int projectTaskSequence =
        isNull(backlog.getProjectTaskSequence()) ? 1 : backlog.getProjectTaskSequence() + 1;
    backlog.setProjectTaskSequence(projectTaskSequence);
    projectTask.setProjectSequence(format(projectIdentifier.concat("-%s"), projectTaskSequence));
    projectTask.setProjectIdentifier(projectIdentifier);
    if (isNull(projectTask.getPriority()) || Objects.equals(projectTask.getPriority(), 0))
      projectTask.setPriority(Priority.LOW.key);

    if (isBlank(projectTask.getStatus())) projectTask.setStatus(Status.TODO.key);

    return projectTaskRepository.save(projectTask);
  }

  private Backlog getBacklogByProjectIdentifier(final String projectIdentifier) {

    return backlogRepository
        .findByProjectIdentifier(projectIdentifier)
        .orElseThrow(
            () ->
                new ProjectManagementException(
                    format(PROJECT_IDENTIFIER_NOT_FOUND, projectIdentifier)));
  }

  public List<ProjectTask> getByProjectIdentifierOrderByPriority(final String projectIdentifier) {

    return projectTaskRepository.findByProjectIdentifierOrderByPriority(
        getBacklogByProjectIdentifier(projectIdentifier).getProjectIdentifier());
  }

  public ProjectTask updateByProjectSequence(
      final String projectSequence,
      final String projectIdentifier,
      final ProjectTask updatedProjectTask) {

    final ProjectTask existingProjectTask =
        getByProjectSequence(projectIdentifier, projectSequence);
    updatedProjectTask.setId(existingProjectTask.getId());

    return projectTaskRepository.save(updatedProjectTask);
  }

  public ProjectTask getByProjectSequence(
      final String projectIdentifier, final String projectSequence) {

    final Backlog backlog = getBacklogByProjectIdentifier(projectIdentifier);
    final ProjectTask projectTask = getProjectTaskByProjectSequence(projectSequence);
    final String projectTaskProjectIdentifier = projectTask.getProjectIdentifier();

    if (Objects.equals(backlog.getProjectIdentifier(), projectTaskProjectIdentifier))
      return projectTask;

    throw new ProjectManagementException(
        format(
            ErrorMessages.PT_SEQUENCE_NOT_IN_BACKLOG,
            projectTask.getProjectSequence(),
            backlog.getProjectIdentifier()));
  }

  private ProjectTask getProjectTaskByProjectSequence(final String projectSequence) {

    return projectTaskRepository
        .findByProjectSequence(projectSequence)
        .orElseThrow(
            () ->
                new ProjectManagementException(
                    format(PROJECT_SEQUENCE_NOT_FOUND, projectSequence)));
  }

  public ProjectTask deleteProjectTask(String projectIdentifier, String projectSequence) {

    ProjectTask projectTask = getByProjectSequence(projectIdentifier, projectSequence);
    projectTaskRepository.delete(projectTask);

    return projectTask;
  }
}
