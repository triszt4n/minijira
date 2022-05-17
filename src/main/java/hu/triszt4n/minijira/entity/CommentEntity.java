package hu.triszt4n.minijira.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@Accessors(chain = true)
public class CommentEntity {
    @GeneratedValue
    @Id
    private Long id;

    @Column
    private String body;

    @ManyToOne
    private UserEntity publisher;

    @ManyToOne
    private TaskEntity task;
}
