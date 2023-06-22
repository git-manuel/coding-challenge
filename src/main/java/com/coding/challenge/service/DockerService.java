package com.coding.challenge.service;

import com.coding.challenge.dto.CreateResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class DockerService {
    private RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(DockerService.class);

    @Value("${docker.vm_url}")
    private String vmURL;

    @Value("${docker.container_url}")
    private String containerURL;


    public ResponseEntity<CreateResponseDTO> createStartDockerNginx() {
        String body = "{\n" +
                "    \"Image\": \"ubuntu/nginx:1.18-22.04_beta\",\n" +
                "    \"Domainname\": \"mycontainer\",\n" +
                "    \"User\": \"root\",\n" +
                "    \"HostConfig\": {\n" +
                "        \"PortBindings\": {\n" +
                "            \"80/tcp\": [\n" +
                "                {\n" +
                "                    \"HostPort\": \"8080\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    }\n" +
                "}";
        String url = String.format("%s/containers/create", vmURL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<String> request = new HttpEntity<>(body, headers);

        //First Call The Docker API Create Container Endpoint
        logger.info("Creating  container");
        ResponseEntity<CreateResponseDTO> createContainerResponse = restTemplate
                .exchange(url, HttpMethod.POST, request, CreateResponseDTO.class);
        String containerId = Objects.requireNonNull(createContainerResponse.getBody()).Id();
        String startURL = String.format("http://localhost:8090/containers/%s/start", containerId);


        //Then  Call The Docker API Start Container
        logger.info("Starting container {}", containerId);
        var startContainerResponse = restTemplate.exchange(startURL, HttpMethod.POST, null, String.class);

        return startContainerResponse.getStatusCode().is2xxSuccessful() ? createContainerResponse : null;
    }

    public ResponseEntity<String> createStopDockerNginx(String containerId) {
        String startURL = String.format("%s/containers/%s/stop", vmURL, containerId);

        logger.info("Stopping container {}", containerId);
        var startContainerResponse = restTemplate.exchange(startURL, HttpMethod.POST, null, String.class);


        return ResponseEntity.ok(startContainerResponse.getStatusCode().toString());
    }

    public ResponseEntity<String> getDockerPage() {


        logger.info("calling container page");
        restTemplate.exchange(containerURL, HttpMethod.GET, null, String.class);

        return restTemplate.exchange(containerURL, HttpMethod.GET, null, String.class);
    }


}
