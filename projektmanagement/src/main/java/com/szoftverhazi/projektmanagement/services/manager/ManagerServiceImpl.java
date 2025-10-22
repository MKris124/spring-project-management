package com.szoftverhazi.projektmanagement.services.manager;

import com.szoftverhazi.projektmanagement.dto.*;
import com.szoftverhazi.projektmanagement.entities.*;
import com.szoftverhazi.projektmanagement.enums.*;
import com.szoftverhazi.projektmanagement.reposities.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService{
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final OrderRepository orderRepository;
    private final OfferRepository offerRepository;

    @Override
    public ProjectDto createProjectForManager(Long managerId, ProjectDto projectDto) {
        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Project Manager by ID: " + managerId));

        Project project = new Project();
        project.setName(projectDto.getName());
        project.setPhase(projectDto.getPhase() != null ? projectDto.getPhase() : ProjectPhase.ÉRDEKLŐDÉS);
        project.setProjectmanager(manager);

        project = projectRepository.save(project);

        return project.getProjectDto();
    }

    @Override
    public TaskDto createTask(TaskDto taskDto, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Project by ID: " + projectId));

        User user = userRepository.findById(taskDto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find User by ID: " + taskDto.getEmployeeId()));

        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDueDate(taskDto.getDueDate());
        task.setStatus(TaskStatus.FOLYAMATBAN);
        task.setType(taskDto.getType());
        task.setUser(user);
        task.setProject(project);

        Task savedTask = taskRepository.save(task);
        return savedTask.getTaskDto();
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

    @Override
    public List<ProjectDto> getProjectsByManager(Long managerId) {
        List<Project> projects = projectRepository.findProjectsByManagerId(managerId);

        return projects.stream()
                .map(Project::getProjectDto)
                .collect(Collectors.toList());
    }


    @Override
    public void removeProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find project by ID: " + projectId));

        projectRepository.delete(project);
    }

    @Override
    public ProjectDto updateProjectPhase(Long projectId, String newPhase) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find project by ID: " + projectId));

        try {
            ProjectPhase phase = ProjectPhase.valueOf(newPhase);
            project.setPhase(phase);
            project = projectRepository.save(project);

            return project.getProjectDto();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unrecognised status: " + newPhase);
        }
    }
    @Override
    public List<UserDto> getEmployees() {
        return userRepository.findAll()
                .stream()
                .filter(employee->employee.getUserRole()== UserRole.USER)
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskTitleAndDueDateDto> getTasksByProject(Long projectId) {
        List<Object[]> results = taskRepository.findTaskTitlesAndDueDatesByProjectId(projectId);

        return results.stream()
                .map(row -> new TaskTitleAndDueDateDto((String) row[0], (Date) row[1],(Long)row  [2],(String) row[3],(String)row[4]))
                .collect(Collectors.toList());
    }
    @Override
    public List<String> getUsersByProjectId(Long projectId) {
        return taskRepository.findDistinctUserNamesByProjectId(projectId);
    }
    @Override
    public List<OfferedProjectsDto> getPendingOffers() {
        List<Object[]> results = offerRepository.findOffersByStatusWithProjectName(OfferStatus.PENDING);
           return results.stream()
                .map(row->new OfferedProjectsDto((Long) row[0],(String)row[1],(Long) row[2],(String)row[3],(String)row[4]))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderedProjectsDto> getPendingOrders() {
        List<Object[]> results= orderRepository.findOrdersByStatusWithProjectName(OrderStatus.PENDING);
            return results.stream()
                .map(row->new OrderedProjectsDto((Long) row[0],(String)row[1],(Long) row[2],(String)row[3],(String)row[4]))
                .collect(Collectors.toList());
    }


    @Override
    public OrderDto acceptOrder(Long orderId, Long managerId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Order by ID: " + orderId));

        Project project = order.getProject();
        project.setProjectmanager(userRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Project Manager by ID: " + managerId)));
        project.setPhase(ProjectPhase.KIVITELEZÉS);
        projectRepository.save(project);

        order.setStatus(OrderStatus.ACCEPTED);
        orderRepository.save(order);

        return order.toOrderDto();
    }

    @Override
    public OrderDto rejectOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Order by ID: " + orderId));

        order.setStatus(OrderStatus.REJECTED);
        orderRepository.save(order);

        return order.toOrderDto();
    }
    @Override
    public OfferDto acceptOffer(Long offerId, Long managerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Order by ID: " + offerId));

        Project project = offer.getProject();
        project.setProjectmanager(userRepository.findById(managerId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Project Manager by ID: " + managerId)));
        project.setPhase(ProjectPhase.KIVITELEZÉS);
        projectRepository.save(project);

        offer.setStatus(OfferStatus.ACCEPTED);
        offerRepository.save(offer);

        return offer.toOfferDto();
    }

    @Override
    public OfferDto rejectOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Order by ID: " + offerId));

        offer.setStatus(OfferStatus.REJECTED);
        offerRepository.save(offer);

        return offer.toOfferDto();
    }




}
