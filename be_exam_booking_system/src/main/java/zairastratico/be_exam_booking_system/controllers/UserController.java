package zairastratico.be_exam_booking_system.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO createUser(@RequestBody @Valid UserRegistrationDTO payload) {
        return userService.createUser(payload);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO getMyProfile(@AuthenticationPrincipal User authorizedUser) {
        return userService.getMyProfile(authorizedUser);
    }

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public UserUpdateResponseDTO updateMyProfile(@AuthenticationPrincipal User authorizedUser, @RequestBody @Valid UserUpdateDTO payload) {

        return userService.updateUser(authorizedUser, payload);
    }

    @PatchMapping("/me/password")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void updateMyPassword(@AuthenticationPrincipal User authorizedUser, @RequestBody @Valid UserPswUpdateDTO payload) {

        userService.updatePassword(authorizedUser, payload);
    }

    @DeleteMapping("/me")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyProfile(@AuthenticationPrincipal User authorizedUser) {

        userService.deleteUser(authorizedUser);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return userService.getMyProfile(user);
    }
}

