package hu.triszt4n.minijira.dto;

import hu.triszt4n.minijira.entity.TaskEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private float hoursNeeded;
    private String statusName;
    private boolean hasAssignee;

    public static TaskDto convert(TaskEntity entity) {
        return new TaskDto()
                .setId(entity.getId())
                .setTitle(entity.getTitle())
                .setDescription(entity.getDescription())
                .setHoursNeeded(entity.getHoursNeeded())
                .setStatusName(entity.getStatus().getName())
                .setHasAssignee(!entity.getAssignedUsers().isEmpty());
    }
}
