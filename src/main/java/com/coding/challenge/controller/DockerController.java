package com.coding.challenge.controller;


import com.coding.challenge.dto.CreateResponseDTO;
import com.coding.challenge.service.DockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController( value = "docker")
public class DockerController {

    @Autowired
    DockerService dockerService;

    @GetMapping("/create-start-docker-nginx")
    public ResponseEntity<CreateResponseDTO> createStartDockerNginx() {

        return dockerService.createStartDockerNginx();

    }

    @GetMapping("/stop-docker-nginx/{id}")
    public ResponseEntity<String> createStopDockerNginx(@PathVariable String id) {
        return dockerService.createStopDockerNginx(id);
    }

    @GetMapping("/get-page-docker")
    public ResponseEntity<String> getDockerPage() {
        return dockerService.getDockerPage();
    }


}
