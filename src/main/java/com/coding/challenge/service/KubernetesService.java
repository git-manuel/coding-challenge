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
public class KubernetesService {
    private RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(KubernetesService.class);

    @Value("${ks8.service_url}")
    private String kubernetesServiceURL;

    @Value("${ks8.cluster_url}")
    private String clusterURL;


    public ResponseEntity<String> createStartDockerK8s() {
        String body = "{\n" +
                "  \"apiVersion\": \"apps/v1\",\n" +
                "  \"kind\": \"Deployment\",\n" +
                "  \"metadata\": {\n" +
                "    \"name\": \"ubuntu-nginx-deployment\",\n" +
                "    \"namespace\": \"default\"\n" +
                "  },\n" +
                "  \"spec\": {\n" +
                "    \"replicas\": 1,\n" +
                "    \"selector\": {\n" +
                "      \"matchLabels\": {\n" +
                "        \"app\": \"ubuntu-nginx\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"template\": {\n" +
                "      \"metadata\": {\n" +
                "        \"labels\": {\n" +
                "          \"app\": \"ubuntu-nginx\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"spec\": {\n" +
                "        \"containers\": [\n" +
                "          {\n" +
                "            \"name\": \"ubuntu-nginx\",\n" +
                "            \"image\": \"ubuntu/nginx:1.18-20.04_beta\",\n" +
                "            \"ports\": [\n" +
                "              {\n" +
                "                \"containerPort\": 80\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        String url = String.format("%s/apis/apps/v1/namespaces/default/deployments",clusterURL);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        logger.info("Creating  kubernetes deployment with ubuntu-nginx ...");

        return restTemplate
                .exchange(url, HttpMethod.POST, request, String.class);
    }

    public ResponseEntity<String> stopDockerK8s(String deployment) {
        String startURL = String.format("%s/apis/apps/v1/namespaces/default/deployments/%s", clusterURL,deployment);

        logger.info("Deleting deployment {}", deployment);

        return restTemplate.exchange(startURL, HttpMethod.DELETE, null, String.class);
    }

    public ResponseEntity<String> getPageK8s() {
        logger.info("calling container nginx page");
        return restTemplate.exchange(kubernetesServiceURL, HttpMethod.GET, null, String.class);
    }

}
