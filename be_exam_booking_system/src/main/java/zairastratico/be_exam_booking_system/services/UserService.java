package zairastratico.be_exam_booking_system.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zairastratico.be_exam_booking_system.entities.User;
import zairastratico.be_exam_booking_system.exceptions.BadRequestException;
import zairastratico.be_exam_booking_system.exceptions.NotFoundException;
import zairastratico.be_exam_booking_system.payloads.UserPswUpdateDTO;
import zairastratico.be_exam_booking_system.payloads.UserResponseDTO;
import zairastratico.be_exam_booking_system.payloads.UserUpdateDTO;
import zairastratico.be_exam_booking_system.repositories.UserRepository;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCrypt;

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
    }

    public UserResponseDTO getMyProfile(User user) {
        return new UserResponseDTO(
                user.getName(), user.getEmail(), user.getPassword()
        );
    }

    public User updateUser(Long id, UserUpdateDTO payload) {

        userRepository.findByEmailIgnoreCase(payload.email())
                .filter(existingPublicUser -> !existingPublicUser.getId().equals(id))
                .ifPresent(existingPublicUser -> {
                    throw new BadRequestException("A user with email " + payload.email() + " already exists in our system");
                });


        User user = findUserById(id);
        user.setName(payload.name());
        user.setEmail(payload.email());

        User updatedUser = userRepository.save(user);

        log.info("User " + user.getEmail() + " has been updated");

        return updatedUser;
    }


    public void updatePassword(Long userId, UserPswUpdateDTO payload) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        if (!bCrypt.matches(payload.oldPassword(), user.getPassword())) {
            throw new BadRequestException("Old password is incorrect");
        }

        user.setPassword(bCrypt.encode(payload.newPassword()));
        userRepository.save(user);

        log.info("Password updated for user " + user.getEmail());
    }
}
