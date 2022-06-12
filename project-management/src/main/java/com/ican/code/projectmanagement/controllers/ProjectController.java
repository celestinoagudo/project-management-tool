package com.ican.code.projectmanagement.controllers;

import com.ican.code.projectmanagement.domain.Project;
import com.ican.code.projectmanagement.services.ErrorMappingService;
import com.ican.code.projectmanagement.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

  private final ProjectService projectService;

  private final ErrorMappingService errorMappingService;

  @Autowired
  public ProjectController(ProjectService projectService, ErrorMappingService errorMappingService) {

    this.projectService = projectService;
    this.errorMappingService = errorMappingService;
  }

  @PostMapping
  public ResponseEntity<?> createNewProject(
      @Valid @RequestBody Project project, BindingResult validationResult) {

    Optional<ResponseEntity<Map<String, String>>> errors =
        errorMappingService.getErrorMapping(validationResult);

    return errors.isPresent()
        ? errors.get()
        : new ResponseEntity<>(projectService.saveOrUpdateProject(project), CREATED);
  }

  @GetMapping("/{projectIdentifier}")
  public ResponseEntity<?> getProjectByIdentifier(
      @PathVariable("projectIdentifier") String projectIdentifier) {

    return new ResponseEntity<>(projectService.findByProjectIdentifier(projectIdentifier), FOUND);
  }

  @GetMapping
  public List<Project> findAll() {

    return projectService.findAll();
  }

  @DeleteMapping("/{projectIdentifier}")
  public ResponseEntity<?> deleteProjectByIdentifier(
      @PathVariable("projectIdentifier") String projectIdentifier) {

    Project deletedProject = projectService.deleteProjectByIdentifier(projectIdentifier);

    return new ResponseEntity<>(
        format("Project with identifier [%s]", deletedProject.getProjectIdentifier()), OK);
  }
}
