package com.szoftverhazi.projektmanagement.dto;

import com.szoftverhazi.projektmanagement.enums.ProjectPhase;
import lombok.Data;
import java.util.Set;

@Data
public class ProjectDto {
    private Long id;
    private String name;
    private ProjectPhase phase;
    private Long projectManagerId;
    private String projectManagerName;
    private Set<TaskDto> tasks;
}
