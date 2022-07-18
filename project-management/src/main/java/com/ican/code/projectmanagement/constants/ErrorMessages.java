package com.ican.code.projectmanagement.constants;

public class ErrorMessages {

  public static final String PROJECT_IDENTIFIER_NOT_FOUND = "Project id %s is non-existent";
  public static final String PROJECT_IDENTIFIER_EXISTS = "Project Identifier %s already exists";
  public static final String PROJECT_SEQUENCE_NOT_FOUND = "Project Sequence %s is non-existent";
  public static final String PT_SEQUENCE_NOT_IN_BACKLOG =
      "Project task %s is not found under project %s";

  private ErrorMessages() {}
}
