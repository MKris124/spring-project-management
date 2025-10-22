package com.szoftverhazi.projektmanagement.controller.employee;

import com.szoftverhazi.projektmanagement.dto.*;
import com.szoftverhazi.projektmanagement.entities.Offer;
import com.szoftverhazi.projektmanagement.services.manager.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager")
@CrossOrigin("http://localhost:4200")
public class ManagerController {

    private final ManagerService managerService;
    @PostMapping("/{managerId}/projects")
    public ResponseEntity<ProjectDto> createProjectForManager(
            @PathVariable Long managerId,
            @RequestBody ProjectDto projectDto) {
        ProjectDto newProject = managerService.createProjectForManager(managerId, projectDto);
        return ResponseEntity.ok(newProject);
    }
    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskDto> createTask(
            @PathVariable Long projectId,
            @RequestBody TaskDto taskDto) {
        TaskDto newTask = managerService.createTask(taskDto, projectId);
        return ResponseEntity.ok(newTask);
    }

    @PutMapping("/tasks/{taskId}/status")
    public ResponseEntity<TaskDto> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam String newStatus) {
        TaskDto updatedTask = managerService.updateTaskStatus(taskId, newStatus);
        return ResponseEntity.ok(updatedTask);
    }

    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<TaskTitleAndDueDateDto>> getTasksByProject(
            @PathVariable Long projectId) {
        List<TaskTitleAndDueDateDto> tasks = managerService.getTasksByProject(projectId);
        return ResponseEntity.ok(tasks);
    }
    @GetMapping("/projects/{projectId}/users")
    public ResponseEntity<List<String>> getUsersByProjectId(@PathVariable Long projectId) {
        List<String> users = managerService.getUsersByProjectId(projectId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{managerId}/projects")
    public ResponseEntity<List<ProjectDto>> getProjectsByManager(
            @PathVariable Long managerId) {
        List<ProjectDto> projects = managerService.getProjectsByManager(managerId);
        return ResponseEntity.ok(projects);
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Void> removeProject(@PathVariable Long projectId) {
        managerService.removeProject(projectId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/projects/{projectId}/phase")
    public ResponseEntity<ProjectDto> updateProjectPhase(
            @PathVariable Long projectId,
            @RequestParam String newPhase) {
        ProjectDto updatedProject = managerService.updateProjectPhase(projectId, newPhase);
        return ResponseEntity.ok(updatedProject);
    }

    @GetMapping("/employees")
    public ResponseEntity<?> getEmployees()
    {
        return ResponseEntity.ok(managerService.getEmployees());
    }

    @PutMapping("/orders/{orderId}/accept")
    public ResponseEntity<OrderDto> acceptOrder(
            @PathVariable Long orderId,
            @RequestParam Long managerId) {
        OrderDto acceptedOrder = managerService.acceptOrder(orderId, managerId);
        return ResponseEntity.ok(acceptedOrder);
    }

    @PutMapping("/orders/{orderId}/reject")
    public ResponseEntity<OrderDto> rejectOrder(@PathVariable Long orderId) {
        OrderDto rejectedOrder = managerService.rejectOrder(orderId);
        return ResponseEntity.ok(rejectedOrder);
    }
    @GetMapping("/orders/pending")
    public ResponseEntity<List<OrderedProjectsDto>> getPendingOrders() {
        List<OrderedProjectsDto> pendingOrders = managerService.getPendingOrders();
        return ResponseEntity.ok(pendingOrders);
    }
    @GetMapping("/offers/pending")
    public ResponseEntity<List<OfferedProjectsDto>> getPendingOffers() {
        List<OfferedProjectsDto> pendingOffers = managerService.getPendingOffers();
        return ResponseEntity.ok(pendingOffers);
    }
    @PutMapping("/offers/{offerId}/accept")
    public ResponseEntity<OfferDto> acceptOffer(
            @PathVariable Long offerId,
            @RequestParam Long managerId) {
        OfferDto acceptedOffer = managerService.acceptOffer(offerId, managerId);
        return ResponseEntity.ok(acceptedOffer);
    }

    @PutMapping("/offers/{offerId}/reject")
    public ResponseEntity<OfferDto> rejectOffer(@PathVariable Long offerId) {
        OfferDto rejectedOffer = managerService.rejectOffer(offerId);
        return ResponseEntity.ok(rejectedOffer);
    }
}
