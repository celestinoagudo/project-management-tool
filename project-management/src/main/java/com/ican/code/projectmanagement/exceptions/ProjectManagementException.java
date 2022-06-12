package com.ican.code.projectmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectManagementException extends RuntimeException {

  public ProjectManagementException(String message) {
    super(message);
  }
}
