package hu.triszt4n.minijira.service;

import hu.triszt4n.minijira.entity.UserEntity;
import hu.triszt4n.minijira.input.CreateUserInput;
import hu.triszt4n.minijira.repository.UserRepository;
import hu.triszt4n.minijira.util.RoleEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(CreateUserInput createUserInput) throws IllegalArgumentException {
        if (userRepository.existsByUsernameIgnoreCase(createUserInput.getUsername())) {
            throw new IllegalArgumentException("Username already in use");
        }

        passwordEncoder.matches(this.passwordEncoder
                .encode(createUserInput.getPasswordRaw()), this.passwordEncoder
                .encode(createUserInput.getPasswordRaw()));

        userRepository.save(new UserEntity()
                .setUsername(createUserInput.getUsername())
                .setPassword(this.passwordEncoder
                        .encode(createUserInput.getPasswordRaw()))
                .setRole(RoleEnum.DEVELOPER));
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public UserEntity getById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
