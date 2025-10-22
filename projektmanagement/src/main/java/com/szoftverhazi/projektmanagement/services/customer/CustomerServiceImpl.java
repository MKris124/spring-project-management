package com.szoftverhazi.projektmanagement.services.customer;

import com.szoftverhazi.projektmanagement.dto.OfferDto;
import com.szoftverhazi.projektmanagement.dto.OrderDto;
import com.szoftverhazi.projektmanagement.dto.ProjectDto;
import com.szoftverhazi.projektmanagement.entities.Offer;
import com.szoftverhazi.projektmanagement.entities.Order;
import com.szoftverhazi.projektmanagement.entities.Project;
import com.szoftverhazi.projektmanagement.entities.User;
import com.szoftverhazi.projektmanagement.enums.OfferStatus;
import com.szoftverhazi.projektmanagement.enums.OrderStatus;
import com.szoftverhazi.projektmanagement.enums.ProjectPhase;
import com.szoftverhazi.projektmanagement.reposities.OfferRepository;
import com.szoftverhazi.projektmanagement.reposities.OrderRepository;
import com.szoftverhazi.projektmanagement.reposities.ProjectRepository;
import com.szoftverhazi.projektmanagement.reposities.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ProjectRepository projectRepository;
    private final OrderRepository orderRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    @Override
    public OrderDto placeOrder(Long projectId, OrderDto orderDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Project by ID: " + projectId));

        User customer = userRepository.findById(orderDto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Customer by ID: " + orderDto.getCustomerId()));

        Order order = new Order();
        order.setProject(project);
        order.setCustomer(customer);
        order.setDetails(orderDto.getDetails());
        order.setStatus(OrderStatus.PENDING);

        order = orderRepository.save(order);
        return order.toOrderDto();
    }
    @Override
    public OfferDto placeOffer(Long projectId, OfferDto offerDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Project by ID: " + projectId));

        User customer = userRepository.findById(offerDto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find Customer by ID: " + offerDto.getCustomerId()));

        Offer offer = new Offer();
        offer.setProject(project);
        offer.setCustomer(customer);
        offer.setOfferDetails(offerDto.getOfferDetails());
        offer.setStatus(OfferStatus.PENDING);

        offer = offerRepository.save(offer);
        return offer.toOfferDto();
    }

    @Override
    public List<ProjectDto> getRunningProjects() {
        return projectRepository.findAll()
                .stream()
                .filter(project -> project.getPhase() == ProjectPhase.AJÁNLATADÁS || project.getPhase() == ProjectPhase.MEGRENDELÉS)
                .map(Project::getProjectDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<OrderDto> getCustomerOrders(Long customerId) {
        return orderRepository.findAcceptedOrdersByCustomerId(customerId);
    }
    @Override
    public List<OfferDto> getCustomerOffers(Long customerId) {
        return offerRepository.findAcceptedOffersByCustomerId(customerId);
    }

    @Override
    public List<OrderDto> getRejectedOrders(Long customerId) {
        return orderRepository.findRejectedOrdersByCustomerId(customerId);
    }
    @Override
    public List<OfferDto> getRejectedOffers(Long customerId) {
        return offerRepository.findRejectedOffersByCustomerId(customerId);
    }
}








