package com.xiaoyu.movie.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.SQLException;

@RestController
@Component
@RequestMapping("/v1")
public class HealthCheckController {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String pwd;

    // Helper method for creating no-cache headers
    public HttpHeaders createNoCacheHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache");
        return headers;
    }

    // GET method: /v1/healthcheck
    @GetMapping("/healthcheck")
    public ResponseEntity<Map<String, String>> healthCheck(@RequestParam(required = false) Map<String, String> params) {

        Map<String, String> response = new HashMap<>();
            if (!params.isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Bad Request");
                return new ResponseEntity<>(errorResponse, createNoCacheHeaders(), HttpStatus.BAD_REQUEST);
            }
            else {
                try (Connection connection = DriverManager.getConnection(url, user, pwd)) {
                    if (connection != null) {
                        // 200 OK if no parameters and database is healthy
                        response.put("status", "OK");
                        response.put("database", "healthy");
                        System.out.println("database connected!");
                        return new ResponseEntity<>(response, createNoCacheHeaders(), HttpStatus.OK);
                    }
                }
                catch (Exception e) {
                    // Return 503 if the database connection is not healthy
                    response.put("error", "Service Unavailable");
                    response.put("database", "unavailable");
                    return new ResponseEntity<>(response, createNoCacheHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
                }
                return new ResponseEntity<>(response, createNoCacheHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
            }
    }

    @RequestMapping(
            value = "/healthcheck",
            method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE}
    )
    public ResponseEntity<Map<String, String>> handleInvalidRequest() {
        // 400 Bad Request for invalid methods
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Bad Request");
        return new ResponseEntity<>(errorResponse, createNoCacheHeaders(), HttpStatus.BAD_REQUEST);
    }

}