package com.example.mprprojectmvn.student.exceptionhandler;

import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.UUID;

public record ErrorResponse( UUID id, Instant timestamp, String message) {
}
