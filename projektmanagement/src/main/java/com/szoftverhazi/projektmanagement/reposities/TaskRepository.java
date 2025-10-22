package com.szoftverhazi.projektmanagement.reposities;

import com.szoftverhazi.projektmanagement.dto.TaskDto;
import com.szoftverhazi.projektmanagement.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "SELECT t.title, t.due_date,t.id,t.status, u.name FROM task t " +
            "JOIN project p ON t.project_id = p.id " +
            "JOIN users u ON t.user_id=u.id "+
            "WHERE t.project_id = :projectId", nativeQuery = true)
    List<Object[]> findTaskTitlesAndDueDatesByProjectId(@Param("projectId") Long projectId);

    @Query(value = "SELECT DISTINCT u.name FROM users u " +
            "JOIN task t ON t.user_id = u.id " +
            "WHERE t.project_id = :projectId", nativeQuery = true)
    List<String> findDistinctUserNamesByProjectId(@Param("projectId") Long projectId);
    @Query(value = "SELECT t.id, t.title, t.due_date, t.status, t.type FROM task t " +
            "JOIN users u ON u.id = t.user_id " +
            "WHERE t.user_id = :userId", nativeQuery = true)
    List<Object[]> findTasksByUserId(@Param("userId") Long userId);
}
