package hu.triszt4n.minijira.input;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class CreateCommentInput {
    @NotBlank
    @Max(1000)
    private String body;
}
