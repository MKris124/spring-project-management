package com.szoftverhazi.projektmanagement.entities;

import com.szoftverhazi.projektmanagement.dto.ProjectDto;
import com.szoftverhazi.projektmanagement.dto.TaskDto;
import com.szoftverhazi.projektmanagement.enums.ProjectPhase;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Task> task= new HashSet<>();
    private ProjectPhase phase;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projectmanager_id", nullable = false)
    private User projectmanager;

    public ProjectDto getProjectDto() {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(this.id);
        projectDto.setName(this.name);
        projectDto.setPhase(this.phase);

        if (this.projectmanager != null) {
            projectDto.setProjectManagerId(this.projectmanager.getId());
            projectDto.setProjectManagerName(this.projectmanager.getName());
        }

        Set<TaskDto> taskDtos = this.task.stream()
                .map(Task::getTaskDto)
                .collect(Collectors.toSet());
        projectDto.setTasks(taskDtos);

        return projectDto;
    }
}
