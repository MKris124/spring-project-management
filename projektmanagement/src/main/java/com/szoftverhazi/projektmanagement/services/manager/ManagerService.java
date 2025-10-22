package com.szoftverhazi.projektmanagement.services.manager;


import com.szoftverhazi.projektmanagement.dto.*;

import java.util.List;

public interface ManagerService {
    ProjectDto createProjectForManager(Long managerId, ProjectDto projectDto);
    TaskDto createTask(TaskDto taskDto, Long projectId);
    List<ProjectDto> getProjectsByManager(Long managerId);
    void removeProject(Long projectId);
    ProjectDto updateProjectPhase(Long projectId, String newPhase);
    TaskDto updateTaskStatus(Long taskId, String newStatus);
    List<UserDto> getEmployees();
    List<TaskTitleAndDueDateDto> getTasksByProject(Long projectId);
    List<String> getUsersByProjectId(Long projectId);
    List<OrderedProjectsDto> getPendingOrders();
    List<OfferedProjectsDto> getPendingOffers();
    OrderDto acceptOrder(Long orderId,Long mangerId);
    OrderDto rejectOrder(Long orderId);
    OfferDto acceptOffer(Long offerId, Long managerId);
    OfferDto rejectOffer(Long offerId);
}
