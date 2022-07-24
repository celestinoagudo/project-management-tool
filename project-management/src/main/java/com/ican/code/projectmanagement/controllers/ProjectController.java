package com.ican.code.projectmanagement.controllers;

import com.ican.code.projectmanagement.domain.CustomApplicationResponse;
import com.ican.code.projectmanagement.domain.Project;
import com.ican.code.projectmanagement.services.ApplicationResponseMappingService;
import com.ican.code.projectmanagement.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

  private final ProjectService projectService;
  private final ApplicationResponseMappingService applicationResponseMappingService;

  @Autowired
  public ProjectController(
      final ProjectService projectService,
      final ApplicationResponseMappingService applicationResponseMappingService) {

    this.projectService = projectService;
    this.applicationResponseMappingService = applicationResponseMappingService;
  }

  @PostMapping
  public ResponseEntity<CustomApplicationResponse> saveProject(
      final @Valid @RequestBody Project project, final BindingResult validationResult) {

    return applicationResponseMappingService
        .getErrorMapping(validationResult)
        .orElseGet(
            () -> {
              final Project savedProject = projectService.saveOrUpdateProject(project);
              final String message =
                  format(
                          "Project %s with identifier %s is successfully ",
                          savedProject.getProjectName(), savedProject.getProjectIdentifier())
                      .concat(isNull(project.getId()) ? "created." : "updated.");
              return applicationResponseMappingService.getApplicationResponse(
                  message, savedProject, isNull(project.getId()) ? CREATED : OK);
            });
  }

  @GetMapping("/{projectIdentifier}")
  public ResponseEntity<CustomApplicationResponse> getProjectByIdentifier(
      final @PathVariable("projectIdentifier") String projectIdentifier) {

    return applicationResponseMappingService.getApplicationResponse(
        format("Project with identifier %s is found", projectIdentifier),
        projectService.findByProjectIdentifier(projectIdentifier),
        FOUND);
  }

  @GetMapping
  public ResponseEntity<CustomApplicationResponse> findAll() {

    return applicationResponseMappingService.getApplicationResponse(
        "Successfully fetched all records", projectService.findAll(), OK);
  }

  @DeleteMapping("/{projectIdentifier}")
  public ResponseEntity<CustomApplicationResponse> deleteProjectByIdentifier(
      final @PathVariable("projectIdentifier") String projectIdentifier) {

    return applicationResponseMappingService.getApplicationResponse(
        "Project is successfully deleted",
        projectService.deleteProjectByIdentifier(projectIdentifier),
        OK);
  }
}
