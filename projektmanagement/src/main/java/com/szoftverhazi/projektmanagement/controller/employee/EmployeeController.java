package com.szoftverhazi.projektmanagement.controller.employee;

import com.szoftverhazi.projektmanagement.dto.TaskDto;
import com.szoftverhazi.projektmanagement.dto.UserTaskDto;
import com.szoftverhazi.projektmanagement.services.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
@CrossOrigin("http://localhost:4200")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<UserTaskDto>> getAssignedTasks(@PathVariable Long userId) {
        List<UserTaskDto> tasks = employeeService.getAssignedTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/tasks/{taskId}/status")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long taskId, @RequestParam String newStatus) {
        TaskDto updatedTask = employeeService.updateTaskStatus(taskId, newStatus);
        return ResponseEntity.ok(updatedTask);
    }
}
