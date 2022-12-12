package az.online.shop.http.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import az.online.shop.dto.PageResponse;
import az.online.shop.dto.UserCreateEditDTO;
import az.online.shop.dto.UserFilter;
import az.online.shop.dto.UserReadDTO;
import az.online.shop.entity.User;
import az.online.shop.model.Role;
import az.online.shop.service.UserService;
import java.security.Principal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String findAll(Model model,
                          UserFilter filter,
                          Pageable pageable) {
        model.addAttribute("time", LocalDate.now().toString());
        model.addAttribute("filter", filter);
        Page<UserReadDTO> page = userService.findAll(filter, pageable);
        model.addAttribute("users", PageResponse.of(page));
        return "user/users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id,
                           Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDTO user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user/registration";
    }

    @PostMapping
    public String create(@ModelAttribute UserCreateEditDTO user, Model model) {
        if (userService.create(user)) {
            return "redirect:/login";
        } else {
            model.addAttribute("user", user);
            return "/user/user";
        }
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @ModelAttribute UserCreateEditDTO user) {
        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(NOT_FOUND);
        }
        return "redirect:/users";
    }

    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("You must provide a principal");
        }
        User user = userService.findByUsername(principal.getName());

        UserReadDTO dto = UserReadDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
        model.addAttribute("user", dto);
        return "/user/profile";
    }

    @PostMapping("/profile")
    public String updateProfileUser(UserCreateEditDTO dto, Model model) {
        model.addAttribute("user", dto);
        boolean updated = userService.updateProfile(dto);
        if (updated) {
            return "redirect:/login";
        }
        return "redirect:/users/profile";
    }
}