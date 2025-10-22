package com.szoftverhazi.projektmanagement.services.employee;

import com.szoftverhazi.projektmanagement.dto.TaskDto;
import com.szoftverhazi.projektmanagement.dto.UserTaskDto;
import com.szoftverhazi.projektmanagement.entities.Task;
import com.szoftverhazi.projektmanagement.entities.User;
import com.szoftverhazi.projektmanagement.enums.TaskStatus;
import com.szoftverhazi.projektmanagement.reposities.TaskRepository;
import com.szoftverhazi.projektmanagement.reposities.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    @Override
    public List<UserTaskDto> getAssignedTasks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find User by ID: " + userId));
        List<Object[]>results=taskRepository.findTasksByUserId(userId);
        return results.stream().map(result->new UserTaskDto(
                        (Long) result[0],(String) result[1],(Date)result[2],(String) result[3],(String) result[4])).collect(Collectors.toList());

    }

    @Override
    public TaskDto updateTaskStatus(Long taskId, String newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Task by ID: " + taskId));
        if (task.getStatus() == TaskStatus.ELHALASZTVA || task.getStatus() == TaskStatus.TELJESÍTVE) {
            throw new IllegalStateException("This task is either postponed or completed and cannot be modified.");
        }
        try {
            TaskStatus status = TaskStatus.valueOf(newStatus);
            task.setStatus(status);
            task = taskRepository.save(task);
            return task.getTaskDto();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unrecognised status: " + newStatus);
        }
    }
}
