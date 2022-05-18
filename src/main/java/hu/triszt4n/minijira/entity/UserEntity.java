package hu.triszt4n.minijira.entity;

import hu.triszt4n.minijira.util.RoleEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@Accessors(chain = true)
public class UserEntity {
    @GeneratedValue
    @Id
    private Long id;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private RoleEnum role;
}
