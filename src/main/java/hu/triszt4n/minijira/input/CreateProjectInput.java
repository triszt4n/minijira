package hu.triszt4n.minijira.input;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class CreateProjectInput {
    @NotBlank
    @Size(min = 3, max = 64)
    private String title;

    private String description = "";
}
