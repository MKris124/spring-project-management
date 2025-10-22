package com.szoftverhazi.projektmanagement.dto;

import com.szoftverhazi.projektmanagement.enums.TaskStatus;
import com.szoftverhazi.projektmanagement.enums.TaskType;
import lombok.Data;

import java.util.Date;
@Data
public class UserTaskDto {
        private Long id;
        private String title;
        private Date dueDate;
        private String type;
        private String status;
    public UserTaskDto(Long id, String title, Date date, String status, String type) {
        this.id = id;
        this.title = title;
        this.dueDate = date;
        this.status =status;
        this.type = type;
    }
}
