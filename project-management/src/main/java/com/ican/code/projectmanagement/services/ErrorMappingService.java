package com.ican.code.projectmanagement.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ErrorMappingService {

  public Optional<ResponseEntity<Map<String, String>>> getErrorMapping(
      BindingResult validationResult) {

    if (!validationResult.hasErrors()) return Optional.empty();

    HashMap<String, String> errorMessages =
        validationResult.getFieldErrors().stream()
            .collect(
                HashMap::new,
                (map, error) -> map.put(error.getField(), error.getDefaultMessage()),
                HashMap::putAll);

    return Optional.of(new ResponseEntity<>(errorMessages, BAD_REQUEST));
  }
}
