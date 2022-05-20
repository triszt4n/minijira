package hu.triszt4n.minijira.entity;

import hu.triszt4n.minijira.util.StatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
public class TaskEntity {
    @GeneratedValue
    @Id
    private Long id;

    @Column
    private String title;

    @Lob
    @Column
    private String description;

    @Column
    private float hoursNeeded;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne
    private ProjectEntity project;

    @ManyToMany
    private List<UserEntity> assignedUsers;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval=true, cascade=CascadeType.ALL, mappedBy = "task")
    private List<CommentEntity> comments;
}
