package hu.triszt4n.minijira.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
public class ProjectEntity {
    @GeneratedValue
    @Id
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private boolean isClosed = false;

    @ManyToOne
    private UserEntity manager;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval=true, cascade=CascadeType.ALL, mappedBy = "project")
    private List<TaskEntity> tasks;
}
