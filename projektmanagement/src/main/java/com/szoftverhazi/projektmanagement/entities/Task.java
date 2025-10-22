package com.szoftverhazi.projektmanagement.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.szoftverhazi.projektmanagement.dto.TaskDto;
import com.szoftverhazi.projektmanagement.dto.UserDto;
import com.szoftverhazi.projektmanagement.enums.TaskStatus;
import com.szoftverhazi.projektmanagement.enums.TaskType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private TaskType type;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private Date dueDate;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name= "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


    public TaskDto getTaskDto()
    {
        TaskDto taskDto= new TaskDto();
        taskDto.setId(id);
        taskDto.setTitle(title);
        taskDto.setType(type);
        taskDto.setStatus(status);
        taskDto.setDueDate(dueDate);
        taskDto.setEmployeeName(user.getName());
        taskDto.setEmployeeId(user.getId());

        return taskDto;
    }
}
