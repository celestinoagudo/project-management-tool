package com.ican.code.projectmanagement.repositories;

import com.ican.code.projectmanagement.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

  @Query("SELECT p FROM Project p WHERE p.projectIdentifier =?1")
  Optional<Project> findByProjectIdentifier(String projectIdentifier);
}
