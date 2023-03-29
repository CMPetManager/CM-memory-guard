package com.catchthemoment.dto;

import com.catchthemoment.validation.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;


import javax.validation.constraints.NotNull;

public record UserDTO(@NotNull @JsonProperty("name") String name,
                      @NotNull @Password @JsonProperty("password") String password,
                      @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+/.[a-zA-Z0-9-.]+$", message = "Incorrect email") @NotNull @JsonProperty("email")
                      String email, String confirmation_reset_token) {
}
