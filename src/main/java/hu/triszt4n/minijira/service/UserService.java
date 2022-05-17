package hu.triszt4n.minijira.service;

import hu.triszt4n.minijira.dto.CreateUserDto;
import hu.triszt4n.minijira.entity.UserEntity;
import hu.triszt4n.minijira.repository.UserRepository;
import hu.triszt4n.minijira.util.RoleEnum;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(CreateUserDto createUserDto) {
        return userRepository.save(new UserEntity()
                .setUsername(createUserDto.getUsername())
                .setPassword(createUserDto.getPasswordRaw())
                .setRole(RoleEnum.DEVELOPER));
    }
}
