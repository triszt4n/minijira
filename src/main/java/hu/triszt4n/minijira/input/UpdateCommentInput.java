package hu.triszt4n.minijira.input;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class UpdateCommentInput {
    @NotBlank
    private String body;
}
