package hu.triszt4n.minijira.dto;

import hu.triszt4n.minijira.entity.CommentEntity;
import hu.triszt4n.minijira.entity.UserEntity;
import hu.triszt4n.minijira.util.RoleEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommentDto {
    private Long id;
    private Long publisherId;
    private String publisherUsername;
    private String body;

    public static CommentDto convert(CommentEntity entity) {
        return new CommentDto()
                .setId(entity.getId())
                .setBody(entity.getBody())
                .setPublisherId(entity.getPublisher().getId())
                .setPublisherUsername(entity.getPublisher().getUsername());
    }
}
