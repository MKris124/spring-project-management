package com.szoftverhazi.projektmanagement.services.employee;

import com.szoftverhazi.projektmanagement.dto.TaskDto;
import com.szoftverhazi.projektmanagement.dto.UserTaskDto;

import java.util.List;

public interface EmployeeService {
    List<UserTaskDto> getAssignedTasks(Long userId);
    TaskDto updateTaskStatus(Long taskId, String newStatus);
}
