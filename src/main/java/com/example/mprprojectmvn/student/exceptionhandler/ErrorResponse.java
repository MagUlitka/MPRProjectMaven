package com.example.mprprojectmvn.student.exceptionhandler;

import java.time.Instant;
import java.util.UUID;

public record ErrorResponse(UUID id, Instant timestamp, String message) {
}
