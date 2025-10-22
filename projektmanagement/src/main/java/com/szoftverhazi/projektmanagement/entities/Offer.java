package com.szoftverhazi.projektmanagement.entities;

import com.szoftverhazi.projektmanagement.dto.OfferDto;
import com.szoftverhazi.projektmanagement.enums.OfferStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    private String offerDetails;
    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    public OfferDto toOfferDto() {
        OfferDto offerDto = new OfferDto();
        offerDto.setId(this.id);
        offerDto.setProjectId(this.project.getId());
        offerDto.setCustomerId(this.customer.getId());
        offerDto.setOfferDetails(this.offerDetails);
        offerDto.setStatus(this.status);
        return offerDto;
    }
}
