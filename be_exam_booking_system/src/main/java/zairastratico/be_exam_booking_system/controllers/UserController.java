package zairastratico.be_exam_booking_system.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import zairastratico.be_exam_booking_system.entities.User;
import zairastratico.be_exam_booking_system.payloads.*;
import zairastratico.be_exam_booking_system.services.UserService;

@RestController
@RequestMapping("/admins")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@RequestBody @Valid UserRegistrationDTO payload) {
        return userService.createUser(payload);
    }

    @GetMapping("/me")
    public UserResponseDTO getMyProfile() {
        String userIdStr = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = Long.parseLong(userIdStr);
        User user = userService.findUserById(userId);

        return userService.getMyProfile(user);
    }

    @PutMapping("/me")
    public UserUpdateResponseDTO updateMyProfile(@RequestBody @Valid UserUpdateDTO payload) {
        String userIdStr = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = Long.parseLong(userIdStr);

        return userService.updateUser(userId, payload);
    }

    @PatchMapping("/me/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // 204
    public void updateMyPassword(@RequestBody @Valid UserPswUpdateDTO payload) {
        String userIdStr = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = Long.parseLong(userIdStr);

        userService.updatePassword(userId, payload);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // 204
    public void deleteMyProfile() {
        String userIdStr = (String) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Long userId = Long.parseLong(userIdStr);

        userService.deleteUser(userId);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return userService.getMyProfile(user);
    }
}

