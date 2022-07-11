package com.ican.code.projectmanagement.services;

import com.ican.code.projectmanagement.constants.Priority;
import com.ican.code.projectmanagement.constants.Status;
import com.ican.code.projectmanagement.domain.Backlog;
import com.ican.code.projectmanagement.domain.ProjectTask;
import com.ican.code.projectmanagement.exceptions.ProjectManagementException;
import com.ican.code.projectmanagement.repositories.BacklogRepository;
import com.ican.code.projectmanagement.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.ican.code.projectmanagement.constants.ErrorMessages.PROJECT_IDENTIFIER_NOT_FOUND;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

@Service
public class ProjectTaskService {

  private final BacklogRepository backlogRepository;
  private final ProjectTaskRepository projectTaskRepository;

  @Autowired
  public ProjectTaskService(
      BacklogRepository backlogRepository, ProjectTaskRepository projectTaskRepository) {

    this.backlogRepository = backlogRepository;
    this.projectTaskRepository = projectTaskRepository;
  }

  public ProjectTask saveProjectTask(
      final String projectIdentifier, final ProjectTask projectTask) {

    final Backlog backlog = getBacklog(projectIdentifier);
    projectTask.setBacklog(backlog);
    int backlogSequence =
        isNull(backlog.getProjectTaskSequence()) ? 0 : backlog.getProjectTaskSequence();
    projectTask.setProjectSequence(
        format(projectIdentifier.concat("-%s"), valueOf(++backlogSequence)));
    projectTask.setProjectIdentifier(projectIdentifier);
    if (isNull(projectTask.getPriority()) || Objects.equals(projectTask.getPriority(), 0))
      projectTask.setPriority(Priority.LOW.key);

    if (isBlank(projectTask.getStatus())) projectTask.setStatus(Status.TODO.key);

    return projectTaskRepository.save(projectTask);
  }

  private Backlog getBacklog(final String projectIdentifier) {

    return backlogRepository
        .findByProjectIdentifier(projectIdentifier)
        .orElseThrow(
            () ->
                new ProjectManagementException(
                    format(PROJECT_IDENTIFIER_NOT_FOUND, projectIdentifier)));
  }
}
