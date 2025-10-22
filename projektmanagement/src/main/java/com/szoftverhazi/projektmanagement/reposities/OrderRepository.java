package com.szoftverhazi.projektmanagement.reposities;


import com.szoftverhazi.projektmanagement.dto.OfferDto;
import com.szoftverhazi.projektmanagement.dto.OrderDto;
import com.szoftverhazi.projektmanagement.entities.Order;
import com.szoftverhazi.projektmanagement.enums.OfferStatus;
import com.szoftverhazi.projektmanagement.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o.id, o.details,p.id, p.name, u.name FROM Order o JOIN o.project p JOIN o.customer u WHERE o.status = :status")
    List<Object[]> findOrdersByStatusWithProjectName(@Param("status") OrderStatus status);
    @Query("SELECT o FROM Order o JOIN o.project p JOIN o.customer u WHERE o.status = 'ACCEPTED' AND u.id = :customerId")
    List<OrderDto> findAcceptedOrdersByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT o FROM Order o JOIN o.project p JOIN o.customer u WHERE o.status = 'REJECTED' AND u.id = :customerId")
    List<OrderDto> findRejectedOrdersByCustomerId(@Param("customerId") Long customerId);
}
