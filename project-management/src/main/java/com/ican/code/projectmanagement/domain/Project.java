package com.ican.code.projectmanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Project name is required")
  private String projectName;

  @NotBlank(message = "Project identifier is required")
  @Size(min = 4, max = 5, message = "Project identifier is required to have 4 to 5 characters")
  @Column(updatable = false, unique = true)
  private String projectIdentifier;

  @NotBlank(message = "Description is required")
  private String description;

  private LocalDate startDate;
  private LocalDate endDate;
  private LocalDate createdAt;
  private LocalDate updatedAt;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
  @JsonIgnore
  private Backlog backlog;

  @Override
  public int hashCode() {
    return Objects.hash(
        getProjectName(), getProjectIdentifier(), getDescription(), getStartDate(), getEndDate());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Project)) return false;
    Project project = (Project) o;
    return getProjectName().equals(project.getProjectName())
        && getProjectIdentifier().equals(project.getProjectIdentifier())
        && getDescription().equals(project.getDescription())
        && Objects.equals(getStartDate(), project.getStartDate())
        && Objects.equals(getEndDate(), project.getEndDate());
  }

  @Override
  public String toString() {
    return "Project{"
        + "id="
        + id
        + ", projectName='"
        + projectName
        + '\''
        + ", projectIdentifier='"
        + projectIdentifier
        + '\''
        + ", description='"
        + description
        + '\''
        + ", startDate="
        + startDate
        + ", endDate="
        + endDate
        + ", createdAt="
        + createdAt
        + ", updatedAt="
        + updatedAt
        + '}';
  }

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDate.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDate.now();
  }
}
