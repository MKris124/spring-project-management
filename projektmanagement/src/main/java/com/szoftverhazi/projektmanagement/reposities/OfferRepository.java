package com.szoftverhazi.projektmanagement.reposities;

import com.szoftverhazi.projektmanagement.dto.OfferDto;
import com.szoftverhazi.projektmanagement.enums.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.szoftverhazi.projektmanagement.entities.Offer;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    @Query("SELECT o.id, o.offerDetails,p.id, p.name, u.name FROM Offer o JOIN o.project p JOIN o.customer u WHERE o.status = :status")
    List<Object[]> findOffersByStatusWithProjectName(@Param("status") OfferStatus status);

    @Query("SELECT o FROM Offer o JOIN o.project p JOIN o.customer u WHERE o.status = 'ACCEPTED' AND u.id = :customerId")
    List<OfferDto> findAcceptedOffersByCustomerId(@Param("customerId") Long customerId);
    @Query("SELECT o FROM Offer o JOIN o.project p JOIN o.customer u WHERE o.status = 'REJECTED' AND u.id = :customerId")
    List<OfferDto> findRejectedOffersByCustomerId(@Param("customerId") Long customerId);
}
