package com.catchthemoment.service;

import com.catchthemoment.dto.ConfirmationEmailDTO;
import com.catchthemoment.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> saveUser(UserDTO userDTO);
    ResponseEntity<?>confirmEmail(ConfirmationEmailDTO emailDTO);

}
