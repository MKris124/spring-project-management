package com.szoftverhazi.projektmanagement.reposities;

import com.szoftverhazi.projektmanagement.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
    @Query("SELECT p FROM Project p WHERE p.projectmanager.id = :managerId")
    List<Project> findProjectsByManagerId(@Param("managerId") Long managerId);
}
