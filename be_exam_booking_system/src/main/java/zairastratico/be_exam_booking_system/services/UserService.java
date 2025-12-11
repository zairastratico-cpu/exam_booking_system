package zairastratico.be_exam_booking_system.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zairastratico.be_exam_booking_system.entities.User;
import zairastratico.be_exam_booking_system.exceptions.BadRequestException;
import zairastratico.be_exam_booking_system.exceptions.NotFoundException;
import zairastratico.be_exam_booking_system.payloads.*;
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
                user.getName(), user.getEmail()
        );
    }

    public UserResponseDTO createUser(UserRegistrationDTO payload) {
        if (userRepository.existsByEmail(payload.email())) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User(
                payload.name(),
                payload.email(),
                bCrypt.encode(payload.password())
        );

        User savedUser = userRepository.save(user);
        log.info("New admin registered: {}", savedUser.getEmail());

        return new UserResponseDTO(
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    public UserUpdateResponseDTO updateUser(Long id, UserUpdateDTO payload) {
        User user = findUserById(id);

        if (!user.getEmail().equalsIgnoreCase(payload.email())) {
            if (userRepository.existsByEmail(payload.email())) {
                throw new BadRequestException("A user with email " + payload.email() + " already exists in our system");
            }
        }

        user.setName(payload.name());
        user.setEmail(payload.email());

        User updatedUser = userRepository.save(user);
        log.info("User {} updated", updatedUser.getEmail());

        return new UserUpdateResponseDTO(
                updatedUser.getName(),
                updatedUser.getEmail()
        );
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

    public void deleteUser(Long id) {
        User user = findUserById(id);

        userRepository.deleteById(id);
        log.info("User {} deleted", user.getEmail());
    }

    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
