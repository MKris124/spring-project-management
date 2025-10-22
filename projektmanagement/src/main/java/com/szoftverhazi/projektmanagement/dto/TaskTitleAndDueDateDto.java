package com.szoftverhazi.projektmanagement.dto;

import com.szoftverhazi.projektmanagement.enums.TaskStatus;
import lombok.Data;

import java.util.Date;

@Data
public class TaskTitleAndDueDateDto {
    private  Long id;
    private String title;
    private Date dueDate;
    private String status;
    private String employeeName;

    public TaskTitleAndDueDateDto(String title, Date dueDate, Long id, String status, String employeeName) {
        this.title = title;
        this.dueDate = dueDate;
        this.id=id;
        this.status=status;
        this.employeeName=employeeName;

    }
}
