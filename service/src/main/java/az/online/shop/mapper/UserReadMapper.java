package az.online.shop.mapper;

import az.online.shop.dto.UserReadDTO;
import az.online.shop.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDTO> {

    @Override
    public UserReadDTO map(User object) {
        return UserReadDTO.builder()
                .id(object.getId())
                .username(object.getUsername())
                .firstname(object.getPersonalInfo().getFirstname())
                .lastname(object.getPersonalInfo().getLastname())
                .birthDate(object.getPersonalInfo().getBirthDate())
                .role(object.getRole())
                .email(object.getEmail())
                .image(object.getImage())
                .build();
    }
}