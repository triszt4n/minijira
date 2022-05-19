package hu.triszt4n.minijira.input;

import hu.triszt4n.minijira.entity.ProjectEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class UpdateProjectInput {
    @NotBlank
    @Size(min = 3, max = 64)
    private String title;
    private String description;
    private String isClosed = "false";

    public UpdateProjectInput() {
    }

    public UpdateProjectInput(ProjectEntity projectEntity) {
        this.title = projectEntity.getTitle();
        this.description = projectEntity.getDescription();
        this.isClosed = projectEntity.isClosed() ? "true" : "false";
    }
}
