package com.ecom.microservices.dto;

import com.ecom.microservices.model.UserRole;
import lombok.Data;

@Data
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole userRole;
    private AddressDTO addressDTO;
}
