package com.ican.code.projectmanagement.repositories;

import com.ican.code.projectmanagement.domain.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {

  @Query(
      "SELECT projectTask FROM ProjectTask projectTask WHERE projectTask.projectIdentifier =?1 "
          + "ORDER BY projectTask.priority")
  List<ProjectTask> findByProjectIdentifierOrderByPriority(String projectIdentifier);

  @Query("SELECT projectTask FROM ProjectTask projectTask WHERE projectTask.projectSequence =?1")
  Optional<ProjectTask> findByProjectSequence(String projectSequence);
}
