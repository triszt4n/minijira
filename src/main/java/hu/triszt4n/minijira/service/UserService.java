package hu.triszt4n.minijira.service;

import hu.triszt4n.minijira.input.CreateUserInput;
import hu.triszt4n.minijira.entity.UserEntity;
import hu.triszt4n.minijira.input.LoginUserInput;
import hu.triszt4n.minijira.repository.UserRepository;
import hu.triszt4n.minijira.util.RoleEnum;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(CreateUserInput createUserInput) throws IllegalArgumentException {
        if (userRepository.existsByUsernameIgnoreCase(createUserInput.getUsername())) {
            throw new IllegalArgumentException("Username already in use");
        }

        return userRepository.save(new UserEntity()
                .setUsername(createUserInput.getUsername())
                .setPassword(createUserInput.getPasswordRaw())
                .setRole(RoleEnum.DEVELOPER));
    }

    public UserEntity getUser(LoginUserInput loginUserInput) {
        return userRepository.findByUsernameIgnoreCaseAndPassword(
                loginUserInput.getUsername(),
                loginUserInput.getPasswordRaw()
                ).orElse(null);
    }
}
