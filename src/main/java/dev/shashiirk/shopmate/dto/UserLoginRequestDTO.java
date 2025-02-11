package dev.shashiirk.shopmate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequestDTO {

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @NotBlank
    @Size(max = 60)
    private String password;

}
