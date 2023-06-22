package com.coding.challenge.controller;


import com.coding.challenge.dto.CreateResponseDTO;
import com.coding.challenge.service.DockerService;
import com.coding.challenge.service.KubernetesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController( value = "ks8")
public class KubernetesController {

    @Autowired
    KubernetesService kubernetesService;

    @GetMapping("/create-start-docker-k8s")
    public ResponseEntity<String> createStartDockerNginx() {

        return kubernetesService.createStartDockerK8s();

    }

    @GetMapping("/stop-docker-k8s/{deployment}")
    public ResponseEntity<String> createStopDockerNginx(@PathVariable String deployment) {
        return kubernetesService.stopDockerK8s(deployment);
    }


    @GetMapping("/get-page-k8s")
    public ResponseEntity<String> getDockerPage() {
        return kubernetesService.getPageK8s();
    }


}
