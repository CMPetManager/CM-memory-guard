package com.catchthemoment.dto;

import com.catchthemoment.validation.Password;
import jakarta.validation.constraints.Email;


import javax.validation.constraints.NotNull;

public record UserDTO(@NotNull String name, @NotNull @Password String password,
                      @Email(regexp = "^(.+)@(.+)$", message = "Incorrect email") @NotNull String email) {
}
