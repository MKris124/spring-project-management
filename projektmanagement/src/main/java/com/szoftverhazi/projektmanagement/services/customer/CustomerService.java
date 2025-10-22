package com.szoftverhazi.projektmanagement.services.customer;

import com.szoftverhazi.projektmanagement.dto.OfferDto;
import com.szoftverhazi.projektmanagement.dto.OrderDto;
import com.szoftverhazi.projektmanagement.dto.ProjectDto;

import java.util.List;

public interface CustomerService {
    OrderDto placeOrder(Long projectId, OrderDto orderDto);
    OfferDto placeOffer(Long projectId, OfferDto offerDto);
    List<ProjectDto> getRunningProjects();
    List<OrderDto> getCustomerOrders(Long customerId);
    List<OfferDto> getCustomerOffers(Long customerId);
    List<OrderDto> getRejectedOrders(Long customerId);
    List<OfferDto> getRejectedOffers(Long customerId);
}
