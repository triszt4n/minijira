package hu.triszt4n.minijira.input;

import hu.triszt4n.minijira.entity.TaskEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class UpdateTaskInput {
    @NotBlank
    @Size(min = 3, max = 64)
    private String title;

    private String description = "";

    @NotNull
    private String statusValue;

    @NotNull
    private float hoursNeeded;

    public UpdateTaskInput() {
    }

    public UpdateTaskInput(TaskEntity taskEntity) {
        this.title = taskEntity.getTitle();
        this.description = taskEntity.getDescription();
        this.statusValue = taskEntity.getStatus().toString();
        this.hoursNeeded = taskEntity.getHoursNeeded();
    }
}
