package dev.hrishi.sec.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoleNotFound extends RuntimeException {
    public RoleNotFound(String message) {
        super(message);
    }

    public RoleNotFound() {
        super("Role not found");
    }
}