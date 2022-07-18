package com.ican.code.projectmanagement.exceptions;

import com.ican.code.projectmanagement.domain.CustomApplicationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.util.StringUtils.hasLength;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @Value("${app.name}")
  private String applicationName;

  @Value("${app.version}")
  private String version;

  @Value("${app.default-error-message}")
  private String defaultErrorMessage;

  @ExceptionHandler
  public final ResponseEntity<CustomApplicationResponse> handleProjectManagementException(
      ProjectManagementException projectManagementException, WebRequest request) {

    return new ResponseEntity<>(
        CustomApplicationResponse.builder()
            .programName(applicationName)
            .version(version)
            .datetime(LocalDateTime.now())
            .status(CustomApplicationResponse.ResponseStatus.ERROR.name())
            .code(HttpStatus.BAD_REQUEST.value())
            .message(projectManagementException.getMessage())
            .build(),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public final ResponseEntity<CustomApplicationResponse> handleGenericException(
      Exception genericException, WebRequest request) {

    final String genericExceptionMessage = genericException.getMessage();

    return new ResponseEntity<>(
        CustomApplicationResponse.builder()
            .programName(applicationName)
            .version(version)
            .datetime(LocalDateTime.now())
            .status(CustomApplicationResponse.ResponseStatus.ERROR.name())
            .code(HttpStatus.BAD_REQUEST.value())
            .message(
                hasLength(genericExceptionMessage) ? genericExceptionMessage : defaultErrorMessage)
            .build(),
        HttpStatus.BAD_REQUEST);
  }
}
