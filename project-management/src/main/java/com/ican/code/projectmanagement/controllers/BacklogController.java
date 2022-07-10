package com.ican.code.projectmanagement.controllers;

import com.ican.code.projectmanagement.domain.ProjectTask;
import com.ican.code.projectmanagement.services.ErrorMappingService;
import com.ican.code.projectmanagement.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

  private final ProjectTaskService projectTaskService;
  private final ErrorMappingService errorMappingService;

  @Autowired
  public BacklogController(
      ProjectTaskService projectTaskService, ErrorMappingService errorMappingService) {
    this.projectTaskService = projectTaskService;
    this.errorMappingService = errorMappingService;
  }

  @PostMapping("/{backlogId}")
  public ResponseEntity<?> saveProjectTaskToBacklog(
      @Valid @RequestBody ProjectTask projectTask,
      BindingResult validationResult,
      @PathVariable("backlogId") String backlogId) {
    Optional<ResponseEntity<Map<String, String>>> errors =
        errorMappingService.getErrorMapping(validationResult);

    return errors.isPresent()
        ? errors.get()
        : new ResponseEntity<>(projectTaskService.saveProjectTask(backlogId, projectTask), CREATED);
  }
}
