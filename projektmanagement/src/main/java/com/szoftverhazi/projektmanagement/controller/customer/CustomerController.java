package com.szoftverhazi.projektmanagement.controller.customer;

import com.szoftverhazi.projektmanagement.dto.OfferDto;
import com.szoftverhazi.projektmanagement.dto.OrderDto;
import com.szoftverhazi.projektmanagement.dto.ProjectDto;
import com.szoftverhazi.projektmanagement.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
@CrossOrigin("http://localhost:4200")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/{customerId}/projects/{projectId}/orders")
    public ResponseEntity<OrderDto> placeOrder(
            @PathVariable Long customerId,
            @PathVariable Long projectId,
            @RequestBody OrderDto orderDto) {
        orderDto.setCustomerId(customerId);
        OrderDto newOrder = customerService.placeOrder(projectId, orderDto);
        return ResponseEntity.ok(newOrder);
    }

    @PostMapping("/{customerId}/projects/{projectId}/offers")
    public ResponseEntity<OfferDto> placeOffer(
            @PathVariable Long customerId,
            @PathVariable Long projectId,
            @RequestBody OfferDto offerDto) {
        offerDto.setCustomerId(customerId);
        OfferDto newOffer = customerService.placeOffer(projectId, offerDto);
        return ResponseEntity.ok(newOffer);
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderDto>> getCustomerOrders(@PathVariable Long customerId) {
        List<OrderDto> customerOrders = customerService.getCustomerOrders(customerId);
        return ResponseEntity.ok(customerOrders);
    }
    @GetMapping("/{customerId}/offers")
    public ResponseEntity<List<OfferDto>> getCustomerOffers(@PathVariable Long customerId) {
        List<OfferDto> customerOrders = customerService.getCustomerOffers(customerId);
        return ResponseEntity.ok(customerOrders);
    }
    @GetMapping("/{customerId}/orders/rejected")
    public ResponseEntity<List<OrderDto>> getRejectedOrders(@PathVariable Long customerId) {
        List<OrderDto> customerOrders = customerService.getRejectedOrders(customerId);
        return ResponseEntity.ok(customerOrders);
    }
    @GetMapping("/{customerId}/offers/rejected")
    public ResponseEntity<List<OfferDto>> getRejectedOffers(@PathVariable Long customerId) {
        List<OfferDto> customerOrders = customerService.getRejectedOffers(customerId);
        return ResponseEntity.ok(customerOrders);
    }
}
