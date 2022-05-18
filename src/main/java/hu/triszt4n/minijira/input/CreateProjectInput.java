package hu.triszt4n.minijira.input;

import hu.triszt4n.minijira.entity.ProjectEntity;
import hu.triszt4n.minijira.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class CreateProjectInput extends ProjectEntity {
    @NotBlank
    @Size(min = 3, max = 64)
    private String title;

    private String description = "";
}
