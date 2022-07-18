package com.ican.code.projectmanagement.services;

import com.ican.code.projectmanagement.domain.CustomApplicationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static com.ican.code.projectmanagement.domain.CustomApplicationResponse.ResponseStatus.ERROR;
import static com.ican.code.projectmanagement.domain.CustomApplicationResponse.ResponseStatus.SUCCESS;
import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ApplicationResponseMappingService {

  @Value("${app.name}")
  private String applicationName;

  @Value("${app.version}")
  private String version;

  public Optional<ResponseEntity<CustomApplicationResponse>> getErrorMapping(
      BindingResult validationResult) {

    if (!validationResult.hasErrors()) return Optional.empty();

    return Optional.of(
        new ResponseEntity<>(
            CustomApplicationResponse.builder()
                .programName(applicationName)
                .version(version)
                .datetime(now())
                .status(ERROR.name())
                .code(BAD_REQUEST.value())
                .message(
                    validationResult.getFieldErrors().stream()
                        .reduce(
                            "",
                            (identity, fieldError) ->
                                identity
                                    .concat("|")
                                    .concat(requireNonNull(fieldError.getDefaultMessage())),
                            String::concat)
                        .substring(1))
                .build(),
            BAD_REQUEST));
  }

  public ResponseEntity<CustomApplicationResponse> getApplicationResponse(
      final String message, final Object data, final HttpStatus status) {

    return new ResponseEntity<>(
        CustomApplicationResponse.builder()
            .programName(applicationName)
            .version(version)
            .datetime(now())
            .status(SUCCESS.name())
            .code(status.value())
            .message(message)
            .data(data)
            .build(),
        status);
  }
}
