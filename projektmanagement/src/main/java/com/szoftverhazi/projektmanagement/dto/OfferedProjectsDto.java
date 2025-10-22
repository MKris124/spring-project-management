package com.szoftverhazi.projektmanagement.dto;

import lombok.Data;

@Data
public class OfferedProjectsDto {
    private Long id;
    private String offerDetails;
    private Long projecId;
    private String ProjectName;
    private String CustomerName;

    public OfferedProjectsDto(Long id,String offerDetails,Long projecId, String projectName, String customerName) {
        this.id=id;
        this.offerDetails = offerDetails;
        this.projecId=projecId;
        this.ProjectName = projectName;
        this.CustomerName = customerName;
    }


}
