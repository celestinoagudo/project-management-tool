package com.ican.code.projectmanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Backlog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer projectTaskSequence;
  private String projectIdentifier;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "project_id", nullable = false)
  @JsonIgnore
  private Project project;

  @OneToMany(
      cascade = CascadeType.REFRESH,
      fetch = FetchType.EAGER,
      mappedBy = "backlog",
      orphanRemoval = true)
  private List<ProjectTask> projectTasks;

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getProjectIdentifier(), getProject());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Backlog)) return false;
    Backlog backlog = (Backlog) o;
    return Objects.equals(getId(), backlog.getId())
        && Objects.equals(getProjectTaskSequence(), backlog.getProjectTaskSequence())
        && Objects.equals(getProjectIdentifier(), backlog.getProjectIdentifier())
        && Objects.equals(getProject(), backlog.getProject());
  }
}
