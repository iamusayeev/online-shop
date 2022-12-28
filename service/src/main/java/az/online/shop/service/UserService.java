package az.online.shop.service;

import static az.online.shop.entity.QUser.user;

import az.online.shop.dto.UserCreateEditDTO;
import az.online.shop.dto.UserFilter;
import az.online.shop.dto.UserReadDTO;
import az.online.shop.entity.User;
import az.online.shop.mapper.UserCreateEditMapper;
import az.online.shop.mapper.UserReadMapper;
import az.online.shop.repository.QPredicates;
import az.online.shop.repository.UserRepository;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean create(UserCreateEditDTO userDto) {
        verifyPasswords(userDto.getPassword(), userDto.getMatchingPassword());
        uploadImage(userDto.getImage());
        User user = userCreateEditMapper.map(userDto);
        userRepository.save(user);
        return user.getId() != null;
    }

    public Page<UserReadDTO> findAll(UserFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.firstname(), user.personalInfo.firstname::containsIgnoreCase)
                .add(filter.lastname(), user.personalInfo.lastname::containsIgnoreCase)
                .add(filter.birthDate(), user.personalInfo.birthDate::before)
                .buildOr();
        return userRepository.findAll(predicate, pageable)
                .map(userReadMapper::map);
    }

    public Optional<UserReadDTO> findById(Integer id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    public Optional<byte[]> findAvatar(Integer id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public Optional<UserReadDTO> update(Integer id, UserCreateEditDTO userDto) {
        return userRepository.findById(id)
                .map(entity -> {
                    uploadImage(userDto.getImage());
                    return userCreateEditMapper.map(userDto, entity);
                })
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                }).orElse(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("message failed to retrieve user: " + username));
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }

    @Transactional
    public boolean updateProfile(UserCreateEditDTO dto) {
        User savedUser = findByUsername(dto.getUsername());

        verifyPasswords(dto.getPassword(), dto.getMatchingPassword());

        boolean isChanged = false;
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            savedUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            isChanged = true;
        }

        if (!Objects.equals(dto.getEmail(), savedUser.getEmail())) {
            savedUser.setEmail(dto.getEmail());
            isChanged = true;
        }

        if (isChanged) {
            userRepository.save(savedUser);
            return true;
        }
        return false;
    }

    private void verifyPasswords(String password, String matchingPassword) {
        if (!password.equals(matchingPassword)) {
            throw new RuntimeException("Пароли не совпадают");
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));
    }
}