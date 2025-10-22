package com.szoftverhazi.projektmanagement.dto;

import lombok.Data;
@Data
public class OrderedProjectsDto {
    private Long id;
    private String orderDetails;
    private Long projecId;
    private String ProjectName;
    private String CustomerName;

    public OrderedProjectsDto(Long id, String orderDetails,Long projecId, String projectName, String customerName) {
        this.id=id;
        this.orderDetails = orderDetails;
        this.projecId=projecId;
        this.ProjectName = projectName;
        this.CustomerName = customerName;
    }

}
