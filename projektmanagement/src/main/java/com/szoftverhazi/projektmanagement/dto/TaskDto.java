package com.szoftverhazi.projektmanagement.dto;

import com.szoftverhazi.projektmanagement.enums.TaskStatus;
import com.szoftverhazi.projektmanagement.enums.TaskType;
import lombok.Data;

import java.util.Date;
@Data
public class TaskDto {
    private Long id;
    private String title;
    private TaskType type;
    private TaskStatus status;
    private Date dueDate;
    private Long employeeId;
    private String employeeName;

}
