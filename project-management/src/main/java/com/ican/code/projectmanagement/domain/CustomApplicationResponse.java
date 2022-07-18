package com.ican.code.projectmanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomApplicationResponse {

  private String programName;
  private String version;
  private LocalDateTime datetime;
  private String status;
  private int code;
  private String message;
  private Object data;

  public enum ResponseStatus {
    SUCCESS,
    ERROR,
    FAIL,
    ACCEPTED
  }
}
