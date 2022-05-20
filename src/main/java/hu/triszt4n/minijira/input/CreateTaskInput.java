package hu.triszt4n.minijira.input;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Accessors(chain = true)
public class CreateTaskInput {
    @NotBlank
    @Size(min = 3, max = 64)
    private String title;

    @Size(max = 1000)
    private String description = "";

    @NotNull
    private String statusValue;

    @NotNull
    private float hoursNeeded;
}
