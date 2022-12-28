package az.online.shop.dto;

import az.online.shop.model.Role;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserReadDTO {
    Integer id;
    String username;
    String firstname;
    String lastname;
    LocalDate birthDate;
    String image;
    Role role;
    String email;
}