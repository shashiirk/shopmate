package dev.shashiirk.shopmate.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppContextDTO {

    private boolean authenticated;

    private UserDTO user;

}
