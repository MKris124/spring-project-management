package com.szoftverhazi.projektmanagement.controller;

import com.szoftverhazi.projektmanagement.dto.ProjectDto;
import com.szoftverhazi.projektmanagement.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/projects")
@CrossOrigin("http://localhost:4200")
public class PublicController {
    private final CustomerService customerService;

    @GetMapping("/running")
    public ResponseEntity<List<ProjectDto>> getRunningProjects() {
        List<ProjectDto> runningProjects = customerService.getRunningProjects();
        return ResponseEntity.ok(runningProjects);
    }
}
