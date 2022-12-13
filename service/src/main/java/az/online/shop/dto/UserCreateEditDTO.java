package az.online.shop.dto;

import az.online.shop.model.Role;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

@Value
@FieldNameConstants
public class UserCreateEditDTO {

    @NotNull
    @NotBlank
    @NotEmpty
    String username;

    @Email
    String email;
    String password;
    String matchingPassword;

    @NotNull
    @Size(min = 3, max = 64)
    String firstname;

    @NotNull
    @NotBlank
    @NotEmpty
    @Min(3)
    String lastname;

    LocalDate birthDate;
    Role role;
    MultipartFile image;
}