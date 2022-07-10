package com.ican.code.projectmanagement.repositories;

import com.ican.code.projectmanagement.domain.Backlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BacklogRepository extends JpaRepository<Backlog, Long> {

  @Query("SELECT backlog FROM Backlog backlog WHERE backlog.projectIdentifier =?1")
  Optional<Backlog> findByProjectIdentifier(String projectIdentifier);
}
