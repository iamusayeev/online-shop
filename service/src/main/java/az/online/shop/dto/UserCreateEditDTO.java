package az.online.shop.dto;

import az.online.shop.model.Role;
import java.time.LocalDate;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

@Value
@FieldNameConstants
public class UserCreateEditDTO {
    String username;
    String email;
    String password;
    String matchingPassword;
    String firstname;
    String lastname;
    LocalDate birthDate;
    Role role;
    MultipartFile image;
}