package com.catchthemoment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;

import java.time.LocalDateTime;

public record ConfirmationEmailDTO(@NotNull @JsonProperty("confirmation_token") String confirmationToken){
}
