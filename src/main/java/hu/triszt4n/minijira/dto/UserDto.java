package hu.triszt4n.minijira.dto;

import hu.triszt4n.minijira.entity.UserEntity;
import hu.triszt4n.minijira.util.RoleEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto {
    private Long id;
    private String username;
    private RoleEnum role;

    public static UserDto convert(UserEntity entity) {
        return new UserDto()
                .setId(entity.getId())
                .setUsername(entity.getUsername())
                .setRole(entity.getRole());
    }
}
