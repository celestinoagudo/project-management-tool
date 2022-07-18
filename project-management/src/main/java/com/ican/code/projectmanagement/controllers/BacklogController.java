package com.ican.code.projectmanagement.controllers;

import com.ican.code.projectmanagement.domain.CustomApplicationResponse;
import com.ican.code.projectmanagement.domain.ProjectTask;
import com.ican.code.projectmanagement.services.ApplicationResponseMappingService;
import com.ican.code.projectmanagement.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

  private final ProjectTaskService projectTaskService;
  private final ApplicationResponseMappingService applicationResponseMappingService;

  @Autowired
  public BacklogController(
      final ProjectTaskService projectTaskService,
      final ApplicationResponseMappingService applicationResponseMappingService) {

    this.projectTaskService = projectTaskService;
    this.applicationResponseMappingService = applicationResponseMappingService;
  }

  @PostMapping("/{projectIdentifier}")
  public ResponseEntity<CustomApplicationResponse> saveProjectTaskToBacklog(
      final @Valid @RequestBody ProjectTask projectTask,
      final BindingResult validationResult,
      final @PathVariable("projectIdentifier") String projectIdentifier) {

    return applicationResponseMappingService
        .getErrorMapping(validationResult)
        .orElseGet(
            () -> {
              final ProjectTask savedTask =
                  projectTaskService.saveProjectTask(projectIdentifier, projectTask);

              return applicationResponseMappingService.getApplicationResponse(
                  format(
                      "Task %s with identifier %s is successfully processed",
                      savedTask.getProjectSequence(), savedTask.getProjectIdentifier()),
                  savedTask,
                  OK);
            });
  }

  @GetMapping("/{projectIdentifier}")
  public ResponseEntity<CustomApplicationResponse> getProjectTasksByProjectIdentifier(
      final @PathVariable("projectIdentifier") String projectIdentifier) {

    return applicationResponseMappingService.getApplicationResponse(
        format("Tasks with project identifier %s is found", projectIdentifier),
        projectTaskService.getByProjectIdentifierOrderByPriority(projectIdentifier),
        FOUND);
  }

  @GetMapping("/{projectIdentifier}/{projectTaskSequence}")
  public ResponseEntity<CustomApplicationResponse> getByProjectIdentifierAndProjectTaskSequence(
      final @PathVariable("projectIdentifier") String projectIdentifier,
      final @PathVariable("projectTaskSequence") String projectTaskSequence) {

    return applicationResponseMappingService.getApplicationResponse(
        format("Task with project sequence %s is found", projectTaskSequence),
        projectTaskService.getByProjectSequence(projectIdentifier, projectTaskSequence),
        FOUND);
  }

  @PatchMapping("/{projectIdentifier}/{projectSequence}")
  @SuppressWarnings({"java:S4684", "java:S1452"})
  public ResponseEntity<CustomApplicationResponse> updatedProjectTask(
      final @PathVariable("projectIdentifier") String projectIdentifier,
      final @PathVariable("projectSequence") String projectSequence,
      final @RequestBody @Valid ProjectTask updatedProjectTask,
      final BindingResult validationResult) {

    return applicationResponseMappingService
        .getErrorMapping(validationResult)
        .orElseGet(
            () ->
                applicationResponseMappingService.getApplicationResponse(
                    format("Task %s is successfully updated", projectSequence),
                    projectTaskService.updateByProjectSequence(
                        projectSequence, projectIdentifier, updatedProjectTask),
                    OK));
  }

  @DeleteMapping("/{projectIdentifier}/{projectSequence}")
  public ResponseEntity<CustomApplicationResponse> deleteProjectTask(
      final @PathVariable("projectIdentifier") String projectIdentifier,
      final @PathVariable("projectSequence") String projectSequence) {

    return applicationResponseMappingService.getApplicationResponse(
        "Task is successfully deleted",
        projectTaskService.deleteProjectTask(projectIdentifier, projectSequence),
        OK);
  }
}
