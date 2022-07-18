package com.ican.code.projectmanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProjectTask {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(updatable = false, unique = true)
  private String projectSequence;

  @NotBlank(message = "Please include a project summary")
  private String summary;

  private String acceptanceCriteria;
  private String status;
  private Integer priority;
  private LocalDate dueDate;
  private LocalDate createdAt;
  private LocalDate updatedAt;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "backlog_id", updatable = false, nullable = false)
  @JsonIgnore
  private Backlog backlog;

  @Column(updatable = false)
  private String projectIdentifier;

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getProjectSequence(),
        getSummary(),
        getAcceptanceCriteria(),
        getStatus(),
        getPriority(),
        getDueDate(),
        getCreatedAt(),
        getProjectIdentifier());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProjectTask)) return false;
    ProjectTask that = (ProjectTask) o;
    return Objects.equals(getId(), that.getId())
        && Objects.equals(getProjectSequence(), that.getProjectSequence())
        && Objects.equals(getSummary(), that.getSummary())
        && Objects.equals(getAcceptanceCriteria(), that.getAcceptanceCriteria())
        && Objects.equals(getStatus(), that.getStatus())
        && Objects.equals(getPriority(), that.getPriority())
        && Objects.equals(getDueDate(), that.getDueDate())
        && Objects.equals(getCreatedAt(), that.getCreatedAt())
        && Objects.equals(getProjectIdentifier(), that.getProjectIdentifier());
  }

  @Override
  public String toString() {
    return "ProjectTask{"
        + "id="
        + id
        + ", projectSequence='"
        + projectSequence
        + '\''
        + ", summary='"
        + summary
        + '\''
        + ", acceptanceCriteria='"
        + acceptanceCriteria
        + '\''
        + ", status='"
        + status
        + '\''
        + ", priority="
        + priority
        + ", dueDate="
        + dueDate
        + ", createdAt="
        + createdAt
        + ", updatedAt="
        + updatedAt
        + ", projectIdentifier='"
        + projectIdentifier
        + '\''
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
