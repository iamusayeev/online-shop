package az.online.shop.mapper;

import static java.util.function.Predicate.not;

import az.online.shop.dto.UserCreateEditDTO;
import az.online.shop.entity.PersonalInfo;
import az.online.shop.entity.User;
import az.online.shop.model.Role;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDTO, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateEditDTO fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDTO object) {
        User user = new User();
        copy(object, user);

        return user;
    }


    private void copy(UserCreateEditDTO object, User user) {
        Random random = new Random();
        user.setUsername(object.getUsername());
        user.setEmail(String.valueOf(random.nextInt()));
        user.setPassword(object.getPassword());
        user.setPersonalInfo(new PersonalInfo(object.getFirstname(), object.getLastname(), object.getBirthDate()));
        user.setRole(Role.USER);

        Optional.ofNullable(object.getPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);

        Optional.ofNullable(object.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));
    }
}