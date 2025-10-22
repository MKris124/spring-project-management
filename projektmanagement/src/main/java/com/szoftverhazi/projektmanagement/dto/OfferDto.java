package com.szoftverhazi.projektmanagement.dto;

import com.szoftverhazi.projektmanagement.enums.OfferStatus;
import lombok.Data;

@Data
public class OfferDto {
    private Long id;
    private Long customerId;
    private Long projectId;
    private String offerDetails;
    private OfferStatus status;
}
