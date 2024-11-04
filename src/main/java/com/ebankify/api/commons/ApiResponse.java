package com.ebankify.api.commons;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

public class ApiResponse {

    public static <T> ResponseEntity<T> okOrNotFound(Optional<T> entity) {
        return entity.map(ApiResponse::ok).orElseGet(ApiResponse::notFound);
    }

    public static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static <T> ResponseEntity<T> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    public static ResponseEntity<Void> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public static <T> ResponseEntity<T> badRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((T) message);
    }

    public static <T> ResponseEntity<T> created(String path, T body) {
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path(path).build().toUri();
        return ResponseEntity.created(location).body(body);
    }

    public static ResponseEntity<Void> tooManyRequests() {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    public static ResponseEntity<Void> tooManyRequests(long retryAfter) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.RETRY_AFTER, String.valueOf(retryAfter));
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).headers(headers).build();
    }

    public static ResponseEntity<Void> response(HttpStatus status, String headerName, String headerValue) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerName, headerValue);
        return ResponseEntity.status(status).headers(headers).build();
    }
}
