package hu.triszt4n.minijira.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MessageDto {
    private String type;
    private String message;
}
